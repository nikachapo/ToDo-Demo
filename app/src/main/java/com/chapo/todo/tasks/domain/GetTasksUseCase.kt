package com.chapo.todo.tasks.domain

import com.chapo.todo.common.domain.repositories.TasksRepository
import com.chapo.todo.common.domain.task.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository) {
    operator fun invoke(): Flow<List<Task>?> {
        return tasksRepository.getAllTasksFlow()
    }
}