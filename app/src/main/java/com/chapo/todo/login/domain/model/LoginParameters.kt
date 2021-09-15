package com.chapo.todo.login.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginParameters(val email: String, val password: String)