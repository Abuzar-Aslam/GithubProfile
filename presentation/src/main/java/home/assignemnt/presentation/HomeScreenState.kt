package home.assignemnt.presentation

import home.assignemnt.presentation.model.UserProfileViewData

/**
 * A sealed class representing the state of the home screen.
 */
sealed class HomeScreenState {

    /**
     * Represents the loading state of the home screen.
     */
    data object Loading: HomeScreenState()

    /**
     * Represents the `Error` state of the home screen.
     */
    data object Error: HomeScreenState()

    /**
     * Represents the content state of the home screen with user profile data.
     *
     * @property userProfileViewData The user profile data to be displayed.
     */
    data class Content(val userProfileViewData: UserProfileViewData): HomeScreenState()
}
