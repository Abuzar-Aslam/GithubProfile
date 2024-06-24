package home.assignemnt.data.network

/**
 * Callback to handle errors from Mobile Services. Implementations should handle one specific error only.
 * This interface allows for the interception and handling of request exceptions.
 */
interface RequestExceptionListener {

    /**
     * Intercepts request exceptions and determines whether they are handled silently.
     *
     * @param requestUrl The URL of the request that caused the exception.
     * @param exception The [RequestException] that was thrown during the request.
     * @return `true` if the error was handled silently, `false` if the error was not handled.
     * @throws RequestException when the error was handled but not swallowed.
     */
    @Throws(RequestException::class)
    fun onRequestException(requestUrl: String, exception: RequestException): Boolean
}
