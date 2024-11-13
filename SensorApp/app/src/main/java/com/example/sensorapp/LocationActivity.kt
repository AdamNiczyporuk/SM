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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Location  // Correct import for the location class

class LocationActivity : AppCompatActivity() {

    private var lastLocation: Location? = null  // Correct type here
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView
    private lateinit var addressTexView: TextView
    private val REQUEST_LOCATION_PERMISSION = 1
    private val TAG = "LocationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location) // Set the layout for this activity

        locationTextView = findViewById(R.id.textview_location)
        addressTexView = findViewById(R.id.textview_address)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val getLocationButton: Button = findViewById(R.id.getLocationButton)
        getLocationButton.setOnClickListener { getLocation() }
    }

    private fun getLocation() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        lastLocation = it
                        locationTextView.text = getString(
                            R.string.location_text,
                            it.latitude,
                            it.longitude,
                            it.time
                        )
                    } ?: run {
                        locationTextView.setText(R.string.no_location)
                    }
                }
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
        }
    }

    fun onBackPressed(view: View) {
        finish()  // Finish this activity and return to the previous one
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
