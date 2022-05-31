package com.example.finalproject

import android.app.LauncherActivity
import android.os.Debug
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemLayoutBinding
import com.google.firebase.database.*

class TaskViewHolder(val binding:ListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
            private lateinit var currentTask:Task
            init {
                binding.root.setOnClickListener { view ->
                    val action =
                        MainFragmentDirections.actionMainFragmentToViewTaskFragment2(if(currentTask.date!=null){currentTask.date}else{null}, currentTask.listOfSteps?.toTypedArray(),currentTask.taskName)
                    binding.root.findNavController().navigate(action)

                }
                }



            fun bindTask(task: Task) {
                currentTask = task
                binding.checkBox.text = task.taskName
                binding.checkBox.isChecked = currentTask.completed
//                var message = ""
//                if (task.listOfSteps != null) {
//                    for ((index, step) in task.listOfSteps.withIndex()) {
//                        message += "${index+1}. $step\n"
//                    }
//                    binding.steps.text = message
//
//                }
                binding.checkBox.setOnClickListener{
                    val myRef = FirebaseDatabase.getInstance().getReference("message").child(currentTask.key).child("completed").setValue(!currentTask.completed)
                    currentTask.completed = !currentTask.completed

                }
            }
        }

