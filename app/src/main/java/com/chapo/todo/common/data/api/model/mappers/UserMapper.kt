package com.chapo.todo.common.data.api.model.mappers

import com.chapo.todo.common.data.api.model.ApiUser
import com.chapo.todo.common.domain.user.User
import javax.inject.Inject

class UserMapper @Inject constructor(): ApiMapper<ApiUser, User> {

    override fun mapToDomain(apiEntity: ApiUser): User {
        return User(apiEntity.id, apiEntity.name, apiEntity.email, apiEntity.age)
    }

}