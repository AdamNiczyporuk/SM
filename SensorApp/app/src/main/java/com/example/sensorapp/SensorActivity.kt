package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorapp.SensorAdapter
import androidx.appcompat.widget.Toolbar

class SensorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private lateinit var adapter: SensorAdapter
    private lateinit var sensorCountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_activity)

        // Inicjalizacja paska narzędzi
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Znalezienie widoków
        recyclerView = findViewById(R.id.sensor_recycler_view)
        sensorCountTextView = findViewById(R.id.sensorCountTextView) // Inicjalizujemy TextView

        recyclerView.layoutManager = LinearLayoutManager(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        if (sensorList.isEmpty()) {
            // Jeśli lista sensorów jest pusta, wyświetl informację o braku sensorów
            Toast.makeText(this, "Brak dostępnych sensorów", Toast.LENGTH_SHORT).show()
        } else {
            // Inicjalizuj adapter, jeśli sensory są dostępne
            adapter = SensorAdapter(sensorList, this)
            recyclerView.adapter = adapter
        }

        // Ustawienie liczby dostępnych sensorów
        sensorCountTextView.text = "Available Sensors: ${sensorList.size}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Ładowanie menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_sensor_count -> {
                val sensorCount = sensorList.size
                val message = "Sensor Count: $sensorCount"
                item.title = message  // Zaktualizuj tytuł przycisku na pasku narzędzi
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
