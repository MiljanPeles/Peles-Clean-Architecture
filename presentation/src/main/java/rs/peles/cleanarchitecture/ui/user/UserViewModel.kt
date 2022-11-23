package rs.peles.cleanarchitecture.ui.user

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import rs.peles.cleanarchitecture.base.BaseViewModel
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.usecase.GetSpecificUser
import rs.peles.domain.usecase.GetUsers
import rs.peles.domain.util.PResource
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getSpecificUser: GetSpecificUser,
    private val getUsers: GetUsers
): BaseViewModel<UserIntent>() {

    private val _specificUser = MutableSharedFlow<GetUserUiState>()
    val specificUser = _specificUser.asSharedFlow()

    private val _userList = MutableSharedFlow<GetUserListUiState>()
    val userList = _userList.asSharedFlow()


    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {

            getUsers.invoke().collect {
                when (it) {
                    is PResource.Error -> {
                        _userList.emit(GetUserListUiState.Error(it.exception?.message!!))  // Todo handle exceptions
                    }
                    is PResource.Loading -> {
                        _userList.emit(GetUserListUiState.Loading)
                    }
                    is PResource.Success -> {
                        _userList.emit(GetUserListUiState.Success(it.data!!))
                    }
                }
            }
        }
    }

    private fun getSpecificUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            getSpecificUser.invoke(GetUserRequest(userId)).collect {
                when (it) {
                    is PResource.Error -> {
                        _specificUser.emit(GetUserUiState.Error(it.exception?.message!!)) // Todo handle exceptions
                    }
                    is PResource.Loading -> {
                        _specificUser.emit(GetUserUiState.Loading)
                    }
                    is PResource.Success -> {
                        _specificUser.emit(GetUserUiState.Success(it.data!!))
                    }
                }
            }
        }
    }

    sealed class GetUserUiState {
        object Empty : GetUserUiState()
        object Loading : GetUserUiState()
        data class Success(val user: User?) : GetUserUiState()
        data class Error(val message: String) : GetUserUiState()
    }

    sealed class GetUserListUiState {
        object Empty : GetUserListUiState()
        object Loading : GetUserListUiState()
        data class Success(val users: List<User> = emptyList()) : GetUserListUiState()
        data class Error(val message: String) : GetUserListUiState()
    }


    override fun onTriggerEvent(eventType: UserIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (eventType) {
                is UserIntent.GetSpecificUser -> getSpecificUser(eventType.id)
                is UserIntent.GetUsers -> getUsers()
            }
        }
    }

}