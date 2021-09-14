package com.chapo.todo.common.data.api.interceptors

import com.chapo.logging.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Logger.i(message)
  }
}