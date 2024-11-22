package com.example.adaptersfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

// Abstract class to manage fragment transactions
abstract class SingleFragmentActivity : AppCompatActivity() {

    // Abstract method that subclasses will implement to return the fragment
    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the fragment already exists
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            // Create a new fragment and add it to the container
            val fragment = createFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
