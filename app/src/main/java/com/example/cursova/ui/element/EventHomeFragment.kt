package com.example.cursova.ui.element

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cursova.databinding.FragmentSecondHomeBinding
import com.example.cursova.R
import com.example.cursova.SharedViewModelEvent
import com.example.cursova.adapter.AdapterForEvent
import kotlin.getValue

class EventHomeFragment : Fragment() {

    private var _binding: FragmentSecondHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModelEvent by activityViewModels()

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
        val name = arguments?.getString("transportName")
        val type = arguments?.getString("transportType")
        val imageRes = arguments?.getInt("transportImageRes")

        binding.transportName.text = name
        binding.transportType.text = type
        binding.icon.setImageResource(imageRes ?: R.drawable.ic_car)

        val customAdapter = AdapterForEvent()
        binding.recycleViewEvent.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewEvent.adapter = customAdapter
        sharedViewModel.eventList.observe(viewLifecycleOwner){
            eventList -> customAdapter.updateList(eventList)
        }
        binding.fabAdd.setOnClickListener {
            val bundle = Bundle().apply {
                putString("transportName", binding.transportName.text.toString())
                putString("transportType", binding.transportType.text.toString())
                putInt("transportImageRes", imageRes ?: R.drawable.ic_car)
            }
            findNavController().navigate(R.id.navigation_add_event, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
