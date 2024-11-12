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

        // Inicjalizacja RecyclerView i ustawienie LayoutManagera
        recyclerView = findViewById(R.id.sensor_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicjalizacja SensorManager i pobranie listy wszystkich czujników
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        // Sprawdzenie i ustawienie adaptera dla RecyclerView
        adapter = SensorAdapter(sensorList)
        recyclerView.adapter = adapter

        // Notyfikacja adaptera o zmianie danych
        adapter.notifyDataSetChanged()
    }
}