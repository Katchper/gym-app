package com.example.gymapp.datasource

import android.app.Application
import com.example.gymapp.model.classgroup.Workout
import datasource.WorkoutInjection

class WorkoutsRepository(application: Application) {
    private val workoutDao = WorkoutInjection.getDatabase(application).workoutsDao()

    fun insert(workout: Workout){
        workoutDao.insertOneWorkout(workout)
    }

    fun insertMultipleExercises(workouts: List<Workout>){
        workoutDao.insertMultipleWorkouts(workouts)
    }

    fun getAllWorkouts() = workoutDao.getAllWorkouts()

    fun getWorkoutFromDay(day : String) = workoutDao.getWorkoutByDay(day)

    fun getWorkoutByID(id : Int) = workoutDao.getWorkoutById(id)!!

    fun updateWorkout(workout: Workout) = workoutDao.updateWorkout(workout)

    fun removeWorkout(workout: Workout) = workoutDao.deleteWorkout(workout)

    fun clearWorkouts() = workoutDao.deleteAll()
}