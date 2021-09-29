package com.chapo.todo.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.chapo.todo.common.utils.hideKeyboard
import com.chapo.todo.tasks.TasksActivity
import com.chapo.todo.databinding.ActivityLoginBinding
import com.chapo.todo.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObservers()
        setupViews()
    }

    private fun bindObservers() {
        loginViewModel.loginState.observe(this, { state ->
            binding.progressBar.isVisible = state is Loading
            when (state) {
                LoginSuccess -> {
                    startActivity(Intent(this, TasksActivity::class.java))
                    finish()
                }
                is LoginError -> {
                    binding.tvError.text = state.message
                    binding.tvError.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupViews() {
        binding.etPassword.doOnTextChanged { _, _, _, _ ->
            binding.tvError.visibility = View.INVISIBLE
        }

        binding.btLogin.setOnClickListener {
            loginViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            binding.btLogin.hideKeyboard()
        }
        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

}
