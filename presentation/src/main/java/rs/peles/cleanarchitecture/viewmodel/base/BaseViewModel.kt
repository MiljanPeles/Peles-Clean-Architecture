package rs.peles.cleanarchitecture.viewmodel.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    fun handleException(action: (Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            action(exception)
        }
    }

}