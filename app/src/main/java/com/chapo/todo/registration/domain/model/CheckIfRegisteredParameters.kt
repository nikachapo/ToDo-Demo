package com.chapo.todo.registration.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckIfRegisteredParameters(val email: String, val password: String)