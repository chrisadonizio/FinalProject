package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemLayoutBinding

class TaskAdapter(val taskList:List<Task>): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(currentViewHolder:TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        currentViewHolder.bindTask(currentTask)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}