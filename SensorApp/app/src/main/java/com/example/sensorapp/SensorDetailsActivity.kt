package com.example.sensorapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sensorapp.R

class SensorDetailsActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensorLight: Sensor? = null
    private var sensorPressure: Sensor? = null
    private lateinit var sensorLightTextView: TextView
    private lateinit var sensorPressureTextView: TextView
    private lateinit var sensorDetailsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_details)

        // Znalezienie widoku
        sensorLightTextView = findViewById(R.id.sensor_light_label)
        sensorPressureTextView = findViewById(R.id.sensor_pressure_label)
        sensorDetailsTextView = findViewById(R.id.sensor_details_label)

        // Pobranie SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val sensorType = intent.getIntExtra("SENSOR_TYPE", -1)

        if (sensorType != -1) {
            when (sensorType) {
                Sensor.TYPE_LIGHT -> {
                    sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
                    if (sensorLight != null) {
                        sensorLightTextView.visibility = View.VISIBLE // Pokaż wartość sensora światła
                        sensorPressureTextView.visibility = View.GONE // Ukryj sensor ciśnienia
                    } else {
                        sensorLightTextView.text = getString(R.string.missing_sensor)
                        sensorLightTextView.visibility = View.VISIBLE
                        sensorPressureTextView.visibility = View.GONE
                        sensorDetailsTextView.text = getString(R.string.sensor_not_configured) // Komunikat
                    }
                }
                Sensor.TYPE_PRESSURE -> {
                    sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
                    if (sensorPressure != null) {
                        sensorPressureTextView.visibility = View.VISIBLE // Pokaż wartość sensora ciśnienia
                        sensorLightTextView.visibility = View.GONE // Ukryj sensor światła
                    } else {
                        sensorPressureTextView.text = getString(R.string.missing_sensor)
                        sensorPressureTextView.visibility = View.VISIBLE
                        sensorLightTextView.visibility = View.GONE
                        sensorDetailsTextView.text = getString(R.string.sensor_not_configured) // Komunikat
                    }
                }
                else -> {
                    sensorLightTextView.visibility = View.GONE
                    sensorPressureTextView.visibility = View.GONE
                    sensorDetailsTextView.text = getString(R.string.sensor_not_configured) // Komunikat o braku sensora
                }
            }
        } else {
            sensorLightTextView.visibility = View.GONE
            sensorPressureTextView.visibility = View.GONE
            sensorDetailsTextView.text = getString(R.string.sensor_not_configured) // Komunikat o braku sensora
        }
    }

    fun onBackPressed(view: View) {
        finish()  // Kończy tę aktywność i wraca do poprzedniej
    }

    override fun onStart() {
        super.onStart()

        // Rejestracja nasłuchiwaczy dla dostępnych sensorów
        sensorLight?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        sensorPressure?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onStop() {
        super.onStop()
        // Odrejestrowanie nasłuchiwacza, aby uniknąć niepotrzebnego zużycia baterii
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent != null) {
            val sensorType = sensorEvent.sensor.type
            val currentValue = sensorEvent.values[0]

            when (sensorType) {
                Sensor.TYPE_LIGHT -> {
                    sensorLightTextView.text = getString(R.string.light_sensor_label, currentValue)
                }
                Sensor.TYPE_PRESSURE -> {
                    sensorPressureTextView.text = getString(R.string.pressure_sensor_label, currentValue)
                }
                else -> {
                    // Obsługuje inne typy sensorów, jeśli są
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Możesz tu obsługiwać zmiany dokładności sensora, jeśli to konieczne
    }
}
