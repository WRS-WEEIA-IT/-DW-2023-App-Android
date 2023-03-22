package com.app.dw2023.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Global.ImagesMap
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
        var eventCardImageView : ImageView = itemView.findViewById(R.id.eventCardImageView)
        var eventCardView: CardView = itemView.findViewById(R.id.eventCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_card_design, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        val type = if (event.eventType == "lecture") {
            "Lecture"
        } else "Workshop"
        holder.eventCardTitle.text = type
        holder.eventCardDesc.text = event.title

        val dateStart = event.timeStart?.toDate()
        val dateEnd = event.timeEnd?.toDate()

        val sdfStart = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        sdfStart.timeZone = TimeZone.getTimeZone("GMT+1:00")
        val sdfEnd = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdfEnd.timeZone = TimeZone.getTimeZone("GMT+1:00")

        val date = "${sdfStart.format(dateStart!!)} - ${sdfEnd.format(dateEnd!!)}"
        holder.eventCardDate.text = date

        val imageSource = event.imageSource
        val drawableId = ImagesMap.imagesMap[imageSource] ?: R.drawable.office
        holder.eventCardImageView.setImageResource(drawableId)
        holder.eventCardImageView.alpha = 0.2F

        holder.eventCardSignUpButton.setOnClickListener {
            val uri = Uri.parse("https://weeia.p.lodz.pl/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }

        holder.eventCardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_anim))
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}