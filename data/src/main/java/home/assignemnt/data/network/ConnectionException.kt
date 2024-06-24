package home.assignemnt.data.network

/**
 * A generic exception indicating a failure to connect to a specified URL.
 * This exception is used to represent errors that occur when attempting to establish a connection.
 *
 * @param message A detailed error message. Defaults to "Unable to connect".
 */
class ConnectionException(
    message: String = "Unable to connect"
) : RequestException(message) {

    /**
     * Secondary constructor to create a [ConnectionException] with a detailed error message.
     *
     * @param url The URL that could not be connected to.
     * @param method The HTTP method used for the connection attempt (e.g., GET, POST).
     */
    constructor(url: String, method: String) : this("Unable to connect to $method $url")
}
