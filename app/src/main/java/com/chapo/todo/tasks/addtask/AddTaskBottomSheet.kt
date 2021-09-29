package com.chapo.todo.tasks.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.chapo.todo.R
import com.chapo.todo.common.utils.showToast
import com.chapo.todo.databinding.BottomSheetAddTaskBinding
import com.chapo.todo.tasks.TasksViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: TasksViewModel by activityViewModels()

    private var _binding: BottomSheetAddTaskBinding? = null

    private val binding: BottomSheetAddTaskBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return BottomSheetAddTaskBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskButton.setOnClickListener {
            val text = binding.taskTitleEditText.text
            if (text.isNotEmpty()) {
                viewModel.addTask(text.toString())
                dismiss()
            } else {
                requireActivity().showToast("Enter task")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        fun show(fragmentManager: FragmentManager) {
            AddTaskBottomSheet().show(fragmentManager, "AddTaskBottomSheet")
        }
    }
}