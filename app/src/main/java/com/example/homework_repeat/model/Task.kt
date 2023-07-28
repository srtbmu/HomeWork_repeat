package com.example.homework_repeat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String? = null,
    val desc: String? = null
) : java.io.Serializable