package com.chapo.todo.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
  fun main(): CoroutineDispatcher = Dispatchers.Main
  fun io(): CoroutineDispatcher = Dispatchers.IO
  fun default(): CoroutineDispatcher = Dispatchers.Default
}