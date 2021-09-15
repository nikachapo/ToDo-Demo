package com.chapo.todo.common.domain.repositories

import com.chapo.todo.login.domain.model.LoginParameters

interface UserRepository {
    suspend fun login(loginParameters: LoginParameters)
    suspend fun isLoggedIn(): Boolean
}