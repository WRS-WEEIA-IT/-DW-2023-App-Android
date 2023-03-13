package com.app.dw2023.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Adapter.EventAdapter
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.EVENTS_FRAGMENT_INDEX
import com.app.dw2023.Global.LOG_MESSAGE
import com.app.dw2023.Model.Event
import com.app.dw2023.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*

class EventsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var eventAdapter: EventAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_events, container, false)

        AppData.lastSelectedIndex = EVENTS_FRAGMENT_INDEX

        recyclerView = view.findViewById(R.id.recyclerViewEvents)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        eventAdapter = EventAdapter(AppData.eventList, requireContext())
        recyclerView.adapter = eventAdapter

        eventChangeListener()

        return view
    }

    private fun eventChangeListener() {

        AppData.eventList.clear()

        db = FirebaseFirestore.getInstance()
        db.collection("lectures").whereGreaterThan("timeEnd", Timestamp.now())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.e(LOG_MESSAGE, error.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val event = dc.document.toObject(Event::class.java)
                            AppData.eventList.add(event)
                        }
                    }

                    keepOnlyUniqueEvents()
                    AppData.eventList.sortWith(compareBy({it.timeStart}, {it.timeEnd}))
                }
            })

        db.collection("workshops").whereGreaterThan("timeEnd", Timestamp.now())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.e(LOG_MESSAGE, error.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val event = dc.document.toObject(Event::class.java)
                            event.eventType = "workshops"
                            AppData.eventList.add(event)
                        }
                    }

                    keepOnlyUniqueEvents()
                    AppData.eventList.sortWith(compareBy({it.timeStart}, {it.timeEnd}))
                    eventAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun keepOnlyUniqueEvents() {
        val set = mutableSetOf<Event>()
        for (task in AppData.eventList) {
            set.add(task)
        }
        AppData.eventList.clear()
        AppData.eventList.addAll(set)
    }
}