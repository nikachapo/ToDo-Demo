package com.chapo.todo.common.data

import com.chapo.todo.common.data.api.ToDoApi
import com.chapo.todo.common.data.api.model.AddTaskParameters
import com.chapo.todo.common.data.api.model.UpdateParameters
import com.chapo.todo.common.data.api.model.mappers.TaskMapper
import com.chapo.todo.common.data.cache.TasksCache
import com.chapo.todo.common.data.cache.model.TaskEntity
import com.chapo.todo.common.domain.repositories.TasksRepository
import com.chapo.todo.common.domain.task.Task
import com.chapo.todo.common.utils.DispatchersProvider
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@ViewModelScoped
class TasksDataRepository @Inject constructor(
    private val toDoApi: ToDoApi,
    private val todoCache: TasksCache,
    private val mapper: TaskMapper,
    private val dispatchersProvider: DispatchersProvider
) : TasksRepository {

    override suspend fun storeTasks(tasks: List<Task>) {
        withContext(dispatchersProvider.io()) {
            // This is called only when the first network response is returned
            todoCache.deleteAllTasks()
            todoCache.storeTasks(tasks.map { TaskEntity.fromDomain(it) })
        }
    }

    override suspend fun storeTask(task: Task) {
        withContext(dispatchersProvider.io()) {
            launch { toDoApi.uploadTask(AddTaskParameters(task.description)) }
            launch { todoCache.storeTask(TaskEntity.fromDomain(task)) }
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(dispatchersProvider.io()) {
            launch {
                try {
                    toDoApi.updateTask(task.id, UpdateParameters(task.completed))
                } catch (e: HttpException) {
                }
            }
            launch { todoCache.updateTask(TaskEntity.fromDomain(task)) }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        withContext(dispatchersProvider.io()) {
            launch {
                try {
                    toDoApi.deleteTask(taskId)
                } catch (e: HttpException) {
                }
            }
            launch { todoCache.deleteTask(taskId) }
        }
    }

    override suspend fun syncCache() {
        withContext(dispatchersProvider.io()) {
            try {
                val tasks = toDoApi.getAllTasks()
                storeTasks(tasks.tasks.map { mapper.mapToDomain(it) })
            } catch (e: Exception) {
            }
        }
    }

    override fun getAllTasksFlow(): Flow<List<Task>?> {
        return todoCache.getAllTasks()
            .map { it?.let { taskEntities -> taskEntities.map { entity -> TaskEntity.toDomain(entity) } } }
    }

}