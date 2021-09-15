package com.chapo.todo.login.domain.usecases

import com.chapo.todo.common.domain.repositories.UserRepository
import com.chapo.todo.login.domain.model.LoginParameters
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(loginParameters: LoginParameters)  {
        return userRepository.login(loginParameters)
    }
}