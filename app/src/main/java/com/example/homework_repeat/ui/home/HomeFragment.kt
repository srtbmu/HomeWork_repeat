package com.example.homework_repeat.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.FragmentHomeBinding
import com.example.homework_repeat.model.Task
import com.example.homework_repeat.ui.task.TaskFragment.Companion.RESULT_KEY
import com.example.homework_repeat.ui.task.TaskFragment.Companion.RESULT_REQUEST_KEY
import com.example.homework_repeat.ui.task.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val adapter = TaskAdapter()
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
        setFragmentResultListener(RESULT_REQUEST_KEY) { _, bundle ->
            val data = bundle.getSerializable(RESULT_KEY) as Task
            adapter.addTask(data)
//            Log.e("ololo", "onViewCreated: " + data)
        }
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}