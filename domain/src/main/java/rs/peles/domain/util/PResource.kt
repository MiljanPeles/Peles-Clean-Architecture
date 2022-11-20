package rs.peles.domain.util


sealed class PResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : PResource<T>(data)
    class Error<T>(message: String, data: T? = null) : PResource<T>(data, message)
    class Loading<T>(data: T? = null) : PResource<T>(data)
}