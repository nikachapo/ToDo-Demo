package com.chapo.todo.registration

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.chapo.todo.R
import com.chapo.todo.common.utils.showToast
import com.chapo.todo.databinding.ActivityRegistrationBinding
import com.chapo.todo.registration.enterdetails.EnterDetailsFragment
import com.chapo.todo.registration.uploadpicture.UploadPictureFragment
import com.chapo.todo.registration.welcome.WelcomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private val registrationViewModel: RegistrationViewModel by viewModels()

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registrationViewModel.step.observe(this, {
            replaceFragment(it)
        })

        registrationViewModel.registrationState.observe(this, {
            when (it) {
                is RegistrationState.Success -> {
                    showToast("User registered successfully :]")
                }
                is RegistrationState.Error -> {
                    binding.fragmentHolder.isVisible = true
                    binding.progressBar.isVisible = false
                    showToast(it.message)
                }
                RegistrationState.Loading -> {
                    binding.fragmentHolder.isVisible = false
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun replaceFragment(step: Step) {
        val fragment = when (step) {
            Step.EnterDetails -> EnterDetailsFragment()
            Step.UploadPicture -> UploadPictureFragment()
            Step.Welcome -> WelcomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .commit()
    }

    fun onValidationChecked() {
        registrationViewModel.registerUser()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}