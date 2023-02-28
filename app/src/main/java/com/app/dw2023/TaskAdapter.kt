package com.app.dw2023

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Constants.ImagesMap

class TaskAdapter(var tasksArray: ArrayList<Task>, var context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskButton : AppCompatButton = itemView.findViewById(R.id.tasksCardPointsButton)
        var taskCardImageView : ImageView = itemView.findViewById(R.id.tasksCardImageView)
        var taskTitle : TextView = itemView.findViewById(R.id.tasksTitleTextView)
        var taskTips : TextView = itemView.findViewById(R.id.tasksTipsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_card_design, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksArray[position]
        val points = "${task.points} points"

        holder.taskButton.text = points
        holder.taskTitle.text = task.title
        holder.taskTips.text = task.description

        val imageSource = task.imageSource
        val drawableId = ImagesMap.imagesMap[imageSource] ?: R.drawable.event_card_background
        holder.taskCardImageView.setImageResource(drawableId)
        holder.taskCardImageView.alpha = 0.2F

        holder.taskButton.isClickable = false

        //TODO buttonOnClickListener, dodajemy do SharedPreferences pkt
    }

    override fun getItemCount(): Int {
        return tasksArray.size
    }

}