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
import models.TripItem
import java.lang.reflect.Type

class RoadmapFragmentViewModel(context: Application) : AndroidViewModel(context){
    private val sharedTrip: SharedPreferences = context.getSharedPreferences("trip_info", Context.MODE_PRIVATE)
    private val sharedRoadmap: SharedPreferences = context.getSharedPreferences("roadmap_info", Context.MODE_PRIVATE)

    fun setTrip(trip: TripItem){
        val gson = Gson()
        val json = gson.toJson(trip)
        sharedTrip.edit().putString("current_trip", json).putBoolean("sub_frame_part_active", true).apply()
    }

    fun rcViewInit(rcView: RecyclerView, roadmapFragment: RoadmapFragment, requireContext: Context) {
        val gson = Gson()
        val jsonList = sharedRoadmap.getString("roadmap", "")
        val type: Type = object : TypeToken<ArrayList<TripItem?>?>() {}.type
        val list = gson.fromJson<ArrayList<TripItem>>(jsonList, type)
        if (!list.isNullOrEmpty()) {
            val adapter = RoadmapAdapter(roadmapFragment)
            rcView.layoutManager = LinearLayoutManager(requireContext)
            rcView.adapter = adapter
            adapter.submitList(list)
        }
    }
}