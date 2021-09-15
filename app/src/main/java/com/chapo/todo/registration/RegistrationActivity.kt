package com.chapo.todo.registration

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.chapo.todo.MainActivity
import com.chapo.todo.R
import com.chapo.todo.common.utils.showToast
import com.chapo.todo.databinding.ActivityRegistrationBinding
import com.chapo.todo.registration.enterdetails.EnterDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {

    private val registrationViewModel: RegistrationViewModel by viewModels()

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, EnterDetailsFragment())
            .commit()

        registrationViewModel.registrationState.observe(this, {
            when(it) {
                is RegistrationState.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
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

    fun onValidationChecked() {
        registrationViewModel.registerUser()
    }

//    fun onDetailsEntered() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_holder, TermsAndConditionsFragment())
//            .addToBackStack(TermsAndConditionsFragment::class.java.simpleName)
//            .commit()
//    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}