package home.assignemnt.presentation.model

/**
 * Data class representing the view data for a user repository.
 *
 * @property name The name of the repository.
 * @property description A brief description of the repository (nullable).
 * @property stargazerCount The number of stargazers (stars) the repository has.
 * @property language The primary programming language used in the repository (nullable).
 */
data class UserRepositoryViewData(
    val name: String,
    val description: String?,
    val stargazerCount: Int,
    val language: String?
)
