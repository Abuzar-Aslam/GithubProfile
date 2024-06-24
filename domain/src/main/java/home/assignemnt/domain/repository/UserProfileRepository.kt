package home.assignemnt.domain.repository

import home.assignemnt.domain.model.UserProfileModel

/**
 * Interface representing a repository for fetching user profile data.
 */
interface UserProfileRepository {

    /**
     * Fetches the user profile data for a given username.
     *
     * @param userName The username of the user whose profile data is to be fetched.
     * @param useCache Indicates whether to use the cache for this operation.
     * @return The [UserProfileModel] containing the user's profile data.
     */
    suspend fun getUserProfile(userName: String, useCache: Boolean): UserProfileModel
}
