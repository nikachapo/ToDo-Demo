package com.chapo.todo.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chapo.todo.common.domain.task.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val completed: Boolean,
    val description: String
) {

    companion object {
        fun fromDomain(task: Task): TaskEntity {
            return TaskEntity(task.id, task.completed, task.description)
        }

        fun toDomain(taskEntity: TaskEntity): Task {
            return Task(taskEntity.id, taskEntity.completed, taskEntity.description)
        }
    }
}