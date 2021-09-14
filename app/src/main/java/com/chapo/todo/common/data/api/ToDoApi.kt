package com.chapo.todo.common.data.api

import com.chapo.todo.common.data.ApiConstants
import com.chapo.todo.common.data.api.model.ApiTaskList
import retrofit2.http.GET

interface ToDoApi {

//    @POST(ApiConstants.REGISTRATION_ENDPOINT)
//    suspend fun register(@Body apiUser: ApiUser): ApiAuthenticatedUser
//
//    @POST(ApiConstants.AUTH_ENDPOINT)
//    suspend fun login(@Body apiUser: ApiUser): ApiAuthenticatedUser

    @GET(ApiConstants.TASKS_ENDPOINT)
    suspend fun getAllTasks(): ApiTaskList

}