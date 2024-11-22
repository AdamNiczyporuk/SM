package com.example.adaptersfragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptersfragments.model.Task
import android.widget.TextView

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskDate.text = task.date.toString()
        holder.taskDone.isChecked = task.done
    }

    override fun getItemCount(): Int = tasks.size

    // ViewHolder to hold individual item views
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.task_name)
        val taskDate: TextView = itemView.findViewById(R.id.task_date)
        val taskDone: CheckBox = itemView.findViewById(R.id.task_done)
    }
}
