package com.example.weatherproject

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherproject.adapter.WeatherAdapter
import com.example.weatherproject.api.RetrofitInstance
import com.example.weatherproject.model.WeatherResponse
import retrofit2.Callback
import retrofit2.Response
import android.Manifest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var temperatureTextView: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherRecyclerView: RecyclerView
    private val weatherData = mutableListOf<Pair<String, String>>()

    private lateinit var  locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperatureTextView = findViewById(R.id.temperatureTextView)
        weatherIcon = findViewById(R.id.weatherIcon)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)

        weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        weatherRecyclerView.adapter = WeatherAdapter(weatherData)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Sprawdzenie uprawnień i żądanie ich, jeśli nie są przyznane
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Jeśli uprawnienia nie są przyznane, żądaj ich
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        // Nasłuchiwanie zmiany lokalizacji
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,  // 1 sekunda
                0f,    // Minimalna zmiana odległości
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        val latitude = formatCoordinate(location.latitude)
                        val longitude = formatCoordinate(location.longitude)

                        // Przekazanie współrzędnych do API
                        fetchWeatherData(latitude, longitude)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
            // Obsługa wyjątku, jeśli użytkownik odmówił uprawnień
            temperatureTextView.text = "Brak uprawnień do lokalizacji"
        }
//        fetchWeatherData(52.2297, 21.0122)

    }
    private fun fetchWeatherData(lat: String, lon: String) {
        val apiKey = "8152758392d21870cd6fa24bd3c9bdd2"

        RetrofitInstance.api.getWeather(lat, lon, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: retrofit2.Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { weather ->
                        val temp = weather.current.temp
                        temperatureTextView.text = "Temperatura: ${temp}°C"

                        val iconCode = weather.current.weather.firstOrNull()?.icon ?: "01d"
                        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
                        Glide.with(this@MainActivity).load(iconUrl).into(weatherIcon)

                        weatherData.clear()
                        weatherData.add("Wshód Słońca" to "${convertUnixToReadableTime(weather.current.sunrise)}")
                        weatherData.add("Zachód Słońca" to "${convertUnixToReadableTime(weather.current.sunset)}")
                        weatherData.add("Wiatr" to "${weather.current.wind_speed} m/s")
                        weatherData.add("Zachmurzenie" to "${weather.current.clouds}%")
                        weatherData.add("Temperatura" to "${weather.current.temp}°C")
                        weatherData.add("Ciśnienie" to "${weather.current.pressure} hPa")
                        weatherData.add("Wilgotność" to "${weather.current.humidity}%")
                        weatherData.add("Odczuwalna" to "${weather.current.feels_like}°C")
                        weatherRecyclerView.adapter?.notifyDataSetChanged()
                    }
                } else {
                    temperatureTextView.text = "Błąd: ${response.code()}"
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherResponse>, t: Throwable) {
                temperatureTextView.text = "Błąd pobierania danych: ${t.message}"
            }
        })

    }
    private fun formatCoordinate(coordinate: Double): String {
        // Zamiana przecinka na kropkę
        return coordinate.toString().replace(',', '.')
    }

    fun convertUnixToReadableTime(unixTime: Int): String {
        // Konwertowanie czasu Unix na milisekundy
        val date = Date(unixTime.toLong() * 1000) // mnożymy przez 1000, bo Unix Time jest w sekundach, a Date oczekuje milisekund

        // Tworzenie formatu czasu (godziny i minuty)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Zwracanie czasu w formacie "HH:mm"
        return format.format(date)
    }
}