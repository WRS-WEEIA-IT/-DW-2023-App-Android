package com.app.dw2023.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Model.Event
import com.app.dw2023.Adapter.EventAdapter
import com.app.dw2023.R
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class EventsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var eventList: ArrayList<Event>
    lateinit var eventAdapter: EventAdapter
    lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_events, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewEvents)
        Log.d("Message", "before context")
        recyclerView.layoutManager = LinearLayoutManager(activity)
        Log.d("Message", "after context")

        eventList = arrayListOf()

        eventAdapter = EventAdapter(eventList, requireContext())
        recyclerView.adapter = eventAdapter

        EventChangeListener()

        return view
    }


    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("lectures").orderBy("timeStart")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Toast.makeText(activity, "Firestore error hehe", Toast.LENGTH_SHORT).show()
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            eventList.add(dc.document.toObject(Event::class.java))
                        }
                    }

                    eventAdapter.notifyDataSetChanged()

                }

            })
        

    }

}