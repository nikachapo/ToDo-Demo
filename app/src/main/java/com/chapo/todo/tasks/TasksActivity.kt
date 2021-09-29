package com.chapo.todo.tasks

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chapo.todo.databinding.ActivityTasksBinding
import com.chapo.todo.tasks.addtask.AddTaskBottomSheet
import com.chapo.todo.tasks.deleteTask.DeleteTaskBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding

    private val viewModel: TasksViewModel by viewModels()

    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        lifecycleScope.launchWhenStarted {
            viewModel.getTasks().collect { tasks ->
                tasks?.let { it -> tasksAdapter.submitData(it) }
            }
        }
    }

    private fun initViews() {

        tasksAdapter = TasksAdapter {
            viewModel.onTaskUpdated(it)
        }

        tasksAdapter.setOnLongClickListener {
            DeleteTaskBottomSheet.show(supportFragmentManager, it.id)
        }

        binding.tasksList.adapter = tasksAdapter

        binding.addButton.setOnClickListener {
            openAddTaskBottomSheet()
        }
    }

    private fun openAddTaskBottomSheet() {
        AddTaskBottomSheet.show(supportFragmentManager)
    }


}