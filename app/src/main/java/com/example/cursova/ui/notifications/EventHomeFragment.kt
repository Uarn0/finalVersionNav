package com.example.cursova.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cursova.databinding.FragmentSecondHomeBinding
import com.example.cursova.R

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
        val name = arguments?.getString("transportName")
        val type = arguments?.getString("transportType")
        val imageRes = arguments?.getInt("transportImageRes")
        binding.transportName.text = name
        binding.transportType.text = type
        binding.icon.setImageResource(imageRes ?: R.drawable.ic_car)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
