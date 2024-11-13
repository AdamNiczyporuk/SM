package com.example.sensorapp

import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sensorapp.R
import android.Manifest
import android.widget.Toast

class LocationActivity : AppCompatActivity() {

    private val REQUEST_LOCATION_PERMISSION  =1
    private val TAG = "LocationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location) // Set the layout for this activity

        val getLocationButton : Button = findViewById(R.id.getLocationButton)
        getLocationButton.setOnClickListener{getLocation()}

    }
    private fun getLocation()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            Log.d(TAG, "getLocation: permissions granted")
            // Implement location retrieval logic here
        }
    }

    fun onBackPressed(view: View) {
        finish()  // Kończy tę aktywność i wraca do poprzedniej
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()  // Call your method to get the location
                } else {
                    Toast.makeText(
                        this,
                        R.string.location_permission_denied,  // You should define this string in your strings.xml
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
