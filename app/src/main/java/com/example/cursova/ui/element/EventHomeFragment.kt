package com.example.cursova.ui.element

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cursova.databinding.FragmentSecondHomeBinding
import com.example.cursova.R
import com.example.cursova.adapter.AdapterForEvent
import com.example.cursova.storage.JsonStorage

class EventHomeFragment : Fragment() {

    private var _binding: FragmentSecondHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transportIndex = arguments?.getInt("transportIndex") ?: -1
        val list = JsonStorage.load(requireContext())
        val item = list.getOrNull(transportIndex)
        if (item == null) {
            findNavController().popBackStack()
            return
        }

        binding.transportName.text = item.transport.getName()
        binding.transportType.text = item.transport.getTransportTypeName()
        binding.icon.setImageResource(item.transport.getImageResId())

        val eventAdapter = AdapterForEvent()
        binding.recycleViewEvent.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewEvent.adapter = eventAdapter

        eventAdapter.updateList(item.events.toMutableList())

        eventAdapter.onItemRemoved = { event ->
            val transportList = JsonStorage.load(requireContext())
            val current = transportList.getOrNull(transportIndex)
            current?.events?.remove(event)
            JsonStorage.save(requireContext(), transportList)
            eventAdapter.updateList(current?.events?.toMutableList() ?: mutableListOf())
        }

        binding.fabAdd.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("transportIndex", transportIndex)
            }
            findNavController().navigate(R.id.navigation_add_event, bundle)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
