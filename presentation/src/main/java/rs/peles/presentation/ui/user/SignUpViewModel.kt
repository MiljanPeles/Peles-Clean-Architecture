package rs.peles.presentation.ui.user

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import rs.peles.presentation.base.BaseViewModel
import rs.peles.domain.model.User
import rs.peles.domain.model.request.RegisterUserRequest
import rs.peles.domain.usecase.RegisterUserUseCase
import rs.peles.domain.util.PResource
import rs.peles.presentation.util.Consts
import rs.peles.presentation.util.ErrorMessageFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
): BaseViewModel<SignUpIntent>() {

    private val _registerUser = MutableSharedFlow<RegisterUserUiState>()
    val registerUser = _registerUser.asSharedFlow()

    private val _saveBitmap = MutableSharedFlow<SaveBitmapUiState>()
    val saveBitmap = _saveBitmap.asSharedFlow()

    private val _deleteBitmap = MutableSharedFlow<DeleteBitmapUiState>()
    val deleteBitmap = _deleteBitmap.asSharedFlow()

    private fun registerUser(photoBase64: String?, name: String?, email: String, password: String, website: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            registerUserUseCase.invoke(
                RegisterUserRequest(
                    name = name,
                    email = email,
                    password = password,
                    website = website,
                    photoBase64 = photoBase64
                )
            ).collect {
                when (it) {
                    is PResource.Error -> {
                        it.exception?.let { exception ->
                            _registerUser.emit(RegisterUserUiState.Error(ErrorMessageFactory.create(e = exception)))
                        }
                    }
                    is PResource.Loading -> {
                        _registerUser.emit(RegisterUserUiState.Loading)
                    }
                    is PResource.Success -> {
                        it.data?.let { user ->
                            _registerUser.emit(RegisterUserUiState.Success(user))
                        }
                    }
                }
            }
        }
    }

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

    private fun saveToInternalStorage(storageDir: File?, bitmapImage: Bitmap, fileName: String, originalImageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val myPath = File(storageDir, "$fileName.jpg")
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(myPath)
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Log.e("SAVE_IMAGE", "Successfully saved.")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SAVE_IMAGE", "Error: " + e.localizedMessage)
                _saveBitmap.emit(SaveBitmapUiState.Error(e.localizedMessage ?: ""))
            } finally {
                try {
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("SAVE_IMAGE", "Error: " + e.localizedMessage)
                    _saveBitmap.emit(SaveBitmapUiState.Error(e.localizedMessage ?: ""))
                }
            }
            _saveBitmap.emit(
                SaveBitmapUiState.Success(
                    savedImageUri = Uri.fromFile(myPath),
                    originalImageUri = originalImageUri
                )
            )
        }
    }

    sealed class RegisterUserUiState {
        object Empty : RegisterUserUiState()
        object Loading : RegisterUserUiState()
        data class Success(val user: User?) : RegisterUserUiState()
        data class Error(@StringRes val message: Int) : RegisterUserUiState()
    }

    sealed class SaveBitmapUiState {
        data class Success(val savedImageUri: Uri, val originalImageUri: Uri) : SaveBitmapUiState()
        data class Error(val message: String) : SaveBitmapUiState()
    }

    sealed class DeleteBitmapUiState {
        object Success: DeleteBitmapUiState()
        data class Error(val message: String) : DeleteBitmapUiState()
    }

    override fun onTriggerEvent(eventType: SignUpIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (eventType) {
                is SignUpIntent.RegisterUser -> registerUser(
                    photoBase64 = eventType.photoBase64,
                    name = eventType.name,
                    email = eventType.email,
                    password = eventType.password,
                    website = eventType.website
                )

                is SignUpIntent.SaveBitmap -> saveToInternalStorage(
                    storageDir = eventType.storageDir,
                    bitmapImage = eventType.bitmapImage,
                    originalImageUri = eventType.originalImageUri,
                    fileName = Consts.getRandomImageFileName()
                )

                is SignUpIntent.DeleteBitmap -> deleteFromInternalStorage(eventType.uri)
            }
        }
    }

}