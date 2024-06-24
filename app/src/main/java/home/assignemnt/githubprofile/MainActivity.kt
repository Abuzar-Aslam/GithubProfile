package home.assignemnt.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import home.assignemnt.design.theme.GitHubProfileTheme
import home.assignemnt.githubprofile.navigation.Navigator

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
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
