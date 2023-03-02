package com.app.dw2023.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Activity.MainActivity
import com.app.dw2023.R
import com.app.dw2023.Model.Task
import com.app.dw2023.Adapter.TaskAdapter
import com.google.firebase.firestore.*

class TasksFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var tasksList: ArrayList<Task>
    lateinit var tasksAdapter: TaskAdapter
    lateinit var db : FirebaseFirestore
    lateinit var validQRCodes: List<String>
    lateinit var tasksPoints: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        recyclerView = view.findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        tasksPoints = view.findViewById(R.id.tasksTextViewPoints)

        tasksList = arrayListOf()  // to musi byc globalne

        tasksAdapter = TaskAdapter(tasksList, requireContext())
        tasksPoints.text = MainActivity.gainedPoints.toString()
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
                    cleanFakeCodesFromDevice()
                    showPoints()
                    tasksAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun cleanFakeCodesFromDevice() {
        validQRCodes = tasksList.mapNotNull { it.qrCode }
        for (i in validQRCodes) {
            Log.d("verySecretMessage", "Firebase code: $i")
        }
        for (i in MainActivity.loadedQrCodes) {
            Log.d("verySecretMessage", "Scanned locally code before deletion: $i")
        }
        if (MainActivity.loadedQrCodes.retainAll(validQRCodes.toSet())) {
            Log.d("verySecretMessage", "deleted some fake codes")
        }
        for (i in MainActivity.loadedQrCodes) {
            Log.d("verySecretMessage", "Scanned locally code after deletion: $i")
        }
    }

    private fun showPoints() {
        val pointsList = tasksList.filter { MainActivity.loadedQrCodes.contains(it.qrCode) }.map { it.points!!.toInt() }
        for (i in pointsList) {
            Log.d("verySecretMessage", "Gained point: $i")
        }
        tasksPoints.text = pointsList.sum().toString()
    }

}