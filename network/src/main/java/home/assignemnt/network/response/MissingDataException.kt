package home.assignemnt.network.response

/**
 * Exception indicating that expected data is missing from a response.
 * This exception is thrown when a response does not contain the expected data.
 *
 * @param message A detailed error message explaining the missing data.
 */
class MissingDataException(message: String) : RequestException(message)
