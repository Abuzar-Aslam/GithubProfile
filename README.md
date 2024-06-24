# GitHub Profile Android App

## Overview

This Android application fetches and displays GitHub user profile information using the GitHub GraphQL API. It shows the user's main information, pinned repositories, top repositories, and starred repositories.

## Features

- Fetches and displays GitHub user profile information.
- Displays user details including avatar, name, company, email, followers, and following count.
- Lists pinned repositories, top repositories, and starred repositories.
- Implements pull-to-refresh to update data.
- Caches data for offline access and performance improvement.

## Technologies Used

- **Kotlin:** The primary language used.
- **GraphQL:** For fetching GitHub data.
- **Apollo GraphQL:** For GraphQL client.
- **Dagger Hilt:** For dependency injection.
- **Jetpack Compose:** For building the UI.
- **Coil:** For image loading.
- **Coroutines:** For asynchronous operations.
- **Chucker:** For network request logging.

## Architecture

- **MVVM (Model-View-ViewModel):** Architecture pattern used for structuring the project.
- **Multi-module:** The project is organized into different modules for separation of concerns and better maintainability.

## Project Structure

- **app:** Contains main App class, MainActivity, and navigation.
- **data:** Contains data sources, repositories, and network configurations.
- **domain:** Contains domain models and use cases.
- **presentation:** Contains ViewModels, UI components, and utility classes.
- **network:** Contains GraphQL client, HttpClientFactory, request interceptors, GraphQL query, and schema.
- **design:** Contains spacing, vector images, colors, typography, and application theme.

## Installation

**Clone the repository:**

```bash
git clone https://github.com/Abuzar-Aslam/GithubProfile.git
cd github-profile-app
```

**Open the project in Android Studio.**

**Set up your GitHub API key:**

Open the system-level `gradle.properties` file and add your API key:

```properties
apiKey=YOUR_GITHUB_API_KEY
```

Build and run the project on an Android device or emulator.

## Usage

- The app will fetch and display a hardcoded user's GitHub profile information.
- You can pull down on the screen to refresh the data.
- The data is cached and will be available offline for a day.

## Code Documentation

### Network Layer:

- **GraphQLClient:** Handles GraphQL queries with caching and error handling.
- **HttpClientFactory:** Initializes OkHttpClient with interceptors.
- **AuthorizationInterceptor:** Adds authorization headers to requests.
- **InvalidProxyPortInterceptor:** Handles invalid proxy port issues.
- **FailedRequestInterceptor:** Handles failed requests and logs errors.

### Data Layer:

- **UserProfileRepositoryImpl:** Implementation of the user profile repository.
- **UserProfileCall:** GraphQL operation for fetching user profile data.
- **UserProfileQueryMapper:** Maps GraphQL query results to domain models.

### Domain Layer:

- **UserProfileModel:** Domain model for user profile.
- **RepositoryModel:** Domain model for repositories.
- **UserProfileUseCase:** Use case for fetching user profile data.
- **UserProfileRepository:** Define the repository data points for the data module.

### Presentation Layer:

- **HomeViewModel:** ViewModel for managing home screen state.
- **HomeScreenState:** Sealed class for representing home screen state.
- **HomeScreen:** Composable function for rendering the home screen.
- **ProfileHeader:** Compo![img.png](img.png)sable function for rendering the profile header.
- **RepositoryList:** Composable function for rendering repository lists.
- **TopAppBarUI:** Composable function for rendering the top app bar.

### Design Layer:

- **Spacing:** Defines standard spacing values used throughout the application.
- **Dimens:** Defines dimension values for consistent dimensions in the UI.
- **Colors:** Defines color palette for the application.
- **Typography:** Defines text styles used throughout the application.
- **Theme:** Provides theming support for the application.

## Points of Improvement

- **Error Handling:** Improve error handling in the UI to display user-friendly messages.

## Contributing

If you wish to contribute to this project, please fork the repository and submit pull requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
