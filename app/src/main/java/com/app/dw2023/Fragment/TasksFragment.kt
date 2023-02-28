package com.app.dw2023.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.R
import com.app.dw2023.Task
import com.app.dw2023.TaskAdapter
import com.google.firebase.firestore.*

class TasksFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var tasksList: ArrayList<Task>
    lateinit var tasksAdapter: TaskAdapter
    lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        recyclerView = view.findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        tasksList = arrayListOf()

        tasksAdapter = TaskAdapter(tasksList, requireContext())
        recyclerView.adapter = tasksAdapter

        TaskChangeListener()

        return view
    }

    private fun TaskChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Toast.makeText(activity, "Firestore error hehe", Toast.LENGTH_SHORT).show()
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            tasksList.add(dc.document.toObject(Task::class.java))
                        }
                    }
                    tasksAdapter.notifyDataSetChanged()
                }
            })
    }

}