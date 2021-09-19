package com.chapo.todo.registration.uploadpicture

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chapo.todo.common.utils.permissions.PermissionManager
import com.chapo.todo.databinding.UploadPictureFragmentBinding
import com.chapo.todo.registration.RegistrationViewModel
import com.chapo.todo.registration.Step
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UploadPictureFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    private val uploadPictureViewModel: UploadPictureViewModel by viewModels()

    private var _binding: UploadPictureFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var permissionManager: PermissionManager

    private var pictureUri: Uri? = null

    private val galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> pictureUri = uri }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UploadPictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        uploadPictureViewModel.uploadPictureState.observe(viewLifecycleOwner, {
            when(it) {
                UploadPictureSuccess -> {
                    registrationViewModel.moveTo(Step.Welcome)
                }
                is UploadPictureError -> {
                    binding.error.isVisible = true
                    binding.error.text = it.error
                }
            }
        })
    }

    private fun setListeners() {
        binding.imageView.setOnClickListener {
            permissionManager.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) { granted ->
                if (granted) {
                    openGallery()
                } else {
                    binding.error.isVisible = true
                    binding.error.text = "Please allow permission to choose Picture"
                }
            }
        }
        binding.btSkip.setOnClickListener {
            registrationViewModel.moveTo(Step.Welcome)
        }
        binding.btNext.setOnClickListener {
            uploadPictureViewModel.uploadUserPicture(pictureUri)
        }
    }

    private fun openGallery() {
        galleryResultLauncher.launch("image/*")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}