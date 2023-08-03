package com.example.homework_repeat.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.App
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.FragmentHomeBinding
import com.example.homework_repeat.model.Task
import com.example.homework_repeat.ui.task.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter = TaskAdapter(this::onLongClick,this::onClick)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        setData()
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onClick(task: Task) {
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK_KEY to task))
    }

    companion object {
        const val TASK_KEY = "task.key"
    }

    private fun onLongClick(task: Task) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete?")
            .setPositiveButton("Yes") { _, _ ->
                App.db.taskDao().delete(task)
                setData()
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog?.dismiss() }
            .create()
            .show()
    }

    private fun setData() {
        val list = App.db.taskDao().getAll()
        adapter.addTasks(list)
    }
}