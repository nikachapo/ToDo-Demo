package com.chapo.todo.common.domain.repositories

import com.chapo.todo.common.domain.task.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun storeTasks(tasks: List<Task>)
    suspend fun storeTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
    fun getAllTasksFlow(): Flow<List<Task>?>
    suspend fun syncCache()
}
