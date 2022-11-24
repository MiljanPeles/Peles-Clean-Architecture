package rs.peles.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * BaseViewModel class for all [ViewModel] instances.
 */
@Suppress("PropertyName", "UNCHECKED_CAST")
abstract class BaseViewModel<T: BaseIntent> : ViewModel() {

    private var _intent: BaseIntent? = null

    protected val event: T
        get() = _intent as T

    abstract fun onTriggerEvent(eventType: T)
}