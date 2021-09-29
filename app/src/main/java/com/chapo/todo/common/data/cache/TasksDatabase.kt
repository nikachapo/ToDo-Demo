package com.chapo.todo.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chapo.todo.common.data.cache.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TasksDao
}