package rs.peles.domain.util


sealed class PResource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : PResource<T>(data)
    class Error<T>(e: Exception) : PResource<T>(exception = e)
    class Loading<T>(data: T? = null) : PResource<T>(data)
}