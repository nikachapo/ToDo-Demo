package com.chapo.todo.common.data.cache

import androidx.room.*
import com.chapo.todo.common.data.cache.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskEntity>?>

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}