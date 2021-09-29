package com.chapo.todo.common.data.cache

import com.chapo.todo.common.data.cache.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksCacheImpl @Inject constructor(private val tasksDao: TasksDao) : TasksCache {

    override suspend fun storeTasks(tasks: List<TaskEntity>) {
        tasksDao.insertTasks(tasks)
    }

    override suspend fun storeTask(taskEntity: TaskEntity) {
        tasksDao.insertTask(taskEntity)
    }

    override suspend fun updateTask(taskEntity: TaskEntity) {
        tasksDao.updateTask(taskEntity)
    }

    override suspend fun deleteTask(taskId: String) {
        tasksDao.deleteTask(taskId)
    }

    override suspend fun deleteAllTasks() {
        tasksDao.deleteAllTasks()
    }

    override fun getAllTasks(): Flow<List<TaskEntity>?> {
        return tasksDao.getAllTasks()
    }
}