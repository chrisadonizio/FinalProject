package com.example.finalproject

import android.app.*
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentAddTaskBinding
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private var listOfSteps = mutableListOf<String>()
    private val notificationId =1
    private val channelID = "todo"
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        createNotificationChannel()
        binding.addSteps.setOnClickListener {
            if (listOfSteps.size <= 10) {
                val word: String = binding.stepAddButton.text.toString()
                listOfSteps.add(word)
                binding.textView3.text =  "${binding.textView3.text.toString()}\n${listOfSteps.lastIndexOf(word) + 1}. $word"
            }
        }
        var cal = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                binding.textView2.text = sdf.format(cal.getTime())
                scheduleNotification(cal.getTime())
            }
        }
        binding.calender!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val picker: DatePickerDialog = DatePickerDialog(
                    binding.root.context, R.style.DialogTheme,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                picker.show()
                val buttonColor = ContextCompat.getColor(binding.root.context, R.color.dark_blue)
                picker.getButton(BUTTON_NEGATIVE).setTextColor(buttonColor)
                picker.getButton(BUTTON_POSITIVE).setTextColor(buttonColor)

            }

        })
        binding.submit.setOnClickListener {
            if(isEmpty(binding.nameEnter.text)){
                Toast.makeText(this.context,"Fill out Name!",Toast.LENGTH_SHORT).show()
            }
            else {
                var bundle = bundleOf(
                    "bundleKey" to binding.nameEnter.text.toString(),
                    "bundleKey2" to listOfSteps.toTypedArray()
                )
                bundle.putSerializable("date", cal.getTime())
                setFragmentResult(
                    "requestKey", bundle
                )

                rootView.findNavController().navigateUp()
            }
        }
        return rootView
    }
        private fun createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "To-Do List"
                val descriptionText = "Description"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("todo", name, importance)
                channel.description = descriptionText
                // Register the channel with the system
                val notificationManager: NotificationManager =binding.root.context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
        private fun scheduleNotification(time:Date){
            val intent = Intent(requireActivity().applicationContext,Notification::class.java )
            intent.putExtra(title,"To Do")
            intent.putExtra(message,"Message")
            val cal = Calendar.getInstance()
            cal.setTime(time)
            val pendingIntent = PendingIntent.getBroadcast(this.context,notificationId,intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager: AlarmManager = this.requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pendingIntent)
            }
            showAlert(time,title,message)
        }
        private fun showAlert(time:Date,title:String,message:String){
            val format = "MM/dd/yyyy"
            val sdf = java.text.SimpleDateFormat(format, java.util.Locale.US)
            AlertDialog.Builder(binding.root.context).setTitle("Notification Scheduled").setMessage("A reminder for ${binding.nameEnter.text} is now set\nAt: ${sdf.format(time)}").setPositiveButton("Okay"){ _, _->}.show()
        }



}
