package com.chapo.todo.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiTask(
    @field:Json(name = "_id") val id: String,
    @field:Json(name = "completed") val completed: Boolean,
    @field:Json(name = "description") val description: String
)

@JsonClass(generateAdapter = true)
data class ApiTaskList(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "data") val tasks: List<ApiTask>
)

@JsonClass(generateAdapter = true)
data class AddTaskParameters(
    @field:Json(name = "description") val description: String
)

@JsonClass(generateAdapter = true)
data class UpdateParameters(
    @field:Json(name = "completed") val completed: Boolean
)