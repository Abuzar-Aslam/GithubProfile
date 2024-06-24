package home.assignemnt.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * An interceptor to handle failed HTTP requests and notify listeners.
 * This class intercepts HTTP requests and handles various types of failures, such as connection errors and non-successful responses.
 * It also notifies registered listeners about request exceptions.
 *
 * @param errorListeners A list of listeners that are notified when a request exception occurs.
 */
class FailedRequestInterceptor(
    private val errorListeners: List<RequestExceptionListener>
) : Interceptor {

    /**
     * Intercepts the HTTP request and handles failures.
     *
     * @param chain The OkHttp [Interceptor.Chain] which provides access to the outgoing request and incoming response.
     * @return The [Response] obtained after processing the request.
     * @throws RequestException if a request-related exception occurs.
     * @throws ConnectionException if a connection-related exception occurs.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var requestException: RequestException? = null
        val requestUrl = chain.request().url.toString()

        val response = try {
            chain.proceed(chain.request())
        } catch (e: RequestException) {
            requestException = e
            null
        } catch (e: IOException) {
            val requestMethod = chain.request().method
            Timber.e("Connection error '${e.message}' during $requestMethod request to $requestUrl")
            throw ConnectionException(url = requestUrl, method = requestMethod)
        }

        if (response != null) {
            val statusCode = response.code
            if (!response.isSuccessful && !response.isSuccessfulWebSocket()) {
                Timber.e("A non-successful statusCode ($statusCode) was received for request to: $requestUrl")
                // FailedResponse(response) consumes the body of the response
                requestException = RequestException(message = response.message, response = FailedResponse(response))
            }
        }

        if (requestException != null) {
            // If there is an error listener that wants to handle this exception, let it, but stop loop when error was handled.
            errorListeners.any { it.onRequestException(requestUrl, requestException) }
            throw requestException
        } else {
            return response!!
        }
    }
}

/**
 * Extension function to check if a response is successful for WebSocket connections.
 *
 * @return `true` if the response code is in the range of 100-199, `false` otherwise.
 */
fun Response.isSuccessfulWebSocket(): Boolean {
    return code in 100..199
}
