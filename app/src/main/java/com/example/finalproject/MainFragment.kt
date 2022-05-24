package com.example.finalproject
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentMainBinding
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import androidx.core.content.ContextCompat;
import androidx.core.content.ContextCompat.getSystemServiceName
import java.util.*

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private  var tasks = mutableListOf<Task>()
    private val binding get() = _binding!!
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")
    private val CHANNEL_ID: String = "todo"
    private val notificationID = 101
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val rootView = binding.root
        createNotificationChannel()
        val childEventListener = object : ChildEventListener{
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
                val task: Task? = dataSnapshot.getValue<Task>()
                Log.d(task.toString(),"original value")
                if (task != null) {
                    if (!(task in tasks)) {
                        tasks.add(task)
                        Log.d(task.toString(), "Step 1")

                    }
                    val adapter = TaskAdapter(tasks)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val newTask = dataSnapshot.getValue<Task>()
                val taskKey = dataSnapshot.key
                for ((index, task) in tasks.withIndex()) {
                    if (task.key == taskKey) {
                        if (newTask != null) {
                            tasks[index].completed = newTask.completed
                        }
                    }
                }
                val adapter = TaskAdapter(tasks)
                binding.recyclerView.adapter = adapter

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
                Toast.makeText(context, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show()
            }

        }
        myRef.addChildEventListener(childEventListener)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val name = bundle.getString("bundleKey")
            val list = bundle.getStringArray("bundleKey2")
            val date: Date = bundle.getSerializable("date") as Date
            Log.d("YEs",date.toString())
            if (list != null) {
                val currentTask = Task(name.toString(),list.asList(),false,date=date)
                val newData = myRef.push()
                currentTask.key = newData.key.toString()
                newData.setValue(currentTask)

            }
        }
        binding.button.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddTaskFragment()
            rootView.findNavController().navigate(action)
        }
        var builder = NotificationCompat.Builder(binding.root.context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle("Look Here")
            .setContentText("Test")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(binding.root.context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationID, builder.build())
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
            val channel = NotificationChannel("todo", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =binding.root.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
