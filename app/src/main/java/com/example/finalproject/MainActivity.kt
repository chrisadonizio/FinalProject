package com.example.finalproject

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")
//Adds value to database
//        myRef.setValue("Hello, World!")

//        val postListener = object:ValueEventListener{
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val post = dataSnapshot.getValue()
//                Log.d(post.toString(),"test")
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        myRef.addValueEventListener(postListener)

    }
}