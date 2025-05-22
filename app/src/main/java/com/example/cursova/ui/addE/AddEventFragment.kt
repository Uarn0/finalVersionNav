package com.example.cursova.ui.addE

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cursova.R
import com.example.cursova.databinding.FragmentAddEventBinding
import com.example.cursova.model.event.IEvent
import com.example.cursova.model.event.RepairEvent
import com.example.cursova.model.event.ServiceEvent
import com.example.cursova.model.event.TripEvent
import com.example.cursova.storage.JsonStorage

class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transportIndex = arguments?.getInt("transportIndex") ?: -1

        binding.saveButton.setOnClickListener {
            val name = binding.typeOfEvent.text.toString()
            val selectedRadioButtonId = binding.eventTypeGroup.checkedRadioButtonId

            if (selectedRadioButtonId == -1) {
                Toast.makeText(requireContext(), "Будь ласка, виберіть тип події", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event: IEvent = when (selectedRadioButtonId) {
                R.id.repair -> RepairEvent(name)
                R.id.trip -> TripEvent(name)
                R.id.service -> ServiceEvent(name)
                else -> throw IllegalArgumentException("Невідомий тип транспорту")
            }

            val transportList = JsonStorage.load(requireContext())
            val selectedTransport = transportList.getOrNull(transportIndex)

            if (selectedTransport != null) {
                selectedTransport.events.add(event)
                JsonStorage.save(requireContext(), transportList)
                val bundle = Bundle().apply {
                    putInt("transportIndex", transportIndex)
                }
                findNavController().navigate(R.id.navigation_list_event, bundle)
            } else {
                Toast.makeText(requireContext(), "Не знайдено транспорт!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}