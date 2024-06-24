package home.assignemnt.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import home.assignemnt.data.datasource.UserProfileRepositoryImpl
import home.assignemnt.domain.repository.UserProfileRepository
import home.assignemnt.network.graphqlclient.GraphQLClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

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
}