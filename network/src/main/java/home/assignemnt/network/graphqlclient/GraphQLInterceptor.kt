package home.assignemnt.network.graphqlclient

import okhttp3.Interceptor

/**
 * Interface for GraphQL-specific interceptors.
 * This interface extends OkHttp's [Interceptor] to provide a common type for interceptors used in GraphQL operations.
 */
interface GraphQLInterceptor : Interceptor
