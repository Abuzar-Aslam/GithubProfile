package home.assignemnt.network.response

import java.io.IOException

/**
 * Exception indicating a failure during a network request.
 * This exception encapsulates the error message, the response from the failed request, and an optional status code.
 *
 * @param message A detailed error message explaining the nature of the failure.
 * @param response The [FailedResponse] object containing the details of the failed response, if available.
 */
open class RequestException(
    message: String = "",
    val response: FailedResponse? = null
) : IOException(message) {

    private var overriddenStatusCode: Int = -1

    /**
     * The HTTP status code of the failed response.
     * If an overridden status code is set, it will be returned. Otherwise, the status code from the response is returned.
     * If neither is available, the default value is 0.
     */
    val statusCode: Int
        get() = if (overriddenStatusCode != -1) overriddenStatusCode else response?.code ?: 0

    /**
     * Provides a string representation of the exception, including the message, response, and status code.
     *
     * @return A string representation of the exception.
     */
    override fun toString(): String {
        return "RequestException(message='$message', response=$response, statusCode=$statusCode)"
    }
}
