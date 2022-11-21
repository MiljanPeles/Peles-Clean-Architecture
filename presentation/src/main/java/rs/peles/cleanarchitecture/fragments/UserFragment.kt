package rs.peles.cleanarchitecture.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.peles.cleanarchitecture.MainActivity
import rs.peles.cleanarchitecture.R
import rs.peles.cleanarchitecture.databinding.FragmentUserBinding
import rs.peles.cleanarchitecture.viewmodel.UserViewModel

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding
        get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        userViewModel.getUsers()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}