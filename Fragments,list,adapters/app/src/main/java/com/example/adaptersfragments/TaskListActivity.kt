package com.example.adaptersfragments

import android.os.Bundle
import androidx.fragment.app.Fragment

// TaskListActivity extends SingleFragmentActivity and provides the fragment to display
class TaskListActivity : SingleFragmentActivity() {

    // Override to return the TaskListFragment
    override fun createFragment(): Fragment {
        return TaskListFragment() // Return the TaskListFragment here
    }
}
