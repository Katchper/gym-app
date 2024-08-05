package com.example.gymapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gymapp.model.classgroup.Workout

/**
 * The Workout DB DAO handler
 * Contains all the SQL queries for the workout db
 */

@Dao
interface WorkoutDao {
    @Insert
    fun insertOneWorkout(workout : Workout)

    @Insert
    fun insertMultipleWorkouts(workout: List<Workout>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWorkout(workout : Workout)

    @Delete
    fun deleteWorkout(workout: Workout)

    @Query("DELETE FROM workouts")
    fun deleteAll()
    // sort the days manually from monday to sunday
    @Query("SELECT * FROM workouts ORDER BY CASE\n" +
            " WHEN day = 'Monday' THEN 1\n" +
            " WHEN day = 'Tuesday' THEN 2\n" +
            " WHEN day = 'Wednesday' THEN 3\n" +
            " WHEN day = 'Thursday' THEN 4\n" +
            " WHEN day = 'Friday' THEN 5\n" +
            " WHEN day = 'Saturday' THEN 6\n" +
            " WHEN day = 'Sunday' THEN 7\n" +
            " END ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("""SELECT DISTINCT * FROM workouts WHERE id = :idValue""")
    fun getWorkoutById(idValue: Int) : Workout?

    @Query("""SELECT DISTINCT * FROM workouts WHERE day = :day""")
    fun getWorkoutByDay(day: String) : LiveData<List<Workout>>
}