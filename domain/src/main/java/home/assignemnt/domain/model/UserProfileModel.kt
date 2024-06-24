package home.assignemnt.domain.model

/**
 * Data class representing a user's profile.
 *
 * @property avatarUrl The URL of the user's avatar image (nullable).
 * @property name The name of the user (nullable).
 * @property company The name of the company the user is associated with (nullable).
 * @property email The email address of the user.
 * @property followers The number of followers the user has.
 * @property following The number of users the user is following.
 * @property pinnedRepositories A list of repositories pinned by the user.
 * @property topRepositories A list of the user's top repositories.
 * @property starredRepositories A list of repositories starred by the user.
 */
data class UserProfileModel(
    val avatarUrl: String?,
    val name: String?,
    val company: String?,
    val email: String,
    val followers: Int,
    val following: Int,
    val pinnedRepositories: List<RepositoryModel>,
    val topRepositories: List<RepositoryModel>,
    val starredRepositories: List<RepositoryModel>
)
