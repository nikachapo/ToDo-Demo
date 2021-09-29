package com.chapo.todo.common.data.api

import com.chapo.todo.common.data.ApiConstants
import com.chapo.todo.common.data.api.model.AddTaskParameters
import com.chapo.todo.common.data.api.model.ApiAuthenticatedUser
import com.chapo.todo.common.data.api.model.ApiTaskList
import com.chapo.todo.common.data.api.model.UpdateParameters
import com.chapo.todo.login.domain.model.LoginParameters
import com.chapo.todo.registration.domain.model.RegisterUserParameters
import retrofit2.http.*

interface ToDoApi {

    @POST(ApiConstants.REGISTRATION_ENDPOINT)
    suspend fun register(@Body registerUserParameters: RegisterUserParameters): ApiAuthenticatedUser

    @POST(ApiConstants.AUTH_ENDPOINT)
    suspend fun login(@Body loginParameters: LoginParameters): ApiAuthenticatedUser

    @GET(ApiConstants.TASKS_ENDPOINT)
    suspend fun getAllTasks(): ApiTaskList

    @POST(ApiConstants.TASKS_ENDPOINT)
    suspend fun uploadTask(@Body addTaskParameters: AddTaskParameters)

    @POST(ApiConstants.TASKS_ENDPOINT + "/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body updateParameters: UpdateParameters
    )

    @POST(ApiConstants.TASKS_ENDPOINT + "/{id}")
    suspend fun deleteTask(@Path("id") id: String)

}