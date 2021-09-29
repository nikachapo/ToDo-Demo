package com.chapo.todo.common.data.di

import android.content.Context
import androidx.room.Room
import com.chapo.todo.common.data.cache.TasksCache
import com.chapo.todo.common.data.cache.TasksCacheImpl
import com.chapo.todo.common.data.cache.TasksDao
import com.chapo.todo.common.data.cache.TasksDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(tasksCacheImpl: TasksCacheImpl): TasksCache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): TasksDatabase {
            return Room.databaseBuilder(context, TasksDatabase::class.java, "tasks.db")
                .build()
        }

        @Provides
        fun provideTasksDao(
            database: TasksDatabase
        ): TasksDao = database.taskDao()
    }
}