package com.chapo.todo.common.data

object ApiConstants {
  const val BASE_ENDPOINT = "https://api-nodejs-todolist.herokuapp.com/"
  const val REGISTRATION_ENDPOINT = "user/register"
  const val AUTH_ENDPOINT = "user/login"
  const val TASKS_ENDPOINT = "task"
}

object ApiParameters {
  const val TOKEN_TYPE = "Bearer "
  const val AUTH_HEADER = "Authorization"
  const val CONTENT_TYPE = "application/json"
  const val CONTENT_TYPE_HEADER = "Content-Type"
  const val EMAIL_KEY = "email"
  const val PASSWORD_KEY = "password"

  const val PAGE = "page"
  const val LIMIT = "limit"
  const val LOCATION = "location"
  const val DISTANCE = "distance"
  const val NAME = "name"
  const val AGE = "age"
  const val TYPE = "type"
}