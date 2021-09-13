package com.chapo.todo.common.task

data class TasksList(
    val count: Int,
    val tasks: List<TasksList>
)