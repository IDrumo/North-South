package com.project.north_south.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.ViewModels.TicketFragmentViewModel
import com.project.north_south.databinding.FragmentTicketBinding

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
            ticketViewModel.check(requireView(), binding)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TicketFragment()
    }
}