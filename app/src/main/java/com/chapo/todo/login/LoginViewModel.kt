package com.chapo.todo.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapo.todo.common.domain.NetworkException
import com.chapo.todo.common.domain.NetworkUnavailableException
import com.chapo.todo.login.domain.model.LoginParameters
import com.chapo.todo.login.domain.usecases.CheckIfLoggedInUseCase
import com.chapo.todo.login.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState: LiveData<LoginViewState>
        get() = _loginState

    init {
        viewModelScope.launch {
            if (checkIfLoggedInUseCase()) _loginState.value = LoginSuccess
        }
    }

    fun login(email: String, password: String) {
        _loginState.value = Loading
        viewModelScope.launch {
            _loginState.value = try {
                loginUseCase(LoginParameters(email, password))
                LoginSuccess
            } catch (e: NetworkException) {
                e.message?.let { LoginError(if (e.code == CODE_NOT_REGISTERED) "Not registered" else it) }
            } catch (e: NetworkUnavailableException) {
                e.message?.let { LoginError(it) }
            }
        }
    }

}

private const val CODE_NOT_REGISTERED = 400

sealed class LoginViewState
object Loading : LoginViewState()
object LoginSuccess : LoginViewState()
data class LoginError(val message: String) : LoginViewState()
