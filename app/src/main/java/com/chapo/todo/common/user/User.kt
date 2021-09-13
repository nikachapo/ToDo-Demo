package com.chapo.todo.common.user

import com.chapo.todo.common.Picture

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val profilePicture: Picture? = null
)