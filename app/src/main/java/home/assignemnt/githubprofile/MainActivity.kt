package home.assignemnt.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import home.assignemnt.design.theme.GitHubProfileTheme
import home.assignemnt.githubprofile.navigation.Navigator

/**
 * MainActivity is the entry point of the application.
 *
 * This activity is responsible for setting up the initial UI and applying the app theme.
 * It is annotated with @AndroidEntryPoint to integrate Hilt's dependency injection.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubProfileTheme {
                Navigator()
            }
        }
    }
}
