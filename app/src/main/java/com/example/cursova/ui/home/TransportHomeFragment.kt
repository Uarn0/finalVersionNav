package com.example.cursova.ui.home

import com.example.cursova.model.TransportWithEvents
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cursova.R
import com.example.cursova.adapter.AdapterForTransport
import com.example.cursova.databinding.FragmentHomeBinding
import com.example.cursova.model.transport.ITransport
import com.example.cursova.storage.JsonStorage

class TransportHomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var list: MutableList<TransportWithEvents> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = JsonStorage.load(requireContext())

        val transportAdapter = AdapterForTransport()
        binding.recycleViewTransports.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewTransports.adapter = transportAdapter

        transportAdapter.updateList(list.map { it.transport }.toMutableList())
        transportAdapter.onItemRemoved = { transport ->
            val idx = list.indexOfFirst { it.transport.getName() == transport.getName() && it.transport.getTransportType() == transport.getTransportType() }
            if (idx != -1) {
                list.removeAt(idx)
                JsonStorage.save(requireContext(), list)
                transportAdapter.updateList(list.map { it.transport }.toMutableList())
            }
        }
        transportAdapter.setOnTransportClickListener(object : AdapterForTransport.OnTransportClickListener {
            override fun onTransportClick(transport: ITransport) {
                val idx = list.indexOfFirst { it.transport.getName() == transport.getName() && it.transport.getTransportType() == transport.getTransportType() }
                if (idx != -1) {
                    val bundle = Bundle().apply {
                        putInt("transportIndex", idx)
                    }
                    findNavController().navigate(R.id.navigation_list_event, bundle)
                }
            }

            override fun onEditClick(transport: ITransport) {
                val idx = list.indexOfFirst { it.transport.getName() == transport.getName() && it.transport.getTransportType() == transport.getTransportType() }
                if (idx != -1) {
                    val bundle = Bundle().apply {
                        putInt("transportIndex", idx)
                    }
                    findNavController().navigate(R.id.navigation_list_event, bundle)
                }
            }
        })

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_transport)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
