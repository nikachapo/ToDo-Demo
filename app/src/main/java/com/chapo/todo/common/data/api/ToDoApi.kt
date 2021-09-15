package com.chapo.todo.common.data.api

import com.chapo.todo.common.data.ApiConstants
import com.chapo.todo.common.data.api.model.ApiAuthenticatedUser
import com.chapo.todo.common.data.api.model.ApiTaskList
import com.chapo.todo.login.domain.model.LoginParameters
import com.chapo.todo.registration.domain.model.RegisterUserParameters
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ToDoApi {

    @POST(ApiConstants.REGISTRATION_ENDPOINT)
    suspend fun register(@Body registerUserParameters: RegisterUserParameters): ApiAuthenticatedUser

    @POST(ApiConstants.AUTH_ENDPOINT)
    suspend fun login(@Body loginParameters: LoginParameters): ApiAuthenticatedUser

    @GET(ApiConstants.TASKS_ENDPOINT)
    suspend fun getAllTasks(): ApiTaskList

}