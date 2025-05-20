package com.example.cursova.ui.addE

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cursova.R
import com.example.cursova.SharedViewModelEvent
import com.example.cursova.databinding.FragmentAddEventBinding
import com.example.cursova.model.event.IEvent
import com.example.cursova.model.event.RepairEvent
import com.example.cursova.model.event.ServiceEvent
import com.example.cursova.model.event.TripEvent
import kotlin.getValue

class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModelEvent by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            val name = binding.typeOfEvent.text.toString()
            val selectedRadioButtonId = binding.eventTypeGroup.checkedRadioButtonId

            val transportName = arguments?.getString("transportName") ?: ""
            val transportType = arguments?.getString("transportType") ?: ""
            val transportImageRes = arguments?.getInt("transportImageRes") ?: R.drawable.ic_car

            if(selectedRadioButtonId == -1){
                Toast.makeText(requireContext(), "Будь ласка, виберіть тип події", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val event: IEvent = when(selectedRadioButtonId){
                R.id.repair -> RepairEvent(name)
                R.id.trip -> TripEvent(name)
                R.id.service -> ServiceEvent(name)
                else -> throw IllegalArgumentException("Невідомий тип транспорту")
            }

            val bundle = Bundle().apply {
                putString("transportName", transportName)
                putString("transportType", transportType)
                putInt("transportImageRes", transportImageRes)
            }
            sharedViewModel.addEvent(event)
            findNavController().navigate(R.id.navigation_list_event, bundle)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}