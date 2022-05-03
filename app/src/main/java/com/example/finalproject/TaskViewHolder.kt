package com.example.finalproject

import android.app.LauncherActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemLayoutBinding

class TaskViewHolder(val binding:ListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
            private lateinit var currentTask:Task

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