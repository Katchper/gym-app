package com.example.gymapp.model.classgroup
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.ceil


/**
 * The Workout data class used within the workouts DB
 * holds all the necessary information for one workout
 * id - generates, day, muscles (List), imageids (List), time, exercises (List)
 * The objects i described as lists are converted into strings before
 * being put into the database
 */

@Entity(tableName = "workouts")
data class Workout (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var day : String = "",
    var muscles : String = "",
    var imageids : String = "",
    var time: Int = 0,
    var exercises: String = ""
) {

    /**
     * timeCalc is the calculation function to workout the time
     * as a integer to be later multiplied by 5 (5 is more approximate)
     * It takes in the whole list of exercises and finds the ones
     * with the same id as the ones within this workout object.
     *
     * Then add up seconds based on the sets | dropsets
     * divide by 60 then divide by 5 to get intervals of 5 minutes (to be approximate)
     */
    // assuming the set takes a minute to complete and a rest period of 2 minutes for regular sets
    // drop sets are 2 minutes for the set + 30 for total time between subsets + 180 for rest
    fun timeCalc(exerciseList: List<Exercise>) {
        var theseExercises = this.exercisesToList()
        var newList : MutableList<Exercise> = mutableListOf()
        for (id in theseExercises){
            for (exercise in exerciseList){
                if (exercise.id == id){
                    newList.add(Exercise(sets = exercise.sets, dropset = exercise.dropset))
                    break
                }
            }
        }

        var exerciseTime = 0f
        for (exercise in newList) {
            if (exercise.dropset){
                exerciseTime += ((120 + 30 + 180) * exercise.sets)
            } else {
                exerciseTime += (exercise.sets * 180)
            }
        }

        exerciseTime = (exerciseTime / 60) / 5
        exerciseTime = ceil(exerciseTime)
        val finalTime: Int = exerciseTime.toInt()
        this.time = finalTime
    }

    /**
     * Returns a list of exercises associated with this
     * workout object based on the id's within this exercises variable
     * it takes in the list of all exercises to execute this method
     */
    fun getAssociatedExercises(exerciseList: List<Exercise>): List<Exercise> {
        var exercisesToday: MutableList<Exercise> = mutableListOf()
        val exersiseTemp = exercisesToList()
        for(exerciseID in exersiseTemp) {
            for (exercise in exerciseList) {
                if (exercise.id == exerciseID) {
                    exercisesToday.add(exercise)
                    break
                }
            }
        }
        return exercisesToday.toList()
    }

    /**
     * Convert the list of exercise ID's to one string separated by commas
     */
    fun exercisesToString(exerciseList: List<Int>){
        exercises = exerciseList.joinToString(",")
    }
    /**
     * Convert the list of exercise image ID's to one string separated by commas
     */
    fun imagesToString(imageList: List<Int>){
        imageids = imageList.joinToString(",")
    }
    /**
     * Convert the list of muscle names to one string separated by commas
     */
    fun musclesToString(muscleList: List<String>){
        muscles = muscleList.joinToString(",")
    }

    /**
     * Convert the exercise Id string back into a list of integer ID's
     */
    fun exercisesToList() : List<Int>{
        var exerciseList = emptyList<Int>()
        if (!exercises.isEmpty()){
            exerciseList = exercises.split(',').map { it.toInt() }
        }
        return exerciseList
    }

    /**
     * Convert the string of muscle names back into a list of muscle names
     */
    fun muscleToList() : List<String>{
        var muscleList = emptyList<String>()
        if (!muscles.isEmpty()){
            muscleList = muscles.split(',').map { it }
        }
        return muscleList
    }

    /**
     * Convert the string of image ID's back into a list of integer ID's
     */
    fun imageToList() : List<Int>{
        var imageList = emptyList<Int>()
        if (!imageids.isEmpty()){
            imageList = imageids.split(',').map { it.toInt() }
        }
        return imageList
    }

}