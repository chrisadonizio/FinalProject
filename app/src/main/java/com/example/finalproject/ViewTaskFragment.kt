package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.databinding.FragmentViewTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class ViewTaskFragment : Fragment() {

    private var _binding: FragmentViewTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = ViewTaskFragmentArgs.fromBundle(requireArguments())
        binding.textView.text = args.title
        if(args.date!=null) {
            val cal: Calendar = Calendar.getInstance()
            cal.setTime(args.date)
            val format = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            binding.textView4.text = "You should complete this by " + sdf.format(cal.getTime())
        }
        val steps = args.steps
        var message:String = ""
        if (steps != null) {
            for ((index, step) in steps.withIndex()) {
                message += "${index+1}. $step\n"
            }
            binding.textView5.text = message

        }

        return rootView
    }
}