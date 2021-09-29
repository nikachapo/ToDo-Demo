package com.chapo.todo.tasks.domain

import com.chapo.todo.common.domain.repositories.TasksRepository
import javax.inject.Inject

class SyncTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository) {
    suspend operator fun invoke() {
        tasksRepository.syncCache()
    }
}