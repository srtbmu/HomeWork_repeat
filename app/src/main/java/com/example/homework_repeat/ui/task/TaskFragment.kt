package com.example.homework_repeat.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.App
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.FragmentTaskBinding
import com.example.homework_repeat.model.Task
import com.example.homework_repeat.ui.home.HomeFragment.Companion.TASK_KEY

@Suppress("DEPRECATION")
class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = arguments?.getSerializable(TASK_KEY) as Task?

        if (task != null) {
            binding.etTitle.setText(task?.title.toString())
            binding.etDescription.setText(task?.desc.toString())
            binding.btnSave.text = getString(R.string.update)
        }

        binding.btnSave.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty()) {
                if (task == null) {
                    save()
                } else {
                    updateTask()
                }
                findNavController().navigateUp()
            } else {
                Toast.makeText(context, "null title", Toast.LENGTH_SHORT).show()
                binding.etTitle.error = "Title Required"
                return@setOnClickListener
            }
        }
    }

    private fun updateTask() {
        val data = task?.copy(
            title = binding.etTitle.text.toString(),
            desc = binding.etDescription.text.toString()
        )
        if (data != null) {
            App.db.taskDao().update(data)
        }
    }

    private fun save() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDescription.text.toString()
        )
        App.db.taskDao().insert(data)
    }
}