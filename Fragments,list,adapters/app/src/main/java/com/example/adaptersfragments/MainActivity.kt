package com.example.adaptersfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.adaptersfragments.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pobranie obiektu FragmentManager
        val fragmentManager = supportFragmentManager

        // Sprawdzanie, czy fragment już istnieje
        var fragment = fragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            // Jeśli fragment nie istnieje, tworzymy nowy
            fragment = TaskFragment()

            // Rozpoczynamy transakcję i dodajemy fragment
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)  // Dodajemy fragment do kontenera
                .commit()  // Zatwierdzamy transakcję
        }
    }
}
