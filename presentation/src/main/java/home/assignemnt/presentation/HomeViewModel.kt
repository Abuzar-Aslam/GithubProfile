package home.assignemnt.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import home.assignemnt.domain.usecases.UserProfileUseCase
import home.assignemnt.presentation.model.toUserProfileModelViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state of the home screen.
 *
 * @property userProfileUseCase The use case for fetching user profile data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val userProfileUseCase: UserProfileUseCase): ViewModel() {

    // StateFlow for tracking if the content is refreshing.
    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    // StateFlow for tracking the content state of the home screen.
    private val _content: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val content: StateFlow<HomeScreenState> = _content

    // Initial data fetch when the ViewModel is created.
    init {
        fetchUserProfile()
    }

    /**
     * Fetches the user profile data.
     *
     * @param useCache Boolean flag indicating whether to use cached data.
     */
    @VisibleForTesting
    fun fetchUserProfile(useCache: Boolean = true) {
        viewModelScope.launch {
            userProfileUseCase(userName = "Abuzar-Aslam", useCache = useCache)
                .catch {
                    _content.value = HomeScreenState.Error
                }
                .collect { result ->
                    _content.value = HomeScreenState.Content(result.toUserProfileModelViewData())
                    _isRefreshing.value = false
                }
        }
    }

    /**
     * Initiates a refresh of the user profile data.
     */
    fun onRefresh() {
        _isRefreshing.value = true
        fetchUserProfile(false)
    }

    /**
     * Retries fetching the user profile data in case of an error.
     */
    fun onRetry() {
        _content.value = HomeScreenState.Loading
        fetchUserProfile()
    }
}
