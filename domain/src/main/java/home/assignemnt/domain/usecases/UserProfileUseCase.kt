package home.assignemnt.domain.usecases

import home.assignemnt.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for fetching user profile data.
 *
 * @property userProfileRepository The repository to fetch user profile data from.
 */
class UserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {

    /**
     * Executes the use case to fetch the user profile data.
     *
     * @param userName The username of the user whose profile data is to be fetched.
     * @param useCache Indicates whether to use the cache for this operation.
     * @return A flow emitting the user's profile data.
     */
    operator fun invoke(userName: String, useCache: Boolean) = flow {
        emit(userProfileRepository.getUserProfile(userName = userName, useCache = useCache))
    }
}
