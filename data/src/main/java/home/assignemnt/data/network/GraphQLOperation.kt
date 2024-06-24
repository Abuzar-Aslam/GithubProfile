package home.assignemnt.data.network

import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.http.HttpHeader

/**
 * Abstract class representing a GraphQL operation.
 *
 * @param ModelType The type of the model returned after the transformation.
 * @param DataType The type of data returned by the GraphQL operation.
 * @param query The GraphQL operation (query or mutation) to be executed.
 * @param transform A transformation function to convert the operation data and errors into the desired model type.
 * @param useCache Indicates whether the operation should use caching. Default is true.
 * @param headers Optional list of HTTP headers to be included in the request.
 */
abstract class GraphQLOperation<ModelType, DataType: Operation.Data>(
    val query: Operation<DataType>,
    val transform: DataType.(errors: List<Error>) -> ModelType?,
    val useCache: Boolean = true,
    val headers: List<HttpHeader>? = null
)
