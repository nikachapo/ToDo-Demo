package com.chapo.todo.common.data.api.model.mappers

import com.chapo.todo.common.data.api.model.ApiTaskList
import com.chapo.todo.common.domain.task.TaskList
import javax.inject.Inject

class TaskListMapper @Inject constructor(private val taskMapper: TaskMapper) :
    ApiMapper<ApiTaskList, TaskList> {

    override fun mapToDomain(apiEntity: ApiTaskList): TaskList {
        return TaskList(apiEntity.count, apiEntity.tasks.map { taskMapper.mapToDomain(it) })
    }

}