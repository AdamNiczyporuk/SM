package com.example.adaptersfragments

import TaskAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptersfragments.model.Task

class TaskListFragment : Fragment() {

    companion object {
        const val KEY_EXTRA_TASK_ID = "com.example.adaptersfragments.KEY_EXTRA_TASK_ID"
    }


    private lateinit var recyclerView: RecyclerView
    private var adapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = view.findViewById(R.id.task_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        updateView()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }

    private fun updateView() {
        val taskStorage = TaskStorage.instance
        val tasks = taskStorage.getAllTasks()

        if (adapter == null) {
            adapter = TaskAdapter(tasks)
            recyclerView.adapter = adapter
        } else {
            adapter?.notifyDataSetChanged()
        }
    }
}

