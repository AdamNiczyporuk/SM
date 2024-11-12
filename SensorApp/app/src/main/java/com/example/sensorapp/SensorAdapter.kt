package com.example.sensorapp

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorapp.R

class SensorAdapter(private val sensorList: List<Sensor>) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sensor_list_item, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensorList[position]
        holder.bind(sensor)
    }

    override fun getItemCount(): Int = sensorList.size

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sensorIcon: ImageView = itemView.findViewById(R.id.sensor_icon)
        private val sensorName: TextView = itemView.findViewById(R.id.sensor_name)

        fun bind(sensor: Sensor) {
            sensorIcon.setImageResource(R.drawable.ic_sensor)  // Upewnij się, że masz ikonę `ic_sensor`
            sensorName.text = sensor.name
        }
    }
}
