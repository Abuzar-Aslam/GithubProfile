package home.assignemnt.githubprofile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import home.assignemnt.presentation.ui.HomeScreen

/**
 * A composable function that sets up the navigation graph for the application.
 *
 * This function uses Jetpack Compose's navigation component to define the navigation
 * routes and their corresponding destinations within the app.
 *
 * @param navHostController The navigation controller for the navigation host.
 */
@Composable
fun Navigator(navHostController: NavHostController = rememberNavController()) {

    // Define the navigation graph
    NavHost(navController = navHostController, startDestination = Destinations.HOME) {
        // Define the composable for the home screen
        composable(route = Destinations.HOME) {
            HomeScreen()
        }
    }
}