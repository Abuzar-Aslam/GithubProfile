package home.assignemnt.data.datasource

import home.assignemnt.data.network.GraphQLOperation
import home.assignemnt.domain.model.UserProfileModel
import home.assignment.data.UserProfileQuery

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
