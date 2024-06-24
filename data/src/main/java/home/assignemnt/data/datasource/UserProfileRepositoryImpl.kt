package home.assignemnt.data.datasource

import home.assignemnt.network.graphqlclient.GraphQLClient
import home.assignemnt.domain.model.UserProfileModel
import home.assignemnt.domain.repository.UserProfileRepository

/**
 * Implementation of the [UserProfileRepository] interface to fetch user profile data.
 *
 * @param graphQLClient The GraphQL client used to execute GraphQL operations.
 */
class UserProfileRepositoryImpl(
    private val graphQLClient: GraphQLClient
) : UserProfileRepository {

    /**
     * Fetches the user profile data for a given username.
     *
     * @param userName The username of the user whose profile data is to be fetched.
     * @param useCache Indicates whether to use the cache for this operation.
     * @return The [UserProfileModel] containing the user's profile data.
     */
    override suspend fun getUserProfile(userName: String, useCache: Boolean): UserProfileModel {
        return graphQLClient.operationCall(UserProfileCall(userName = userName, useCache = useCache))
    }
}
