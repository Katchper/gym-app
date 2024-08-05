package com.example.gymapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gymapp.model.classgroup.Exercise

/**
 * The exercises DAO handler
 * Contains the SQL queries for the exercises database
 */

@Dao
interface ExercisesDao {
    @Insert
    fun insertOneExercise(exercise : Exercise)

    @Insert
    fun insertMultipleExercises(exercises: List<Exercise>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateExercise(exercise : Exercise)

    @Delete
    fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercises")
    fun deleteAll()

    @Query("SELECT DISTINCT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("""SELECT DISTINCT * FROM exercises WHERE id = :idValue""")
    fun getExerciseById(idValue: Int) : Exercise?
}