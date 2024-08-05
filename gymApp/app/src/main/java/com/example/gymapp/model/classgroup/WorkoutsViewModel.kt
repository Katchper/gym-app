package com.example.gymapp.model.classgroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gymapp.datasource.WorkoutsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The workouts view model class which acts as a layer of abstraction
 * to allow the user to interact with the repository from within composables much easier
 */

class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WorkoutsRepository = WorkoutsRepository(application)

    var workoutList: LiveData<List<Workout>> = repository.getAllWorkouts()
        private set

    // Delete item from the DB by the ID
    fun deleteFromID(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeWorkout(repository.getWorkoutByID(id))
        }
    }
    // Add an empty workout
    fun addEmptyEntry(){
        insertWorkout(Workout(0,"Monday","", "",0,""))
    }
    // Clear the DB
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearWorkouts()
        }
    }
    // Get an entry from the DB based on the parameter ID
    fun getEntryFromID(id: Int): Workout{
        var workout = Workout()
        viewModelScope.launch(Dispatchers.IO) {
            workout = repository.getWorkoutByID(id)
        }
        return workout
    }
    // Update a workout using the information from the parameter
    // get the existing workout using the ID, update its variables
    // send back into the DB
    fun updateWorkout(id: Int, newWorkout: Workout){
        viewModelScope.launch(Dispatchers.IO) {
            val workout = repository.getWorkoutByID(id)

            workout.let {
                it.day = newWorkout.day
                it.muscles = newWorkout.muscles
                it.exercises = newWorkout.exercises
                it.time = newWorkout.time
                it.imageids = newWorkout.imageids

                repository.updateWorkout(it)
            }
        }
    }


    // Get a workout based on the day parameter
    fun getWorkoutFromDay(day : String) : LiveData<List<Workout>>{
        var data : LiveData<List<Workout>> = MutableLiveData(emptyList())
        viewModelScope.launch(Dispatchers.IO){
           data = repository.getWorkoutFromDay(day)
        }
        return data
    }
    // Insert a workout into the DB
    fun insertWorkout(newWorkout: Workout) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(newWorkout)
        }
    }

}