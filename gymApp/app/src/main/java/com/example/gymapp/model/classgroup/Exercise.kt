package com.example.gymapp.model.classgroup

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gymapp.R

/**
 * The exercises data class used within the exercises DB
 * holds all the necessary information for one exercise
 * the id - generated, imageID, name, sets, reps, dropset
 * weight1, weight2, weight3
 * weight 1 is for regular exercises & 2 + 3 are for dropsets
 */

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var imageId : Int = R.drawable.icons8_dumbbell,
    var name : String = "",
    var sets : Int = 0,
    var reps : Int = 0,
    var dropset : Boolean = false,
    var weight1 : Int = 0,
    var weight2 : Int = 0,
    var weight3 : Int = 0,
)
