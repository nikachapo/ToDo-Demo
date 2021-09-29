package com.chapo.todo.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chapo.todo.common.domain.task.Task
import com.chapo.todo.databinding.ItemTaskBinding

class TasksAdapter(private val onTaskCompletionChanged: (Task) -> Unit) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var onItemLongClick: ((Task) -> Unit)? = null

    fun setOnLongClickListener(onItemLongClick: (Task) -> Unit) {
        this.onItemLongClick = onItemLongClick
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun submitData(task: List<Task>) {
        differ.submitList(task)
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.root.setOnLongClickListener {
                onItemLongClick?.invoke(task)
                true
            }
            binding.titleTextView.text = task.description
            binding.isDoneCheckBox.isChecked = task.completed
            binding.isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCompletionChanged(task.copy(completed = isChecked))
            }
        }

    }

}