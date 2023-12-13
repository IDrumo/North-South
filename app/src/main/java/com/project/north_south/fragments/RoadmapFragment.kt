package com.project.north_south.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.north_south.R
import com.project.north_south.RoadmapAdapter
import com.project.north_south.databinding.FragmentRoadmapBinding
import com.project.north_south.subAlgorithms.Storage
import com.project.north_south.viewModels.RoadmapFragmentViewModel
import models.TripItem


class RoadmapFragment : Fragment(), RoadmapAdapter.Listener {
    private lateinit var binding: FragmentRoadmapBinding
    private lateinit var roadmapFragmentViewModel: RoadmapFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoadmapBinding.inflate(layoutInflater)
        roadmapFragmentViewModel = ViewModelProvider(this)[RoadmapFragmentViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roadmapFragmentViewModel.rcViewInit(binding.rcView, this, requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance(): RoadmapFragment {
            return RoadmapFragment()
        }
    }

    override fun onClick(item: TripItem) {
        Storage(requireContext()).setTrip(item)
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_place, TripFragment.newInstance())
            .commit()
    }


}