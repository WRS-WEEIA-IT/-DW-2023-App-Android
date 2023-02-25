package com.app.dw2023.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Model.Event
import com.app.dw2023.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(var eventList: ArrayList<Event>): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventCardTitle : TextView = itemView.findViewById(R.id.eventCardTitle)
        var eventCardDesc : TextView = itemView.findViewById(R.id.eventCardDesc)
        var eventCardDate : TextView = itemView.findViewById(R.id.eventCardDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_card_design, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventCardTitle.text = event.company
        holder.eventCardDesc.text = event.desc

        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val date = event.date?.toDate()
        holder.eventCardDate.text = simpleDateFormat.format(date!!)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}