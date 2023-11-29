package com.project.north_south.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.databinding.FragmentTripBinding
import com.project.north_south.viewModels.StopwatchViewModel
import com.project.north_south.viewModels.TripFragmentViewModel

class TripFragment() : Fragment() {
    private lateinit var binding: FragmentTripBinding
    private lateinit var tripFragmentViewModel: TripFragmentViewModel
    private lateinit var stopwatchViewModel: StopwatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripBinding.inflate(layoutInflater)
        tripFragmentViewModel = ViewModelProvider(this)[TripFragmentViewModel::class.java]
        stopwatchViewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripFragmentViewModel.initFields(binding)

        binding.nextStep.setOnClickListener {
            tripFragmentViewModel.nextStep(stopwatchViewModel)
        }
        binding.previousStep.setOnClickListener {
            tripFragmentViewModel.previousStep(stopwatchViewModel)
        }
        binding.cancelButton.setOnClickListener{
            tripFragmentViewModel.clearShare()
            childFragmentManager.beginTransaction()
                .replace(R.id.frame_place, RoadmapFragment.newInstance())
                .commit()
        }
        binding.startStopButton.setOnClickListener {
            tripFragmentViewModel.startStop(stopwatchViewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopwatchViewModel.stop()
    }

    companion object {
        @JvmStatic
        fun newInstance() : TripFragment{
            return TripFragment()
        }
    }
}