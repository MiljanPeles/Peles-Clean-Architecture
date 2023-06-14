package rs.peles.presentation.ui.confirmation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import rs.peles.presentation.base.BaseViewModel
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.usecase.GetSpecificUser
import rs.peles.domain.usecase.RegisterUserUseCase
import rs.peles.domain.util.PResource
import rs.peles.presentation.ui.user.SignUpViewModel
import rs.peles.presentation.util.ErrorMessageFactory
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(): BaseViewModel<ConfirmationIntent>() {

    private val _deleteBitmap = MutableSharedFlow<DeleteBitmapUiState>()
    val deleteBitmap = _deleteBitmap.asSharedFlow()

    private fun deleteFromInternalStorage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val f = File(uri.path!!)
                val success = f.delete()
                if (success) {
                    _deleteBitmap.emit(DeleteBitmapUiState.Success)
                } else {
                    _deleteBitmap.emit(DeleteBitmapUiState.Error("Deleting image error."))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _deleteBitmap.emit(DeleteBitmapUiState.Error(e.localizedMessage ?: ""))
            }
        }
    }

    sealed class DeleteBitmapUiState {
        object Success: DeleteBitmapUiState()
        data class Error(val message: String) : DeleteBitmapUiState()
    }

    override fun onTriggerEvent(eventType: ConfirmationIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (eventType) {
                is ConfirmationIntent.DeleteBitmap -> deleteFromInternalStorage(eventType.uri)
            }
        }
    }

}