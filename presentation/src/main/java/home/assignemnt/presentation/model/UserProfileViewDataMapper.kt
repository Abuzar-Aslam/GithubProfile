package home.assignemnt.presentation.model

import home.assignemnt.domain.model.RepositoryModel
import home.assignemnt.domain.model.UserProfileModel

/**
 * Extension function to convert a [UserProfileModel] to a [UserProfileViewData].
 *
 * @receiver The [UserProfileModel] instance to be converted.
 * @return A [UserProfileViewData] instance with mapped properties from the [UserProfileModel].
 */
internal fun UserProfileModel.toUserProfileModelViewData(): UserProfileViewData {
    return UserProfileViewData(
        avatarUrl = avatarUrl,
        name = name,
        company = company,
        email = email,
        followers = followers,
        following = following,
        pinnedRepositories = pinnedRepositories.toRepositoriesViewData(),
        topRepositories = topRepositories.toRepositoriesViewData(),
        starredRepositories = starredRepositories.toRepositoriesViewData(),
    )
}

/**
 * Extension function to convert a list of [RepositoryModel] to a list of [UserRepositoryViewData].
 *
 * @receiver The list of [RepositoryModel] instances to be converted.
 * @return A list of [UserRepositoryViewData] instances with mapped properties from the [RepositoryModel].
 */
private fun List<RepositoryModel>.toRepositoriesViewData(): List<UserRepositoryViewData> {
    return this.map { item ->
        UserRepositoryViewData(
            name = item.name,
            description = item.description,
            stargazerCount = item.stargazerCount,
            language = item.language
        )
    }
}
