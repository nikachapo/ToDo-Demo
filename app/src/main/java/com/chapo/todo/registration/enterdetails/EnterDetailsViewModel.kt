package com.chapo.todo.registration.enterdetails

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapo.todo.common.domain.NetworkUnavailableException
import com.chapo.todo.registration.domain.CheckIfRegisteredUseCase
import com.chapo.todo.registration.domain.model.CheckIfRegisteredParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EnterDetailsViewModel @Inject constructor(
    private val checkIfRegisteredUseCase: CheckIfRegisteredUseCase
) : ViewModel() {

    private val _enterDetailsState = MutableLiveData<EnterDetailsViewState>()
    val enterDetailsState: LiveData<EnterDetailsViewState>
        get() = _enterDetailsState

    fun validateInput(name: String, age: String, email: String, password: String) {
        when {
            name.isEmpty() -> {
                _enterDetailsState.value = EnterDetailsError("Enter name")
            }
            age.isEmpty() -> {
                _enterDetailsState.value = EnterDetailsError("Enter age")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _enterDetailsState.value = EnterDetailsError("Invalid email")
            }
            password.length < MAX_LENGTH -> {
                _enterDetailsState.value =
                    EnterDetailsError("Password has to be longer than 4 characters")
            }
            else -> {
                checkUser(email, password)
            }
        }
    }

    private fun checkUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                if (checkIfRegisteredUseCase(CheckIfRegisteredParameters(email, password))) {
                    _enterDetailsState.value = EnterDetailsError("User has already registered")
                } else {
                    _enterDetailsState.value = EnterDetailsSuccess
                }
            } catch (e: NetworkUnavailableException) {
                _enterDetailsState.value = EnterDetailsError("Network unavailable")
            }
        }
    }

    companion object {
        private const val MAX_LENGTH = 5
    }
}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()