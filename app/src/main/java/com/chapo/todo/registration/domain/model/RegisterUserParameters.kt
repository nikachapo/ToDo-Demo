package com.chapo.todo.registration.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterUserParameters(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String,
    @field:Json(name = "age") val age: Int
)