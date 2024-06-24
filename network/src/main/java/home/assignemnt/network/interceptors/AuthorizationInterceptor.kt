package home.assignemnt.network.interceptors

import home.assignemnt.network.BuildConfig
import home.assignemnt.network.graphqlclient.GraphQLInterceptor
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add the Authorization header to every request.
 * This class is used to intercept HTTP requests and add the Authorization header
 * containing the API key for authentication purposes.
 */
class AuthorizationInterceptor : GraphQLInterceptor {

    /**
     * Intercepts the outgoing HTTP request and adds the Authorization header.
     *
     * @param chain The OkHttp [Interceptor.Chain] which provides access to the outgoing request.
     * @return The [Response] obtained after adding the Authorization header and proceeding with the request.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add the Authorization header with the API key
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()

        // Proceed with the modified request
        return chain.proceed(newRequest)
    }
}
