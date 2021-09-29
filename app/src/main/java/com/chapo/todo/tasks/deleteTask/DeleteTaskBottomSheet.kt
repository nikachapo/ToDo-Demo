package com.chapo.todo.tasks.deleteTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.chapo.todo.R
import com.chapo.todo.common.utils.showToast
import com.chapo.todo.databinding.BottomSheetAddTaskBinding
import com.chapo.todo.databinding.BottomSheetDeleteTaskBinding
import com.chapo.todo.tasks.TasksViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteTaskBottomSheet : BottomSheetDialogFragment() {

    private var taskId: String? = null

    private val viewModel: TasksViewModel by activityViewModels()

    private var _binding: BottomSheetDeleteTaskBinding? = null

    private val binding: BottomSheetDeleteTaskBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        taskId = arguments?.getString(ARG_TASK_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return BottomSheetDeleteTaskBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteTaskButton.setOnClickListener {
            viewModel.deleteTask(taskId)
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        const val ARG_TASK_ID = "task-id"
        fun show(fragmentManager: FragmentManager, taskId: String) {
            DeleteTaskBottomSheet().apply {
                arguments = Bundle().apply { putString(ARG_TASK_ID, taskId) }
            }.show(fragmentManager, "AddTaskBottomSheet")
        }
    }
}