package com.example.weatherproject.model

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val wind_speed: Double,
    val clouds: Int,
    val sunrise: Int,
    val sunset: Int,
    val weather: List<WeatherDetails>
)

data class WeatherDetails(
    val icon: String
)

