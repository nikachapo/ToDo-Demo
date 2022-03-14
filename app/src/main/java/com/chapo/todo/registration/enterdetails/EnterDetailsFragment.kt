package com.chapo.todo.registration.enterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chapo.todo.common.di.FragmentPermission
import com.chapo.todo.common.utils.permissions.PermissionManager
import com.chapo.todo.databinding.EnterDetailsFragmentBinding
import com.chapo.todo.registration.RegistrationActivity
import com.chapo.todo.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterDetailsFragment : Fragment() {

//    @FragmentPermission
//    @Inject
//    lateinit var permissionManager: PermissionManager

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    private val enterDetailsViewModel: EnterDetailsViewModel by viewModels()

    private var _binding: EnterDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EnterDetailsFragmentBinding.inflate(inflater, container, false)

        enterDetailsViewModel.enterDetailsState.observe(viewLifecycleOwner,
            { state ->
                when (state) {
                    is EnterDetailsSuccess -> {
                        val name = binding.etName.text.toString()
                        val age = binding.etAge.text.toString()
                        val email = binding.etEmail.text.toString()
                        val password = binding.etPassword.text.toString()
                        registrationViewModel.updateUserData(name, age, email, password)

                        (activity as RegistrationActivity).onValidationChecked()
                    }
                    is EnterDetailsError -> {
                        binding.error.text = state.error
                        binding.error.visibility = View.VISIBLE
                    }
                }
            })

        setupViews()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupViews() {
        binding.etName.doOnTextChanged { _, _, _, _ -> hideError() }
        binding.etAge.doOnTextChanged { _, _, _, _ -> hideError() }
        binding.etEmail.doOnTextChanged { _, _, _, _ -> hideError() }
        binding.etPassword.doOnTextChanged { _, _, _, _ -> hideError() }

        binding.btNext.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            enterDetailsViewModel.validateInput(name, age, email, password)
        }
    }

    private fun hideError() {
        binding.error.visibility = View.INVISIBLE
    }
}