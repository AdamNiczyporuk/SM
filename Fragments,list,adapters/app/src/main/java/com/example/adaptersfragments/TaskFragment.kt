package com.example.adaptersfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.adaptersfragments.model.Task
import java.util.Date
import java.util.UUID

class TaskFragment : Fragment() {

    private lateinit var task: Task
    private lateinit var nameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var doneCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)

        // Find the views by ID
        nameTextView = view.findViewById(R.id.task_name)
        dateTextView = view.findViewById(R.id.task_date)
        doneCheckBox = view.findViewById(R.id.task_done)

        // Retrieve the task ID passed from the previous fragment or activity
        val taskId = arguments?.getSerializable(ARG_TASK_ID) as UUID
        task = TaskStorage.instance.getTaskById(taskId) ?: throw IllegalArgumentException("Task not found")

        // Set the task data to the views
        nameTextView.text = task.name
        dateTextView.text = task.date.toString()
        doneCheckBox.isChecked = task.done

        return view
    }

    companion object {
        private const val ARG_TASK_ID = "task_id"

        // Static method to create a new instance of TaskFragment with taskId
        fun newInstance(taskId: UUID): TaskFragment {
            val bundle = Bundle().apply {
                putSerializable(ARG_TASK_ID, taskId)
            }
            return TaskFragment().apply {
                arguments = bundle
            }
        }
    }
}
