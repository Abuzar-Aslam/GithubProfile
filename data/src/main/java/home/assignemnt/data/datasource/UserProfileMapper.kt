package home.assignemnt.data.datasource

import home.assignemnt.domain.model.RepositoryModel
import home.assignemnt.domain.model.UserProfileModel
import home.assignment.data.UserProfileQuery

/**
 * Extension function to convert a [UserProfileQuery.User] object to a [UserProfileModel].
 *
 * @return A [UserProfileModel] containing the mapped data.
 */
internal fun UserProfileQuery.User.toUserProfileModel(): UserProfileModel {
    return UserProfileModel(
        avatarUrl = avatarUrl.toString(),
        name = name,
        email = email,
        company = company,
        followers = followers.totalCount,
        following = following.totalCount,
        pinnedRepositories = pinnedItems.toPinnedRepositories(),
        topRepositories = topRepositories.toRepositoryModels(),
        starredRepositories = starredRepositories.toRepositoryModels()
    )
}

/**
 * Extension function to convert a [UserProfileQuery.PinnedItems] object to a list of [RepositoryModel].
 *
 * @return A list of [RepositoryModel] containing the pinned repositories.
 */
private fun UserProfileQuery.PinnedItems.toPinnedRepositories(): List<RepositoryModel> {
    return nodes?.mapNotNull { it?.onRepository?.toRepositoryModel() } ?: emptyList()
}

/**
 * Extension function to convert a [UserProfileQuery.StarredRepositories] object to a list of [RepositoryModel].
 *
 * @return A list of [RepositoryModel] containing the starred repositories.
 */
private fun UserProfileQuery.StarredRepositories.toRepositoryModels(): List<RepositoryModel> {
    return nodes?.mapNotNull { it?.toRepositoryModel() } ?: emptyList()
}

/**
 * Extension function to convert a [UserProfileQuery.TopRepositories] object to a list of [RepositoryModel].
 *
 * @return A list of [RepositoryModel] containing the top repositories.
 */
private fun UserProfileQuery.TopRepositories.toRepositoryModels(): List<RepositoryModel> {
    return nodes?.mapNotNull { it?.toRepositoryModel() } ?: emptyList()
}

/**
 * Extension function to convert a [UserProfileQuery.Node2] object to a [RepositoryModel].
 *
 * @return A [RepositoryModel] containing the mapped data.
 */
private fun UserProfileQuery.Node2.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        name = name,
        description = description,
        stargazerCount = stargazerCount,
        language = primaryLanguage?.name
    )
}

/**
 * Extension function to convert a [UserProfileQuery.Node1] object to a [RepositoryModel].
 *
 * @return A [RepositoryModel] containing the mapped data.
 */
private fun UserProfileQuery.Node1.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        name = name,
        description = description,
        stargazerCount = stargazerCount,
        language = primaryLanguage?.name
    )
}

/**
 * Extension function to convert a [UserProfileQuery.OnRepository] object to a [RepositoryModel].
 *
 * @return A [RepositoryModel] containing the mapped data.
 */
private fun UserProfileQuery.OnRepository.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        name = name,
        description = description,
        stargazerCount = stargazerCount,
        language = primaryLanguage?.name
    )
}
