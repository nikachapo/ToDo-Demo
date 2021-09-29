package com.chapo.todo.common.data.cache

import com.chapo.todo.common.data.cache.model.TaskEntity
import kotlinx.coroutines.flow.Flow


interface TasksCache {
    suspend fun storeTasks(tasks: List<TaskEntity>)
    suspend fun storeTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
    fun getAllTasks(): Flow<List<TaskEntity>?>
}