package com.project.north_south.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.north_south.RoadmapAdapter
import com.project.north_south.fragments.RoadmapFragment
import com.project.north_south.subAlgorithms.Storage
import models.TripItem
import java.lang.reflect.Type

class RoadmapFragmentViewModel(context: Application) : AndroidViewModel(context){


    fun rcViewInit(rcView: RecyclerView, roadmapFragment: RoadmapFragment, requireContext: Context) {

        val list = Storage(requireContext).getRoadmap()
        if (!list.isNullOrEmpty()) {
            val adapter = RoadmapAdapter(roadmapFragment)
            rcView.layoutManager = LinearLayoutManager(requireContext)
            rcView.adapter = adapter
            adapter.submitList(list)
        }
    }
}