package home.assignemnt.presentation

import home.assignemnt.domain.model.UserProfileModel
import home.assignemnt.domain.usecases.UserProfileUseCase
import home.assignemnt.presentation.model.toUserProfileModelViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [HomeViewModel].
 *
 * This class tests the behavior of the HomeViewModel, ensuring that it correctly updates
 * its state based on the results of the UserProfileUseCase.
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @Mock
    private lateinit var userProfileUseCase: UserProfileUseCase

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the test environment before each test.
     * Initializes the mocks and sets the main dispatcher to a test dispatcher.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(userProfileUseCase)
    }

    /**
     * Cleans up the test environment after each test.
     * Resets the main dispatcher.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that [HomeViewModel.fetchUserProfile] updates the content and isRefreshing states.
     */
    @Test
    fun `fetchUserProfile should update content and isRefreshing states`() = runTest {
        val userProfile = UserProfileModel(
            avatarUrl = "url",
            name = "name",
            company = "company",
            email = "email",
            followers = 100,
            following = 50,
            pinnedRepositories = emptyList(),
            topRepositories = emptyList(),
            starredRepositories = emptyList()
        )
        val userProfileViewData = userProfile.toUserProfileModelViewData()

        `when`(userProfileUseCase("Abuzar-Aslam", true)).thenReturn(flow {
            emit(userProfile)
        })

        viewModel.fetchUserProfile()

        advanceUntilIdle()

        assertEquals(HomeScreenState.Content(userProfileViewData), viewModel.content.value)
        assertEquals(false, viewModel.isRefreshing.value)
    }

    /**
     * Tests that [HomeViewModel.onRefresh] updates the isRefreshing state and fetches new data.
     */
    @Test
    fun `onRefresh should update isRefreshing state and fetch new data`() = runTest {
        val userProfile = UserProfileModel(
            avatarUrl = "url",
            name = "name",
            company = "company",
            email = "email",
            followers = 100,
            following = 50,
            pinnedRepositories = emptyList(),
            topRepositories = emptyList(),
            starredRepositories = emptyList()
        )
        val userProfileViewData = userProfile.toUserProfileModelViewData()

        `when`(userProfileUseCase("Abuzar-Aslam", false)).thenReturn(flow {
            emit(userProfile)
        })

        viewModel.onRefresh()

        assertEquals(true, viewModel.isRefreshing.value)

        advanceUntilIdle()

        assertEquals(HomeScreenState.Content(userProfileViewData), viewModel.content.value)
        assertEquals(false, viewModel.isRefreshing.value)
    }

    /**
     * Tests that [HomeViewModel.onRetry] sets the Loading state and fetches new data.
     */
    @Test
    fun `onRetry should set Loading state and fetch new data`() = runTest {
        val userProfile = UserProfileModel(
            avatarUrl = "url",
            name = "name",
            company = "company",
            email = "email",
            followers = 100,
            following = 50,
            pinnedRepositories = emptyList(),
            topRepositories = emptyList(),
            starredRepositories = emptyList()
        )
        val userProfileViewData = userProfile.toUserProfileModelViewData()

        `when`(userProfileUseCase("Abuzar-Aslam", true)).thenReturn(flow {
            emit(userProfile)
        })

        viewModel.onRetry()

        advanceUntilIdle()

        assertEquals(HomeScreenState.Content(userProfileViewData), viewModel.content.value)
        assertEquals(false, viewModel.isRefreshing.value)
    }
}
