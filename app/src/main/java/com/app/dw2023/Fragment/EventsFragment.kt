package com.app.dw2023.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dw2023.Adapter.EventAdapter
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.EVENTS_FRAGMENT_INDEX
import com.app.dw2023.R
class EventsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var eventAdapter: EventAdapter

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

        eventAdapter.notifyDataSetChanged()

        return view
    }
}