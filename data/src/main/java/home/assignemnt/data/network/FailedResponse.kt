package home.assignemnt.data.network

import okhttp3.Headers
import okhttp3.Response
import java.io.IOException

/**
 * Data class representing a failed HTTP response.
 * This class encapsulates the HTTP response code, headers, and body of a failed response.
 *
 * @param code The HTTP status code of the response.
 * @param headers The HTTP headers of the response.
 * @param body The body of the response, if available.
 */
data class FailedResponse(
    val code: Int,
    val headers: Headers,
    val body: String?
) {
    /**
     * Secondary constructor to create a [FailedResponse] from an OkHttp [Response].
     * It attempts to read the response body as a string. If reading the body fails, it sets the body to null.
     *
     * @param response The OkHttp [Response] object to create the [FailedResponse] from.
     */
    constructor(response: Response) : this(
        response.code,
        response.headers,
        try {
            response.body?.string()
        } catch (e: IOException) {
            null
        }
    )
}
