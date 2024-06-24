package home.assignemnt.domain.model

/**
 * Data class representing a repository model.
 *
 * @property name The name of the repository.
 * @property description The description of the repository (nullable).
 * @property stargazerCount The number of stars the repository has received.
 * @property language The primary programming language used in the repository (nullable).
 */
data class RepositoryModel(
    val name: String,
    val description: String?,
    val stargazerCount: Int,
    val language: String?
)
