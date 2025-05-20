package com.example.cursova.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cursova.R
import com.example.cursova.SharedViewModelTransport
import com.example.cursova.adapter.AdapterForTransport
import com.example.cursova.databinding.FragmentHomeBinding
import com.example.cursova.model.transport.ITransport

class TransportHomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModelTransport by activityViewModels()

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

        val customAdapter = AdapterForTransport()
        binding.recycleViewTransports.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewTransports.adapter = customAdapter

        sharedViewModel.transportList.observe(viewLifecycleOwner) { transportList ->
            customAdapter.updateList(transportList)
        }
        customAdapter.setOnTransportClickListener(object : AdapterForTransport.OnTransportClickListener {
            override fun onTransportClick(transport: ITransport) {
                val bundle = Bundle().apply {
                    putString("transportName", transport.getName())
                    putString("transportType", transport.getTransportTypeName())
                    putInt("transportImageRes", transport.getImageResId())
                }
                findNavController().navigate(R.id.navigation_list_event, bundle)
            }

            override fun onEditClick(transport: ITransport) {
                val bundle = Bundle().apply {
                    putString("transportName", transport.getName())
                    putString("transportType", transport.getTransportTypeName())
                    putInt("transportImageRes", transport.getImageResId())
                }
                findNavController().navigate(R.id.navigation_list_event, bundle)
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
