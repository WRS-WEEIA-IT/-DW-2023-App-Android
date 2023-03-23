package com.app.dw2023.Fragment

import android.os.Bundle
import android.util.Log
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
import com.app.dw2023.Global.LOG_MESSAGE
import com.app.dw2023.Global.TASKS_FRAGMENT_INDEX
import com.app.dw2023.Model.Task
import com.google.firebase.firestore.*

class TasksFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tasksAdapter: TaskAdapter
    private lateinit var tasksPoints: TextView
    private lateinit var db: FirebaseFirestore


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
        val points = "You have ${AppData.gainedPoints} points!"
        tasksPoints.text = points
        recyclerView.adapter = tasksAdapter

        tasksChangeListener()

        return view
    }

    private fun tasksChangeListener() {

        AppData.tasksList.clear()

        db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.e(LOG_MESSAGE, error.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val task = dc.document.toObject(Task::class.java)
                            AppData.tasksList.add(task)
                        }
                    }

                    AppData.tasksList.filter { it.qrCode in AppData.loadedQrCodes }.forEach { it.isDone = true }
                    keepOnlyUniqueTasks()
                    AppData.tasksList.sortBy { it.taskNumber }
                    AppData.tasksList.sortBy { it.isDone }

                    tasksAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun keepOnlyUniqueTasks() {
        val set = mutableSetOf<Task>()
        for (task in AppData.tasksList) {
            set.add(task)
        }
        AppData.tasksList.clear()
        AppData.tasksList.addAll(set)
    }
}