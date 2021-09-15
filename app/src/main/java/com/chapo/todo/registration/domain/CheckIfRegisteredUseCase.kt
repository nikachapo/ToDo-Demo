package com.chapo.todo.registration.domain

import com.chapo.todo.common.domain.repositories.UserRepository
import com.chapo.todo.registration.domain.model.CheckIfRegisteredParameters
import javax.inject.Inject

class CheckIfRegisteredUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(parameters: CheckIfRegisteredParameters) : Boolean {
        return userRepository.isRegistered(parameters)
    }
}