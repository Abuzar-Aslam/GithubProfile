package home.assignemnt.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import home.assignemnt.data.network.AuthorizationInterceptor
import home.assignemnt.data.network.GraphQLClient
import home.assignemnt.data.network.GraphQLInterceptor
import home.assignemnt.data.datasource.UserProfileRepositoryImpl
import home.assignemnt.domain.repository.UserProfileRepository
import javax.inject.Singleton

/**
 * Dagger module to provide dependencies related to data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    /**
     * Provides a singleton instance of [GraphQLClient].
     *
     * @param context The application context.
     * @param graphQLInterceptors A set of GraphQL interceptors.
     * @return A configured [GraphQLClient] instance.
     */
    @Provides
    @Singleton
    fun provideGraphQLClient(
        @ApplicationContext context: Context,
        graphQLInterceptors: Set<@JvmSuppressWildcards GraphQLInterceptor>,
    ): GraphQLClient {
        return GraphQLClient(context, graphQLInterceptors)
    }

    /**
     * Provides a singleton instance of [UserProfileRepository].
     *
     * @param graphQLClient The GraphQL client used to execute GraphQL operations.
     * @return An implementation of [UserProfileRepository].
     */
    @Provides
    @Singleton
    fun provideUserProfileRepository(
        graphQLClient: GraphQLClient
    ): UserProfileRepository {
        return UserProfileRepositoryImpl(graphQLClient)
    }

    /**
     * Provides a singleton instance of [AuthorizationInterceptor] as a GraphQLInterceptor.
     * This interceptor adds authorization headers to GraphQL requests.
     *
     * @return An instance of [AuthorizationInterceptor].
     */
    @Provides
    @Singleton
    @IntoSet
    fun provideAuthorizationInterceptor(): GraphQLInterceptor {
        return AuthorizationInterceptor()
    }
}
