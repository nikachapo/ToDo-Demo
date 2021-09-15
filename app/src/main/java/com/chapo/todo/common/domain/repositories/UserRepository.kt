package com.chapo.todo.common.domain.repositories

import com.chapo.todo.login.domain.model.LoginParameters
import com.chapo.todo.registration.domain.model.CheckIfRegisteredParameters
import com.chapo.todo.registration.domain.model.RegisterUserParameters

interface UserRepository {
    suspend fun login(loginParameters: LoginParameters)
    suspend fun isLoggedIn(): Boolean
    suspend fun isRegistered(parameters: CheckIfRegisteredParameters): Boolean
    suspend fun registerUser(parameters: RegisterUserParameters)
}