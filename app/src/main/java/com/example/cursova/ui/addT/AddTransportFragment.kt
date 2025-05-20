package com.example.cursova.ui.addT

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cursova.R
import com.example.cursova.SharedViewModelTransport
import com.example.cursova.databinding.FragmentAddTransportBinding
import com.example.cursova.model.transport.Car
import com.example.cursova.model.transport.ITransport
import com.example.cursova.model.transport.Minibus
import com.example.cursova.model.transport.Motorcycle

class AddTransportFragment : Fragment() {

    private var _binding: FragmentAddTransportBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModelTransport by activityViewModels()

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

            val transport: ITransport = when (selectedRadioButtonId) {
                R.id.car -> Car(name)
                R.id.minibus -> Minibus(name)
                R.id.motorcycle -> Motorcycle(name)
                else -> throw IllegalArgumentException("Невідомий тип транспорту")
            }

            sharedViewModel.addTransport(transport)
            findNavController().navigate(R.id.navigation_list_transport)
        }
    }
}