package com.example.gymapp.model.classgroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gymapp.R
import com.example.gymapp.datasource.ExercisesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The exercises view model class which is used as a layer of abstraction
 * to handle the information within the database in composables with this as the origin
 */

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExercisesRepository = ExercisesRepository(application)

    var exercisesList: LiveData<List<Exercise>> = repository.getAllExercises()
        private set

    // get a specific exercise using the ID
    fun getEntryFromID(id: Int): Exercise{
        var exercise = Exercise()
        viewModelScope.launch(Dispatchers.IO) {
            exercise = repository.getExerciseByID(id)
        }
        return exercise
    }
    // Clear the DB
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearWorkouts()
        }
    }
    // add an empty exercise to the DB
    fun addEmptyEntry(){
        insertExercise(Exercise(0, R.drawable.icons8_dumbbell, "exercise", 0, 0,false, 0, 0,0 ))
    }
    // delete an exercise from the DB by using an ID
    fun deleteFromID(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeExercise(repository.getExerciseByID(id))
        }
    }
    // update an exercise by getting the original exercise, changing the
    // information and updating the repository

    fun updateExercise(id: Int, newExercice: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            val exercise = repository.getExerciseByID(id)

            exercise.let {
                it.imageId = newExercice.imageId
                it.name = newExercice.name
                it.sets = newExercice.sets
                it.reps = newExercice.reps
                it.dropset = newExercice.dropset
                it.weight1 = newExercice.weight1
                it.weight2 = newExercice.weight2
                it.weight3 = newExercice.weight3
                repository.updateExercise(it)
            }
        }
    }
    // insert an exercise into the DB
    fun insertExercise(newExercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(newExercise)
        }
    }
}