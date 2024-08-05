package com.example.gymapp.datasource

import com.example.gymapp.model.ExercisesDao
import com.example.gymapp.model.WorkoutDao

interface RoomDatabaseI {
    fun exercisesDao(): ExercisesDao
    fun closeDb()
}

interface RoomDatabaseII {
    fun workoutsDao(): WorkoutDao
    fun closeDb()
}