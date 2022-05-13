package com.example.finalproject

import java.util.*

data class Task(val taskName:String="", val listOfSteps: List<String>? = null, var completed:Boolean=false, val index: Int = currentPosition) {
    init {
        currentPosition+=1
    }
    companion object{
    var currentPosition = 0
}
}