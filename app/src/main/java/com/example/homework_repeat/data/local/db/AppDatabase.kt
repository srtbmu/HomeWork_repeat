package com.example.homework_repeat.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework_repeat.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
