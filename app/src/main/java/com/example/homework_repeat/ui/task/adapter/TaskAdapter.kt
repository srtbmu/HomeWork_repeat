package com.example.homework_repeat.ui.task.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.ItemTaskBinding
import com.example.homework_repeat.model.Task

class TaskAdapter(
    private val onLongClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val list = arrayListOf<Task>()

    fun addTasks(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        list.reverse()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) = with(binding) {
            if (adapterPosition % 2 == 0) {
                itemView.setBackgroundColor(Color.BLACK)
                tvTitle.setTextColor(Color.WHITE)
                tvDesc.setTextColor(Color.WHITE)
            } else {
                itemView.setBackgroundColor(Color.WHITE)
                tvTitle.setTextColor(Color.BLACK)
                tvDesc.setTextColor(Color.BLACK)
            }
            tvTitle.text = task.title
            tvDesc.text = task.desc
            itemView.setOnLongClickListener {
                onLongClick(task)
                false
            }
        }
    }
}