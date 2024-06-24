package home.assignemnt.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import home.assignemnt.presentation.HomeScreenState
import home.assignemnt.presentation.HomeViewModel
import home.assignemnt.presentation.R
import home.assignemnt.design.util.Spacing

/**
 * Composable function representing the Home Screen.
 *
 * @param modifier A [Modifier] for this composable.
 * @param viewModel The [HomeViewModel] instance, provided by Hilt.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.content.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    // Trigger the refresh action when pull to refresh state is refreshing
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.onRefresh()
        }
    }

    // Update the pull to refresh state based on isRefreshing value
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    // Main UI structure using Scaffold
    Scaffold(
        topBar = {
            TopAppBarUI(title = R.string.profile_screen_title)
        }
    ) { paddingValues ->
        when (state) {
            // Show loading indicator while data is loading
            is HomeScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // Display Error when there is an error while fetching data
            HomeScreenState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ErrorState(onRetry = viewModel::onRetry)
                }
            }
            // Display user profile data when loaded
            is HomeScreenState.Content -> {
                val profile = (state as HomeScreenState.Content).userProfileViewData
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        modifier = modifier
                            .padding(start = Spacing.x4, end = Spacing.x4)
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    ) {
                        item {
                            ProfileHeader(profile)
                            Spacer(modifier = Modifier.height(Spacing.x4))
                        }
                        item {
                            RepositorySectionHeader(R.string.profile_screen_pinned_section_title)
                            VerticalRepositoryList(
                                profile.avatarUrl,
                                profile.company,
                                profile.pinnedRepositories
                            )
                            Spacer(modifier = Modifier.height(Spacing.x4))
                        }
                        item {
                            RepositorySectionHeader(R.string.profile_screen_top_repository_section_title)
                            HorizontalRepositoryList(
                                profile.avatarUrl,
                                profile.company,
                                profile.topRepositories
                            )
                            Spacer(modifier = Modifier.height(Spacing.x4))
                        }
                        item {
                            RepositorySectionHeader(R.string.profile_screen_starred_repository_section_title)
                            HorizontalRepositoryList(
                                profile.avatarUrl,
                                profile.company,
                                profile.starredRepositories
                            )
                        }
                    }
                    PullToRefreshContainer(
                        state = pullToRefreshState,
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                    )
                }
            }
        }
    }
}
