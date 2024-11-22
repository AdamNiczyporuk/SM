package com.example.adaptersfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.adaptersfragments.R
import java.util.UUID


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the taskId from the Intent and create the TaskFragment
        val taskId = intent.getSerializableExtra(TaskListFragment.KEY_EXTRA_TASK_ID) as UUID
        val taskFragment = TaskFragment.newInstance(taskId)

        // Load the fragment into the activity
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .commit()
    }
}
