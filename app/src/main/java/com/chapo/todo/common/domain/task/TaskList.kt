package com.chapo.todo.common.domain.task

data class TaskList(
    val count: Int,
    val tasks: List<Task>
)