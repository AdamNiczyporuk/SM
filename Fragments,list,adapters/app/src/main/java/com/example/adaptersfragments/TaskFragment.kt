package com.example.adaptersfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.adaptersfragments.model.Task
import java.util.Date

class TaskFragment : Fragment() {

    private lateinit var task: Task  // Tworzenie obiektu Task
    private lateinit var nameField: EditText
    private lateinit var dateButton: Button
    private lateinit var doneCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicjalizacja obiektu Task
        task = Task(name = "",  date = Date(System.currentTimeMillis()), done = false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate widok fragment_task.xml
        val view = inflater.inflate(R.layout.fragment_task, container, false)

        // Pobranie kontrolek z widoku
        nameField = view.findViewById(R.id.task_name)
        dateButton = view.findViewById(R.id.task_date)
        doneCheckBox = view.findViewById(R.id.task_done)

        // Obsługa pola tekstowego
        nameField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nie wymaga implementacji
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                task.name = s.toString() // Aktualizacja nazwy zadania
            }

            override fun afterTextChanged(s: Editable?) {
                // Nie wymaga implementacji
            }
        })

        // Ustawienie daty na przycisku
        dateButton.text = task.date.toString()
        dateButton.isEnabled = false // Przycisk jest nieaktywny

        // Obsługa pola CheckBox
        doneCheckBox.isChecked = task.done
        doneCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.done = isChecked // Aktualizacja statusu zadania
        }

        return view
    }
}
