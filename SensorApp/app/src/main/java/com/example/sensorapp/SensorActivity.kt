package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorapp.SensorAdapter

class SensorActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SensorAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_activity)

        recyclerView = findViewById(R.id.sensor_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        adapter = SensorAdapter(sensorList, this)  // Przekazujemy `this` jako kontekst
        recyclerView.adapter = adapter
    }
}