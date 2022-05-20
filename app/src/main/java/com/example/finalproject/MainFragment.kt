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
import android.content.ContentValues.TAG
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private  var tasks = mutableListOf<Task>()
    private val binding get() = _binding!!
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("message")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val rootView = binding.root
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
            if (list != null) {
                val currentTask = Task(name.toString(),list.asList(),false)
                val newData = myRef.push()
                currentTask.key = newData.key.toString()
                newData.setValue(currentTask)

            }
        }
        binding.button.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddTaskFragment()
            rootView.findNavController().navigate(action)
        }
        return rootView
    }

}
