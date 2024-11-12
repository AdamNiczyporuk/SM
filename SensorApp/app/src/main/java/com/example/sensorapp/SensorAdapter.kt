package com.example.sensorapp
import SensorDetailsActivity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorapp.R

class SensorAdapter(
    private val sensorList: List<Sensor>,
    private val context: Context  // Przekazanie kontekstu do adaptera
) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sensor_list_item, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensorList[position]
        holder.bind(sensor)

        // Obsługa kliknięcia na czujnik
        holder.itemView.setOnClickListener {
            // Tworzenie intencji do przejścia do szczegółów sensora
            val intent = Intent(context, SensorDetailsActivity::class.java)
            // Przekazanie nazwy sensora i typu sensora jako dodatkowych danych
            intent.putExtra("SENSOR_NAME", sensor.name)
            intent.putExtra("SENSOR_TYPE", sensor.type)  // Dodajemy typ sensora
            context.startActivity(intent)
        }
    }



    override fun getItemCount() = sensorList.size

    inner class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sensorIcon: ImageView = itemView.findViewById(R.id.sensor_icon)
        private val sensorName: TextView = itemView.findViewById(R.id.sensor_name)

        fun bind(sensor: Sensor) {
            sensorIcon.setImageResource(R.drawable.ic_sensor)  // Dodaj swoją ikonę tutaj
            sensorName.text = sensor.name

            // Ustawienie długiego kliknięcia
            itemView.setOnLongClickListener {
                showSensorDetailsDialog(sensor)
                true
            }
        }

        private fun showSensorDetailsDialog(sensor: Sensor) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Szczegóły czujnika")
            builder.setMessage(
                "Producent: ${sensor.vendor}\n" +
                        "Maksymalna wartość: ${sensor.maximumRange}"
            )
            builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }

    }
}
