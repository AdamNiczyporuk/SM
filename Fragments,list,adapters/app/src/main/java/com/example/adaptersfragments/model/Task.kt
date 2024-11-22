package com.example.adaptersfragments.model

import java.util.*

data class Task(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var done: Boolean = false,
    val date: Date = Date()
)
