package com.chapo.todo.login.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginParameters(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String
)