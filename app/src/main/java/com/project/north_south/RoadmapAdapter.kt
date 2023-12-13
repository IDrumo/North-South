package com.project.north_south

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.north_south.databinding.TripListItemBinding
import models.TripItem
import java.time.format.DateTimeFormatter

class RoadmapAdapter(private val listener: Listener): ListAdapter<TripItem, RoadmapAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = TripListItemBinding.bind(view)

        fun setData(item: TripItem, listener: Listener) = with(binding){
            time.text = item.time_start
            destination.text = item.stations[0]
            itemView.setOnClickListener{
                listener.onClick(item)
            }
        }
        companion object{
            fun  create(parent: ViewGroup) :ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.trip_list_item, parent,false))
            }
        }
    }
    class ItemComparator: DiffUtil.ItemCallback<TripItem>(){
        override fun areItemsTheSame(oldItem: TripItem, newItem: TripItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TripItem, newItem: TripItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener{
        fun onClick(item: TripItem){

        }
    }
}