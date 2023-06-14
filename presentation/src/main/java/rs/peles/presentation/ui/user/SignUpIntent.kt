package rs.peles.presentation.ui.user

import android.graphics.Bitmap
import android.net.Uri
import rs.peles.presentation.base.BaseIntent
import java.io.File

/**
 * SignUpIntent class to specify our events intentions.
 */
sealed class SignUpIntent : BaseIntent() {
    data class RegisterUser(val photoBase64: String?, val name: String?, val email: String, val password: String, val website: String?) : SignUpIntent()

    data class SaveBitmap(val storageDir: File?, val bitmapImage: Bitmap, val originalImageUri: Uri): SignUpIntent()

    data class DeleteBitmap(val uri: Uri): SignUpIntent()
}
