package home.assignemnt.presentation.model

/**
 * Data class representing the view data for a user profile.
 *
 * @property avatarUrl The URL of the user's avatar image (nullable).
 * @property name The name of the user (nullable).
 * @property company The name of the company the user is associated with (nullable).
 * @property email The email address of the user.
 * @property followers The number of followers the user has.
 * @property following The number of users the user is following.
 * @property pinnedRepositories A list of pinned repositories associated with the user.
 * @property topRepositories A list of top repositories associated with the user.
 * @property starredRepositories A list of starred repositories associated with the user.
 */
data class UserProfileViewData(
    val avatarUrl: String?,
    val name: String?,
    val company: String?,
    val email: String,
    val followers: Int,
    val following: Int,
    val pinnedRepositories: List<UserRepositoryViewData>,
    val topRepositories: List<UserRepositoryViewData>,
    val starredRepositories: List<UserRepositoryViewData>
)
