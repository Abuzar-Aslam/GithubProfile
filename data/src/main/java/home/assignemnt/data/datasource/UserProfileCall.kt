package home.assignemnt.data.datasource

import home.assignemnt.domain.model.UserProfileModel
import home.assignemnt.network.graphqlclient.GraphQLOperation
import home.assignment.network.UserProfileQuery

/**
 * A GraphQL operation to fetch user profile data.
 *
 * @param userName The username of the user whose profile data is to be fetched.
 * @param useCache Indicates whether to use the cache for this operation.
 */
internal class UserProfileCall(userName: String, useCache: Boolean) :
    GraphQLOperation<UserProfileModel, UserProfileQuery.Data>(
        query = UserProfileQuery(username = userName),
        transform = {
            user?.toUserProfileModel()
        },
        useCache = useCache
    )
