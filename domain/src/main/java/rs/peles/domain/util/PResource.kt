package rs.peles.domain.util


/**
 * PResource class is a wrapper class to wrap th data between the app layers.
 */
sealed class PResource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : PResource<T>(data)
    class Error<T>(e: Exception) : PResource<T>(exception = e)
    class Loading<T>(data: T? = null) : PResource<T>(data)
}