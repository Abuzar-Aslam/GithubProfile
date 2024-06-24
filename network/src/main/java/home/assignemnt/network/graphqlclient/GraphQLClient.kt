package home.assignemnt.network.graphqlclient

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.cache.http.HttpFetchPolicy
import com.apollographql.apollo3.cache.http.httpCache
import com.apollographql.apollo3.cache.http.httpDoNotStore
import com.apollographql.apollo3.cache.http.httpExpireTimeout
import com.apollographql.apollo3.cache.http.httpFetchPolicy
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import home.assignemnt.network.BuildConfig
import home.assignemnt.network.response.MissingDataException
import home.assignemnt.network.response.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import kotlin.time.Duration.Companion.minutes

/**
 * Client to execute GraphQL calls with caching and error handling.
 * @param context Application context.
 * @param graphQLInterceptors Set of interceptors for GraphQL operations.
 */
class GraphQLClient(
    private val context: Context,
    private val graphQLInterceptors: Set<@JvmSuppressWildcards GraphQLInterceptor>,
) {
    private val apolloClient: ApolloClient by lazy { buildApolloClient() }

    /**
     * Builds the ApolloClient instance with OkHttp client and caching settings.
     *
     * @return The configured ApolloClient instance.
     */
    private fun buildApolloClient(): ApolloClient {
        val cacheDir = File(context.cacheDir, HTTP_CACHE_NAME)
        val okHttpClient = HttpClientFactory.initOkHttpClient(
            context = context,
            name = TAG,
            addDebugLogging = true,
            interceptors = graphQLInterceptors
        )
        return ApolloClient.Builder()
            .okHttpClient(okHttpClient)
            .serverUrl(BuildConfig.BASE_URL)
            .httpCache(cacheDir, HTTP_CACHE_SIZE)
            .httpExpireTimeout(HTTP_CACHE_EXPIRE_MINUTES.minutes.inWholeMilliseconds)
            .build()
    }

    /**
     * Determines the HTTP fetch policy based on the useCache parameter.
     *
     * @param useCache Whether to use the cache or not.
     * @return The HttpFetchPolicy to be used.
     */
    private fun getHttpFetchPolicy(useCache: Boolean): HttpFetchPolicy {
        return if (useCache) HttpFetchPolicy.CacheFirst else HttpFetchPolicy.NetworkOnly
    }

    /**
     * Performs a GraphQL operation.
     *
     * @param operation The [GraphQLOperation] to execute.
     * @throws MissingDataException when the operation returns no data and there are no errors.
     * @throws RequestException for other failures.
     */
    suspend fun <ModelType, DataType: Operation.Data> operationCall(
        operation: GraphQLOperation<ModelType, DataType>
    ): ModelType = withContext(Dispatchers.IO) {
        try {
            val (data, errors) = performOperation(
                operation.query,
                operation.useCache,
                operation.headers
            )
            operation.transform(data, errors)
                ?: throw MissingDataException("Operation transform returned `null`")
        } catch (e: RequestException) {
            Timber.e("GraphQL operation ${operation.query.name()} failed")
            throw e
        }
    }

    /**
     * Performs a GraphQL operation.
     *
     * @param operation The GraphQL [Operation] to execute.
     * @param useCache Whether to use the cache or not.
     * @param headers Optional HTTP headers to include in the request.
     * @return A pair containing the operation data and any errors.
     * @throws RequestException if the request fails.
     */
    private suspend fun <DataType: Operation.Data> performOperation(
        operation: Operation<DataType>,
        useCache: Boolean,
        headers: List<HttpHeader>?
    ): Pair<DataType, List<Error>> = withContext(Dispatchers.IO) {
        val response: ApolloResponse<out Operation.Data> = apolloClient.executeOperation(
            operation,
            useCache,
            headers
        ) { apolloException ->
            throw RequestException(apolloException.localizedMessage!!)
        }

        val errors = response.errors ?: emptyList()
        val data = response.data ?: throw MissingDataException("Query returned no data")
        return@withContext (data as DataType) to errors
    }

    /**
     * Executes a GraphQL query operation.
     *
     * @param operation The GraphQL query operation to execute.
     * @param useCache Whether to use the cache.
     * @param headers Optional HTTP headers to include in the request.
     * @param onError Lambda to handle errors.
     * @return The ApolloResponse containing the operation data.
     */
    private suspend inline fun <DataType: Operation.Data> ApolloClient.executeOperation(
        operation: Operation<DataType>,
        useCache: Boolean,
        headers: List<HttpHeader>?,
        onError: (ApolloException) -> ApolloResponse<out Operation.Data>
    ): ApolloResponse<out Operation.Data> {
        val httpFetchPolicy = getHttpFetchPolicy(useCache)
        return try {
            val apolloCall = when (operation) {
                is Query<*> -> query(operation)
                    .httpFetchPolicy(httpFetchPolicy)
                    .httpDoNotStore(!useCache)
                else -> error("Couldn't perform operation")
            }
            headers?.forEach { apolloCall.addHttpHeader(it.name, it.value) }
            apolloCall.execute()
        } catch (e: ApolloException) {
            onError(e)
        }
    }

    /**
     * Clears all GraphQL caches.
     */
    fun clearCache() {
        apolloClient.httpCache.clearAll()
    }

    companion object {
        private const val TAG = "GraphQLClient"
        private const val HTTP_CACHE_NAME = "apolloCache"
        private const val HTTP_CACHE_SIZE = 1024L * 1024L // 1 MB
        private const val HTTP_CACHE_EXPIRE_MINUTES = 24 * 60L // 24 hours
    }
}
