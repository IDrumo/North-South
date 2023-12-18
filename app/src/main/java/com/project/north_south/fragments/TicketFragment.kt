package com.project.north_south.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.viewModels.TicketFragmentViewModel
import com.project.north_south.databinding.FragmentTicketBinding
import com.project.north_south.subAlgorithms.Storage

class TicketFragment : Fragment() {
    private lateinit var binding: FragmentTicketBinding
    private lateinit var ticketViewModel: TicketFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketBinding.inflate(layoutInflater)
        ticketViewModel = ViewModelProvider(this)[TicketFragmentViewModel::class.java]

        binding.checkButton.setOnClickListener{
            ticketViewModel.check(requireContext(), requireView(), binding)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ticketViewModel.ticketDate.observe(requireActivity()){
            Storage(requireContext()).savePassengerInfo(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TicketFragment()
    }
}