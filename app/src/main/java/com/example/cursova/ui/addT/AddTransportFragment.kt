package com.example.cursova.ui.addT

import com.example.cursova.model.TransportWithEvents
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cursova.R
import com.example.cursova.databinding.FragmentAddTransportBinding
import com.example.cursova.model.transport.Car
import com.example.cursova.model.transport.ITransport
import com.example.cursova.model.transport.Minibus
import com.example.cursova.model.transport.Motorcycle
import com.example.cursova.storage.JsonStorage

class AddTransportFragment : Fragment() {

    private var _binding: FragmentAddTransportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransportBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val name = binding.nameOfTransport.text.toString()
            val selectedRadioButtonId = binding.transportTypeGroup.checkedRadioButtonId

            if (name.isBlank() || selectedRadioButtonId == -1) {
                Toast.makeText(requireContext(), "Будь ласка, введіть назву і виберіть тип транспорту", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ITransport: ITransport = when (selectedRadioButtonId) {
                R.id.car -> Car(name)
                R.id.minibus -> Minibus(name)
                R.id.motorcycle -> Motorcycle(name)
                else -> throw IllegalArgumentException("Невідомий тип транспорту")
            }
            val list = JsonStorage.load(requireContext())
            list.add(TransportWithEvents(ITransport, mutableListOf()))
            JsonStorage.save(requireContext(), list)

            findNavController().navigate(R.id.navigation_list_transport)
        }
    }
}