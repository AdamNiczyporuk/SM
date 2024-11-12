import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sensorapp.R

class SensorDetailsActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensorLight: Sensor? = null
    private lateinit var sensorLightTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_details)

        // Znalezienie widoku
        sensorLightTextView = findViewById(R.id.sensor_light_label)

        // Pobranie SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Pobranie sensora na podstawie przekazanego typu
        val sensorType = intent.getIntExtra("SENSOR_TYPE", -1)
        if (sensorType != -1) {
            sensorLight = sensorManager.getDefaultSensor(sensorType)

            if (sensorLight != null) {
                sensorLightTextView.text = getString(R.string.light_sensor_available)
            } else {
                sensorLightTextView.text = getString(R.string.missing_sensor)
            }
        } else {
            sensorLightTextView.text = getString(R.string.missing_sensor)
        }
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

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_LIGHT) {
            // Pobranie wartości zmierzonej przez sensor światła
            val lightValue = event.values[0]
            // Wyświetlenie wartości w odpowiednim formacie
            sensorLightTextView.text = getString(R.string.light_sensor_label, lightValue)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Możesz tu obsługiwać zmiany dokładności sensora, jeśli to konieczne
    }

}
