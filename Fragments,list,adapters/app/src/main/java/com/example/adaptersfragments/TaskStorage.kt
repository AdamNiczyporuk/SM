package com.example.adaptersfragments

import com.example.adaptersfragments.model.Task
import java.util.*

class TaskStorage private constructor() {

    // Lista zadań
    private val tasks: MutableList<Task> = mutableListOf()

    // Statyczna zmienna przechowująca instancję klasy Singleton
    companion object {
        // Jedyna instancja klasy
        val instance: TaskStorage by lazy { TaskStorage() }
    }

    // Metoda zwracająca całą listę zadań
    fun getAllTasks(): List<Task> {
        return tasks
    }

    // Metoda zwracająca zadanie na podstawie identyfikatora
    fun getTaskById(id: UUID): Task? {
        return tasks.find { it.id == id }
    }

    // Konstruktor prywatny, który generuje przykładowe zadania
    init {
        // Tworzymy przykładowe zadania
        for (i in 1..150) {
            val task = Task(
                name = "Pilne zadanie numer $i",
                done = i % 3 == 0  // Ustawiamy, że co trzecie zadanie jest wykonane
            )
            tasks.add(task)
        }
    }
}
