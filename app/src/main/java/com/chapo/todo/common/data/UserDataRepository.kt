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
                userPreferences.putValue(userMapper.mapToDomain(authenticatedUser.user))
            } catch (exception: HttpException) {
                throw NetworkException(exception.message ?: "Code ${exception.code()}")
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return withContext(dispatchersProvider.io()) {
            tokenPreferences.getValue() != null // token is always available if user is logged in
        }
    }

}