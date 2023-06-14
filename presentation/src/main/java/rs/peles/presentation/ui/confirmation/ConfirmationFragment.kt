package rs.peles.presentation.ui.confirmation

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.peles.presentation.base.BaseFragment
import rs.peles.presentation.databinding.FragmentConfirmationBinding
import rs.peles.presentation.databinding.FragmentSignUpBinding
import rs.peles.presentation.ui.user.SignUpIntent
import rs.peles.presentation.ui.user.SignUpViewModel
import rs.peles.presentation.util.executeAsyncTask
import rs.peles.presentation.util.hide
import rs.peles.presentation.util.showToast

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    private val confirmationViewModel: ConfirmationViewModel by viewModels()

    private val args: ConfirmationFragmentArgs by navArgs()

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConfirmationBinding
        get() = FragmentConfirmationBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        subscribeObservers()
        setUi()
    }

    private fun setUi() {

        binding.headerTitle.text = if (args.name == "") "Hello" else "Hello, ${args.name}!"
        binding.website.text = args.website
        binding.name.text = args.name
        binding.email.text = args.email

        if (args.website == "") binding.website.hide()
        if (args.name == "") binding.name.hide()

        if (args.photo != "") {
            lifecycleScope.executeAsyncTask(
                onPreExecute = { },
                doInBackground = {
                    val b = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(requireActivity().contentResolver, Uri.parse(args.photo))
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, Uri.parse(args.photo))
                    }
                    b // send to onPostExecute
                }, onPostExecute = {
                    binding.avatarImage.setImageBitmap(it)

                    // After image is set, delete it from internal storage to avoid filling storage on this demo app
                    confirmationViewModel.onTriggerEvent(
                        ConfirmationIntent.DeleteBitmap(
                            uri = Uri.parse(args.photo)
                        )
                    )
                })
        }
    }

    private fun subscribeObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            confirmationViewModel.deleteBitmap.collect {
                when (it) {
                    is ConfirmationViewModel.DeleteBitmapUiState.Error -> { }
                    ConfirmationViewModel.DeleteBitmapUiState.Success -> { }
                }
            }
        }

    }

}