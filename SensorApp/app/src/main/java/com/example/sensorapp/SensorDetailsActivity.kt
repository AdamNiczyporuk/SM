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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_details)

        // Znalezienie widoku
        sensorLightTextView = findViewById(R.id.sensor_light_label)
        sensorPressureTextView = findViewById(R.id.sensor_pressure_label)

        // Pobranie SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val sensorType = intent.getIntExtra("SENSOR_TYPE", -1)

        if (sensorType != -1) {
            if (sensorLight != null) {
                sensorLightTextView.text = getString(R.string.light_sensor_available)
            } else {
                sensorLightTextView.text = getString(R.string.missing_sensor)
            }

            if (sensorPressure != null) {
                sensorPressureTextView.text = getString(R.string.pressure_sensor_available)
            } else {
                sensorPressureTextView.text = getString(R.string.missing_sensor)
            }
        } else {
            sensorLightTextView.text = getString(R.string.missing_sensor)
        }
    }
    fun onBackPressed(view: View) {
        finish()  // Kończy tę aktywność i wraca do poprzedniej
    }


    override fun onStart() {
        super.onStart()
        if (sensorLight != null) {
            // Rejestracja nasłuchiwacza, jeśli sensor jest dostępny
            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
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
                Sensor.TYPE_PRESSURE ->
                {
                    sensorPressureTextView.text = getString(R.string.pressure_sensor_label,currentValue)
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
