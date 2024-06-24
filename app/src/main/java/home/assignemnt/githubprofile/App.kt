package home.assignemnt.githubprofile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main Application class for the app.
 *
 * This class is annotated with @HiltAndroidApp to trigger Hilt's code generation,
 * including a base class for the application that uses Dagger for dependency injection.
 *
 * By extending Application, this class serves as the entry point for the application.
 */
@HiltAndroidApp
class App : Application()
