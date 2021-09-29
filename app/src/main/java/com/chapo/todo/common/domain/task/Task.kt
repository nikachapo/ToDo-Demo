package com.chapo.todo.common.domain.task

data class Task(
    val id: String,
    val completed: Boolean,
    val description: String
)