package home.assignemnt.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.moczul.ok2curl.CurlInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Factory object to create and configure OkHttpClient instances.
 * Provides methods to initialize OkHttpClient with custom settings and interceptors.
 */
object HttpClientFactory {

    // Base client to reuse connection and thread pool, reducing memory usage and latency
    private val baseClient = OkHttpClient.Builder()
        .addInterceptor(InvalidProxyPortInterceptor())
        .build()

    /**
     * Initializes OkHttpClient with the given settings and interceptors.
     *
     * @param context Application context for accessing cache directories.
     * @param name Name for logging purposes.
     * @param addDebugLogging Whether to add debug logging interceptors.
     * @param addFailedRequestInterceptor Whether to add a failed request interceptor.
     * @param cacheDirectory Directory for cache storage. Defaults to a directory named "HttpResponseCache" in the app's cache directory.
     * @param interceptors Set of additional interceptors to be added to the client.
     * @param errorListeners List of listeners for handling request exceptions.
     * @param settings Custom settings for the HTTP client, including timeouts and cache size.
     * @return Configured OkHttpClient instance.
     */
    fun initOkHttpClient(
        context: Context,
        name: String,
        addDebugLogging: Boolean,
        addFailedRequestInterceptor: Boolean = true,
        cacheDirectory: File? = File(context.cacheDir, "HttpResponseCache"),
        interceptors: Set<Interceptor> = emptySet(),
        errorListeners: List<RequestExceptionListener> = emptyList(),
        settings: HttpClientSettings = HttpClientSettings(),
    ): OkHttpClient {

        val builder = baseClient.newBuilder()
            .connectTimeout(settings.connectionTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(settings.dataReadTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(settings.dataWriteTimeoutSeconds, TimeUnit.SECONDS)

        cacheDirectory?.let {
            builder.cache(Cache(it, settings.maxCacheSize))
        }

        if (addFailedRequestInterceptor) {
            builder.addInterceptor(FailedRequestInterceptor(errorListeners))
        }

        interceptors.forEach { builder.addInterceptor(it) }

        if (addDebugLogging) {
            builder.addNetworkInterceptor(CurlInterceptor(CurlTimberLogger(name)))
            builder.addNetworkInterceptor(ChuckerInterceptor(context))
        }

        return builder.build()
    }
}

/**
 * Data class to hold custom settings for OkHttpClient.
 *
 * @param connectionTimeoutSeconds Connection timeout in seconds.
 * @param dataReadTimeoutSeconds Data read timeout in seconds.
 * @param dataWriteTimeoutSeconds Data write timeout in seconds.
 * @param maxCacheSize Maximum cache size in bytes.
 */
data class HttpClientSettings(
    val connectionTimeoutSeconds: Long = DEFAULT_CONNECTION_TIMEOUT_SECS,
    val dataReadTimeoutSeconds: Long = DEFAULT_DATA_TIMEOUT_SECS,
    val dataWriteTimeoutSeconds: Long = DEFAULT_DATA_TIMEOUT_SECS,
    val maxCacheSize: Long = DEFAULT_MAX_CACHE_SIZE
)

private const val DEFAULT_CONNECTION_TIMEOUT_SECS = 10L
private const val DEFAULT_DATA_TIMEOUT_SECS = 10L
private const val DEFAULT_MAX_CACHE_SIZE = 10L * 1024L * 1024L // 10 MB
