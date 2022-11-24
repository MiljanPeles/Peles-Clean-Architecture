package rs.peles.presentation.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.peles.presentation.base.BaseFragment
import rs.peles.presentation.databinding.FragmentUserBinding

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>() {

    private val userViewModel: UserViewModel by viewModels()

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserBinding
        get() = FragmentUserBinding::inflate


    override fun prepareView(savedInstanceState: Bundle?) {
        subscribeObservers()

        userViewModel.onTriggerEvent(UserIntent.GetUsers)
    }

    private fun subscribeObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.userList.collect {
                when (it) {
                    is UserViewModel.GetUserListUiState.Empty -> {

                    }
                    is UserViewModel.GetUserListUiState.Error -> {

                    }
                    is UserViewModel.GetUserListUiState.Success -> {

                    }
                    UserViewModel.GetUserListUiState.Loading -> {

                    }
                }
            }
        }

    }

}