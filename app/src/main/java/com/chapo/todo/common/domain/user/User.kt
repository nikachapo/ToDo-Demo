package com.chapo.todo.common.domain.user

import com.chapo.todo.common.domain.Picture

data class User(
    val id: String,
    val name: String,
    val email: String,
    val age: Int,
    val password: String? = null,
    val profilePicture: Picture? = null
)