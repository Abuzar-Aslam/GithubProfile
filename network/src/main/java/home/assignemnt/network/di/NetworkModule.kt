package home.assignemnt.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import home.assignemnt.network.interceptors.AuthorizationInterceptor
import home.assignemnt.network.graphqlclient.GraphQLClient
import home.assignemnt.network.graphqlclient.GraphQLInterceptor
import javax.inject.Singleton

/**
 * Dagger module to provide dependencies related to network layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

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
