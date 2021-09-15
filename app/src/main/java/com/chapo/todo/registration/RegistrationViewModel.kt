package com.chapo.todo.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapo.todo.common.domain.NetworkException
import com.chapo.todo.common.domain.NetworkUnavailableException
import com.chapo.todo.registration.domain.RegisterUserUseCase
import com.chapo.todo.registration.domain.model.RegisterUserParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> = _registrationState

    private var name: String? = null
    private var email: String? = null
    private var password: String? = null
    private var age: Int? = null

    fun updateUserData(name: String, age: String, email: String, password: String) {
        this.name = name
        this.age = age.toInt()
        this.email = email
        this.password = password
    }

    fun registerUser() {
        _registrationState.value = RegistrationState.Loading
        ifNotNull(name, email, password, age) {
            viewModelScope.launch {
                try {
                    registerUserUseCase(RegisterUserParameters(name!!, email!!, password!!, age!!))
                    _registrationState.value = RegistrationState.Success
                } catch (e: NetworkException) {
                    e.message?.let {
                        _registrationState.value =
                            RegistrationState.Error(if (e.code == CODE_ALREADY_REGISTERED) "Already registered" else it)
                    }
                } catch (e: NetworkUnavailableException) {
                    e.message?.let {
                        _registrationState.value = RegistrationState.Error(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val CODE_ALREADY_REGISTERED = 400
    }
}

sealed class RegistrationState {
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}

inline fun ifNotNull(vararg objects: Any?, block: () -> Unit) {
    val containsNull = objects.any { it == null }
    if (!containsNull) {
        block()
    }
}