import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptersfragments.MainActivity
import com.example.adaptersfragments.R
import com.example.adaptersfragments.model.Task

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val nameTextView: TextView = view.findViewById(R.id.task_item_name)
        private val dateTextView: TextView = view.findViewById(R.id.task_item_date)
        private lateinit var task: Task

        init {
            view.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            nameTextView.text = task.name
            dateTextView.text = task.date.toString()
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, MainActivity::class.java)
            intent.putExtra("KEY_EXTRA_TASK_ID", task.id)
            itemView.context.startActivity(intent)
        }
    }
}
