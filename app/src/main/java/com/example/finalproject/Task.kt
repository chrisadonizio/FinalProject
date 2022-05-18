package com.example.finalproject

import java.util.*

data class Task(val taskName:String="", val listOfSteps: List<String>? = null, var completed:Boolean=false, var key:String="") {

}