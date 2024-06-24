package home.assignemnt.network.response

import com.moczul.ok2curl.logger.Logger
import timber.log.Timber

/**
 * A logger implementation that logs cURL commands using Timber.
 * This class is used to log HTTP requests in cURL format, helping in debugging network calls.
 *
 * @param tag The tag to use for logging. Must not exceed 23 characters.
 * @throws IllegalArgumentException if the tag length exceeds 23 characters.
 */
class CurlTimberLogger(val tag: String) : Logger {

    init {
        require(tag.length <= 23) {
            "Logging tag too long `$tag`, must not exceed 23 characters"
        }
    }

    /**
     * Logs the given message using Timber with the specified tag.
     *
     * @param message The message to log, typically a cURL command representing an HTTP request.
     */
    override fun log(message: String) {
        Timber.tag(tag + "OkHttpClient").v(message)
    }
}
