package com.chapo.todo.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ApiAuthenticatedUser(
    @field:Json(name = "count") val user: ApiUser,
    @field:Json(name = "count") val token: ApiToken
)

@JsonClass(generateAdapter = true)
data class ApiUser(
    @field:Json(name = "_id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "age") val age: Int
)

@JvmInline
value class ApiToken(val value: String) {
    companion object {
        const val INVALID = ""
    }
}
