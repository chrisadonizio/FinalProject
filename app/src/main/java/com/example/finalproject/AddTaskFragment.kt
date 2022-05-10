package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentAddTaskBinding
import com.example.finalproject.databinding.FragmentMainBinding


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private var listOfSteps = mutableListOf<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        val rootView = binding.root

        binding.addSteps.setOnClickListener{
            if(listOfSteps.size<=10) {
                val word:String = binding.stepAddButton.text.toString()
                listOfSteps.add(word)
                binding.textView3.text =
                    "${binding.textView3.text.toString()}\n${listOfSteps.lastIndexOf(word)+1}. $word"
            }
        }
        binding.submit.setOnClickListener{
          setFragmentResult("requestKey",bundleOf("bundleKey" to binding.nameEnter.text.toString(),"bundleKey2" to listOfSteps.toTypedArray()))
            rootView.findNavController().navigateUp()
        }
        return rootView
    }
}
