package com.example.gymapp.datasource

import android.app.Application
import com.example.gymapp.model.classgroup.Exercise
import datasource.Injection

class ExercisesRepository(application: Application) {
    private val exercisesDao = Injection.getDatabase(application).exercisesDao()

    fun insert(exercise: Exercise){
        exercisesDao.insertOneExercise(exercise)
    }

    fun insertMultipleExercises(exercises: List<Exercise>){
        exercisesDao.insertMultipleExercises(exercises)
    }

    fun getAllExercises() = exercisesDao.getAllExercises()

    fun getExerciseByID(id : Int) = exercisesDao.getExerciseById(id)!!

    fun updateExercise(exercise: Exercise) = exercisesDao.updateExercise(exercise)

    fun removeExercise(exercise: Exercise) = exercisesDao.deleteExercise(exercise)

    fun clearWorkouts() = exercisesDao.deleteAll()
}