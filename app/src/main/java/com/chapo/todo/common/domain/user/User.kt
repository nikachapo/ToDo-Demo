package com.chapo.todo.common.domain.user

data class User(
    val id: String,
    val name: String,
    val email: String,
    val age: Int,
    val password: String? = null
)