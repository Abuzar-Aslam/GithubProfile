package home.assignemnt.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Interceptor to handle invalid proxy port exceptions.
 * This interceptor catches `IllegalArgumentException` caused by invalid proxy port settings
 * (e.g., port out of range -1) and rethrows them as `IOException` to prevent the application from crashing.
 */
internal class InvalidProxyPortInterceptor : Interceptor {

    /**
     * Intercepts the outgoing HTTP request and handles invalid proxy port exceptions.
     *
     * @param chain The OkHttp [Interceptor.Chain] which provides access to the outgoing request.
     * @return The [Response] obtained after processing the request.
     * @throws IOException if an invalid proxy port is detected.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (exception: IllegalArgumentException) {
            if (exception.message == "port out of range:-1") {
                // Handle the specific case of invalid proxy port
                throw IOException("Port out of range for url ${chain.request().url}")
            } else {
                // Rethrow other IllegalArgumentExceptions
                throw exception
            }
        }
    }
}
