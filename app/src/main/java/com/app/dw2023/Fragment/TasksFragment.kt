package com.app.dw2023.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.R
import com.app.dw2023.Adapter.TaskAdapter
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.TASKS_FRAGMENT_INDEX

class TasksFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tasksAdapter: TaskAdapter
    private lateinit var tasksPoints: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        AppData.lastSelectedIndex = TASKS_FRAGMENT_INDEX

        recyclerView = view.findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        tasksPoints = view.findViewById(R.id.tasksTextViewPoints)

        tasksAdapter = TaskAdapter(AppData.tasksList, requireContext())
        tasksPoints.text = AppData.gainedPoints.toString()
        recyclerView.adapter = tasksAdapter

        tasksAdapter.notifyDataSetChanged()

        return view
    }
}