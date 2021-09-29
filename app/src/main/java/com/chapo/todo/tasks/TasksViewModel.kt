package com.chapo.todo.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapo.todo.common.domain.task.Task
import com.chapo.todo.tasks.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val syncTasksUseCase: SyncTasksUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        viewModelScope.launch {
            syncTasksUseCase()
        }
    }

    fun onTaskUpdated(it: Task) {
        viewModelScope.launch {
            updateTaskUseCase(it)
        }
    }

    fun addTask(text: String) {
        viewModelScope.launch {
            addTaskUseCase(Task(UUID.randomUUID().toString(), false, text))
        }
    }

    fun getTasks(): Flow<List<Task>?> {
        return getTasksUseCase()
    }

    fun deleteTask(taskId: String?) {
        viewModelScope.launch {
            taskId?.let { deleteTaskUseCase(it) }
        }
    }
}