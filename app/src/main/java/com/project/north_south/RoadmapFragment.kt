package com.project.north_south

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RoadmapFragment : Fragment() {

    private lateinit var adapter: RoadmapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_roadmap, container, false)
        val rcView = rootView.findViewById<RecyclerView>(R.id.rcView)

        adapter = RoadmapAdapter()
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RoadmapFragment()
    }


}