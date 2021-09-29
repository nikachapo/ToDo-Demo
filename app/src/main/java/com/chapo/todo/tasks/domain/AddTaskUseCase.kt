package com.chapo.todo.tasks.domain

import com.chapo.todo.common.domain.repositories.TasksRepository
import com.chapo.todo.common.domain.task.Task
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.storeTask(task)
    }
}