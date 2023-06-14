package rs.peles.presentation.ui.user

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rs.peles.presentation.MainActivity
import rs.peles.presentation.R
import rs.peles.presentation.base.BaseFragment
import rs.peles.presentation.databinding.FragmentSignUpBinding
import rs.peles.presentation.util.Consts
import rs.peles.presentation.util.FileCreator
import rs.peles.presentation.util.executeAsyncTask
import rs.peles.presentation.util.hide
import rs.peles.presentation.util.isValidEmail
import rs.peles.presentation.util.isValidPassword
import rs.peles.presentation.util.show
import rs.peles.presentation.util.showToast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding
        get() = FragmentSignUpBinding::inflate

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            showToast(getString(R.string.string_grant_permission))
        }
    }

    private val captureImageActivityForResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                picturePath?.let {
                    val originalImageUri = Uri.fromFile(File(it))
                    // Async image compressing
                    lifecycleScope.executeAsyncTask(onPreExecute = {
                        (requireActivity() as MainActivity).showProgressBar(true)
                    }, doInBackground = {
                        val b = processBitmap(originalImageUri)
                        b // send it to onPostExecute
                    }, onPostExecute = { bitmap ->
                        (requireActivity() as MainActivity).showProgressBar(false)
                        binding.avatarImage.show()
                        binding.tapToAdd.hide()
                        binding.avatarImage.setImageBitmap(bitmap)

                        // Save to internal storage for caching
                        signUpViewModel.onTriggerEvent(
                            SignUpIntent.SaveBitmap(
                                storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                bitmapImage = bitmap,
                                originalImageUri = originalImageUri
                            )
                        )
                    })
                }

            }
        }

    private val signUpViewModel: SignUpViewModel by viewModels()
    private var picturePath: String? = null
    private var currentPhotoUri: Uri? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        subscribeObservers()

        binding.submit.setOnClickListener { signUp() }
        binding.tapToAdd.setOnClickListener { captureImage() }
    }

    private fun subscribeObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            signUpViewModel.registerUser.collect {
                when (it) {
                    is SignUpViewModel.RegisterUserUiState.Empty -> { }
                    is SignUpViewModel.RegisterUserUiState.Error -> {
                        (requireActivity() as MainActivity).showProgressBar(false)
                        showToast(getString(it.message))
                    }
                    is SignUpViewModel.RegisterUserUiState.Success -> {
                        (requireActivity() as MainActivity).showProgressBar(false)

                        showToast(getString(R.string.string_successfully_registered))

                        // In real scenario photo would be URL link
                        val currentPhoto: String = if (currentPhotoUri != null) currentPhotoUri.toString() else ""

                        // Navigate to ConfirmationFragment and pass data
                        it.user?.let { user ->
                            val action = SignUpFragmentDirections.actionSignUpFragmentToConfirmationFragment(
                                user.name,
                                user.email,
                                user.website,
                                currentPhoto
                            )
                            findNavController().navigate(action)
                        }
                    }
                    SignUpViewModel.RegisterUserUiState.Loading -> {
                        (requireActivity() as MainActivity).showProgressBar(true)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            signUpViewModel.saveBitmap.collect {
                when (it) {
                    is SignUpViewModel.SaveBitmapUiState.Error -> showToast(it.message)
                    is SignUpViewModel.SaveBitmapUiState.Success -> {

                        // Save current photo
                        currentPhotoUri = it.savedImageUri

                        // Delete original full size image after successfully saved compressed image
                        signUpViewModel.onTriggerEvent(
                            SignUpIntent.DeleteBitmap(
                                uri = it.originalImageUri
                            )
                        )

                    }
                }
            }
        }

    }

    private fun signUp() {
        var validInput = true
        binding.inputEmailAddress.isErrorEnabled = false
        binding.inputPassword.isErrorEnabled = false

        val name = binding.firstName.text.toString().ifEmpty { null }
        val email = binding.emailAddress.text.toString()
        val password = binding.password.text.toString()
        val website = binding.website.text.toString().ifEmpty { null }

        if (!email.isValidEmail()) {
            binding.inputEmailAddress.isErrorEnabled = true
            binding.inputEmailAddress.error = getString(R.string.string_email_not_valid)
            validInput = false
        }

        if (!password.isValidPassword(Consts.PASSWORD_MIN_LEN, Consts.PASSWORD_CLASSES)) {
            binding.inputPassword.isErrorEnabled = true
            binding.inputPassword.error = String.format(Locale.getDefault(), getString(R.string.string_password_validation), Consts.PASSWORD_MIN_LEN, Consts.PASSWORD_CLASSES)
            validInput = false
        }

        if (email.isEmpty()) {
            binding.inputEmailAddress.isErrorEnabled = true
            binding.inputEmailAddress.error = getString(R.string.string_fill_up_email)
            validInput = false
        }

        if (password.isEmpty()) {
            binding.inputPassword.isErrorEnabled = true
            binding.inputPassword.error = getString(R.string.string_fill_up_password)
            validInput = false
        }

        if (validInput) {
            signUpViewModel.onTriggerEvent(
                SignUpIntent.RegisterUser(
                    photoBase64 = "", // In real scenario we would convert bitmap to base64 String
                    name = name,
                    email = email,
                    password = password,
                    website = website
                )
            )
        }
    }

    private fun captureImage() {
        val hasPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else openCamera()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = FileCreator.createImageFile(requireContext())
                picturePath = photoFile.absolutePath
            } catch (ex: IOException) {
                Log.e("FILE_PROVIDER", "Exception while creating file $ex")
                showToast(getString(R.string.string_basic_error))
            }
            if(photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "rs.peles.cleanarchitecture.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                captureImageActivityForResultLauncher.launch(takePictureIntent)
            }
        }
    }

    private fun processBitmap(uri: Uri) : Bitmap {
        var b = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        }
        //b = BitmapHelper.getResizedBitmap(b)
        //b = BitmapHelper.compressBitmap(b)
        val bytes = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
        b = BitmapFactory.decodeStream(ByteArrayInputStream(bytes.toByteArray()))
        return b
    }

}