package com.chapo.todo.registration.domain

import com.chapo.todo.common.domain.repositories.UserRepository
import com.chapo.todo.registration.domain.model.CheckIfRegisteredParameters
import com.chapo.todo.registration.domain.model.RegisterUserParameters
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(parameters: RegisterUserParameters) {
        return userRepository.registerUser(parameters)
    }
}