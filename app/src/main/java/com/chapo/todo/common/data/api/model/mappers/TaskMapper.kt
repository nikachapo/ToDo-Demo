package com.chapo.todo.common.data.api.model.mappers

import com.chapo.todo.common.data.api.model.ApiTask
import com.chapo.todo.common.domain.task.Task
import javax.inject.Inject

class TaskMapper @Inject constructor() : ApiMapper<ApiTask, Task> {

    override fun mapToDomain(apiEntity: ApiTask): Task {
        return Task(apiEntity.id, apiEntity.completed, apiEntity.description)
    }

}