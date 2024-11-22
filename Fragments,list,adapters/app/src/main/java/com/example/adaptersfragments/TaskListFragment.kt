package com.example.adaptersfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptersfragments.model.Task

class TaskListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Find the RecyclerView by its ID
        recyclerView = view.findViewById(R.id.task_recycler_view)

        // Set up the RecyclerView (adapter, layout manager, etc.)
        recyclerView.adapter = TaskAdapter(TaskStorage.instance.getAllTasks()) // Assuming you have a TaskAdapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        return view
    }
}
