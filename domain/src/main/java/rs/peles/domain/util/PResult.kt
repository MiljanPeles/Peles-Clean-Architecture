package rs.peles.domain.util

/**
 * For state management when we call some api
 */
sealed class PResult<T> {
    data class Success<T>(val data: T) : PResult<T>()
    data class Error<T>(val error: Throwable) : PResult<T>()
}

inline fun <T, R> PResult<T>.getResult(
    success: (PResult.Success<T>) -> R,
    error: (PResult.Error<T>) -> R
): R = if (this is PResult.Success) success(this) else error(this as PResult.Error)

inline fun <T> PResult<T>.onSuccess(
    block: (T) -> Unit
): PResult<T> = if (this is PResult.Success) also { block(data) } else this

inline fun <T> PResult<T>.onError(
    block: (Throwable) -> Unit
): PResult<T> = if (this is PResult.Error) also { block(error) } else this