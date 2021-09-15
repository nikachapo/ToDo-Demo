package com.chapo.todo.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.chapo.todo.MainActivity
import com.chapo.todo.R
import com.chapo.todo.common.utils.hideKeyboard
import com.chapo.todo.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel.loginState.observe(this, { state ->
            binding.progressBar.isVisible = state is Loading
            when (state) {
                LoginSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                LoginError -> binding.tvError.visibility = View.VISIBLE
            }
        })

        setupViews()
    }

    private fun setupViews() {
        binding.etPassword.doOnTextChanged { _, _, _, _ -> binding.tvError.visibility = View.INVISIBLE }

        binding.btLogin.setOnClickListener {
            loginViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            binding.btLogin.hideKeyboard()
        }
        binding.btRegister.setOnClickListener {
//            loginViewModel.unregister()
//            val intent = Intent(this, RegistrationActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
//                    Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
        }
    }

}
