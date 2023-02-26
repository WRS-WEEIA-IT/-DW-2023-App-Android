package com.app.dw2023.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Model.Event
import com.app.dw2023.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(var eventList: ArrayList<Event>, var context: Context): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventCardTitle : TextView = itemView.findViewById(R.id.eventCardTitle)
        var eventCardDesc : TextView = itemView.findViewById(R.id.eventCardDesc)
        var eventCardDate : TextView = itemView.findViewById(R.id.eventCardDate)
        var eventCardSignUpButton : AppCompatButton = itemView.findViewById(R.id.eventCardSignUpButton)
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

        holder.eventCardSignUpButton.setOnClickListener {
//            Toast.makeText(context, "You clicked $position event", Toast.LENGTH_SHORT).show()
            val uri = Uri.parse("https://weeia.p.lodz.pl/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}