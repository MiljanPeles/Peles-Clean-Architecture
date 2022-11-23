package rs.peles.cleanarchitecture.ui.user

import rs.peles.cleanarchitecture.base.BaseIntent

/**
 * UserIntent class to specify our events intentions.
 */
sealed class UserIntent : BaseIntent() {

    data class GetSpecificUser(val id: String) : UserIntent()

    object GetUsers : UserIntent()
}
