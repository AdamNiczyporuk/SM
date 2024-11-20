package com.example.weatherproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherproject.adapter.WeatherAdapter
import com.example.weatherproject.api.RetrofitInstance
import com.example.weatherproject.model.WeatherResponse
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var temperatureTextView: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherRecyclerView: RecyclerView
    private val weatherData = mutableListOf<Pair<String, String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperatureTextView = findViewById(R.id.temperatureTextView)
        weatherIcon = findViewById(R.id.weatherIcon)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)

        weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        weatherRecyclerView.adapter = WeatherAdapter(weatherData)


        fetchWeatherData(52.2297, 21.0122)

    }
    private fun fetchWeatherData(lat: Double, lon: Double) {
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
                        weatherData.add("Odczuwalna" to "${weather.current.feels_like}°C")
                        weatherData.add("Ciśnienie" to "${weather.current.pressure} hPa")
                        weatherData.add("Wilgotność" to "${weather.current.humidity}%")
                        weatherData.add("Wiatr" to "${weather.current.wind_speed} m/s")
                        weatherData.add("Zachmurzenie" to "${weather.current.clouds}%")
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
}