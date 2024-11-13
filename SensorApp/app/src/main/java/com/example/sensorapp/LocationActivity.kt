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
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Location  // Correct import for the location class
import android.text.TextUtils
import java.io.IOException
import java.util.Locale
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

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
        val getAddressButton : Button = findViewById(R.id.getLocationAddress)
        getAddressButton.setOnClickListener{ executeGeocoding() }
    }
    private fun locationGeocoding(context: Context, location: Location): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var resultMessage = ""
        var addresses: List<Address>? = null

        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (ioException: IOException) {
            resultMessage = context.getString(R.string.service_not_available)
            Log.e(TAG, resultMessage, ioException)
        }

        if (addresses == null || addresses.isEmpty()) {
            if (resultMessage.isEmpty()) {
                resultMessage = context.getString(R.string.no_address_found)
                Log.e(TAG, resultMessage)
            }
        } else {
            val address = addresses[0]
            val addressParts = mutableListOf<String>()

            for (i in 0..address.maxAddressLineIndex) {
                addressParts.add(address.getAddressLine(i))
            }

            resultMessage = TextUtils.join("\n", addressParts)
        }

        return resultMessage
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
    private fun executeGeocoding() {
        if (lastLocation != null) {
            // Create an ExecutorService to run the geocoding operation on a background thread
            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            // Submit the geocoding task and get a Future object
            val returnedAddress: Future<String> = executor.submit<String> {
                locationGeocoding(applicationContext, lastLocation!!)
            }

            try {
                // Get the result of the geocoding operation
                val result = returnedAddress.get()

                // Update the TextView with the geocoded address and current time
                addressTexView.text = getString(R.string.address_text, result, System.currentTimeMillis())
            } catch (e: ExecutionException) {
                Log.e(TAG, "ExecutionException: ${e.message}", e)
            } catch (e: InterruptedException) {
                Log.e(TAG, "InterruptedException: ${e.message}", e)
                Thread.currentThread().interrupt()  // Ensure thread interruption is handled
            }
        } else {
            // Show a message that location is not available
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
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
