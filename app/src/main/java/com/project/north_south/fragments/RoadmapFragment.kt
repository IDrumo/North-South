package com.project.north_south.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.north_south.R
import com.project.north_south.RoadmapAdapter
import com.project.north_south.activity.TripPage
import models.TripItem


class RoadmapFragment : Fragment(), RoadmapAdapter.Listener {

    private lateinit var adapter: RoadmapAdapter
    private val tripActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Обработка результата
            val data = result.data
            // ...
        }
    }

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

        val data = arguments?.getSerializable("key") as ArrayList<TripItem>

        adapter = RoadmapAdapter(this)
        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = adapter

        adapter.submitList(data)



        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(data: ArrayList<TripItem>?): RoadmapFragment {
            val fragment = RoadmapFragment()
            val args = Bundle()
            args.putSerializable("key", data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(item: TripItem) {
        val intent = Intent(requireContext(), TripPage::class.java).putExtra("data", item)
        tripActivityResultLauncher.launch(intent)
    }


}