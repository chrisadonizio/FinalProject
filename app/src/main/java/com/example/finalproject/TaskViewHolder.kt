package com.example.finalproject

import android.app.LauncherActivity
import android.os.Debug
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemLayoutBinding
import com.google.firebase.database.FirebaseDatabase

class TaskViewHolder(val binding:ListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
            private lateinit var currentTask:Task
            init {
                binding.root.setOnClickListener { view ->
                    val action = MainFragmentDirections.actionMainFragmentToEditTaskFragment()
                    binding.root.findNavController().navigate(action)
                }
//                binding.checkBox.setOnClickListener{
//                    val database = FirebaseDatabase.getInstance()
//                    val myRef = database.getReference()
//                    myRef.child("").orderByKey()
//
//                }
            }

            fun bindTask(task: Task) {
                currentTask = task
                binding.checkBox.text = task.taskName
                var message = ""
                if (task.listOfSteps != null) {
                    for ((index, step) in task.listOfSteps.withIndex()) {
                        message += "${index+1}. $step\n"
                    }
                    binding.steps.text = message

                }
            }
        }