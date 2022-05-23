package com.example.finalproject

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentAddTaskBinding
import com.example.finalproject.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private var listOfSteps = mutableListOf<String>()
    @RequiresApi(Build.VERSION_CODES.N)
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
        var cal = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format,Locale.US)
                binding.textView2.text = sdf.format(cal.getTime())
            }
        }
        binding.calender!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(binding.root.context,dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
        binding.submit.setOnClickListener{
            var bundle = bundleOf( "bundleKey" to binding.nameEnter.text.toString(),
                "bundleKey2" to listOfSteps.toTypedArray())
            bundle.putSerializable("date",cal.getTime())
          setFragmentResult("requestKey",bundle
          )

            rootView.findNavController().navigateUp()
        }


        return rootView
    }
}
