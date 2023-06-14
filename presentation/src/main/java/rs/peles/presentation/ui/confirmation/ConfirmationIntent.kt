package rs.peles.presentation.ui.confirmation

import android.net.Uri
import rs.peles.presentation.base.BaseIntent

/**
 * SignUpIntent class to specify our events intentions.
 */
sealed class ConfirmationIntent : BaseIntent() {

    data class DeleteBitmap(val uri: Uri): ConfirmationIntent()
}
