package com.example.finalproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val rootView = binding.root

        val tasks = mutableListOf<Task>(Task("Task 1", arrayOf("Do step","Do another step"), completed = false),Task("Task 2",
            arrayOf(""),false),Task("Task 3", arrayOf("Do step"),true))

        val adapter = TaskAdapter(tasks)
        binding.recyclerView.adapter =adapter
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val name = bundle.getString("bundleKey")
            val list = bundle.getStringArray("bundleKey2")
            tasks.add(Task(name.toString(),list,false))
            val adapter = TaskAdapter(tasks)
            binding.recyclerView.adapter =adapter
        }
        binding.button.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddTaskFragment()
            rootView.findNavController().navigate(action)
        }

        return rootView
    }
}