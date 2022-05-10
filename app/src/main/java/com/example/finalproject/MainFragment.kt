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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.ContentValues.TAG

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
        val postListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var post = dataSnapshot.getValue()
                if(post!= null){

                    }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
        val adapter = TaskAdapter(tasks)
        binding.recyclerView.adapter =adapter
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val name = bundle.getString("bundleKey")
            val list = bundle.getStringArray("bundleKey2")
            if (list != null) {
                val currentTask = Task(name.toString(),list.asList(),false)
                tasks.add(currentTask)
                val newData = myRef.push()
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