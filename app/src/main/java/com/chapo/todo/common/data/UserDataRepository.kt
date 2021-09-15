package com.chapo.todo.common.data

import com.chapo.todo.common.data.api.ToDoApi
import com.chapo.todo.common.data.api.model.mappers.UserMapper
import com.chapo.todo.common.data.di.Token
import com.chapo.todo.common.data.di.UserData
import com.chapo.todo.common.data.preferences.Preferences
import com.chapo.todo.common.domain.NetworkException
import com.chapo.todo.common.domain.repositories.UserRepository
import com.chapo.todo.common.domain.user.User
import com.chapo.todo.common.utils.DispatchersProvider
import com.chapo.todo.login.domain.model.LoginParameters
import com.chapo.todo.registration.domain.model.CheckIfRegisteredParameters
import com.chapo.todo.registration.domain.model.RegisterUserParameters
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val userMapper: UserMapper,
    private val toDoApi: ToDoApi,
    @Token private val tokenPreferences: Preferences<String>,
    @UserData private val userPreferences: Preferences<User>,
    private val dispatchersProvider: DispatchersProvider
) : UserRepository {

    @Throws(NetworkException::class)
    override suspend fun login(loginParameters: LoginParameters) {
        return withContext(dispatchersProvider.io()) {
            try {
                val authenticatedUser = toDoApi.login(loginParameters)
                tokenPreferences.putValue(authenticatedUser.token)
                userPreferences.putValue(
                    userMapper.mapToDomain(authenticatedUser.user)
                        .copy(password = loginParameters.password)
                )
            } catch (exception: HttpException) {
                throw NetworkException(
                    exception.message ?: "Code ${exception.code()}",
                    exception.code()
                )
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return withContext(dispatchersProvider.io()) {
            tokenPreferences.getValue() != null // token is always available if user is logged in
        }
    }

    @Throws(NetworkException::class)
    override suspend fun isRegistered(parameters: CheckIfRegisteredParameters): Boolean {
        return withContext(dispatchersProvider.io()) {
            try {
                toDoApi.login(LoginParameters(parameters.email, parameters.password))
                true
            } catch (exception: HttpException) {
                if (exception.code() == 400) {
                    false
                } else {
                    throw NetworkException(
                        exception.message ?: "Code ${exception.code()}",
                        exception.code()
                    )
                }
            }
        }
    }

    @Throws(NetworkException::class)
    override suspend fun registerUser(parameters: RegisterUserParameters) {
        withContext(dispatchersProvider.io()) {
            try {
                val authenticatedUser = toDoApi.register(parameters)
                tokenPreferences.putValue(authenticatedUser.token)
                userPreferences.putValue(
                    userMapper.mapToDomain(authenticatedUser.user)
                        .copy(password = parameters.password)
                )
            } catch (exception: HttpException) {
                throw NetworkException(
                    exception.message ?: "Code ${exception.code()}",
                    exception.code()
                )
            }
        }
    }
}