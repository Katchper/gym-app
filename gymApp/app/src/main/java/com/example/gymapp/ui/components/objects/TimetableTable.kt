package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.Workout

/**
 * The timetable table composable
 * used for simply displaying the timetable without anything else
 *
 * Similar to the other tables
 */
@Composable
fun TimetableTable(modifier: Modifier = Modifier, workout: Workout, exerciseList: List<Exercise>){
    Box(modifier = modifier
        .padding(5.dp)
        .padding(top = 20.dp)
        .fillMaxWidth()
        .shadow(
            elevation = 15.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.primaryContainer)){

        Column (modifier = modifier){
            //ROW WHICH CONTAINS THE TITLE
            Row {
                TableTitle(content = workout.day,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                // APPROXIMATE WORKOUT TIME
                if (workout.exercises.isNotEmpty()) {
                    TimeText(
                        content = "~" + (workout.time * 5).toString() + " min",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            // SECOND ROW WHICH CONTAINS THE MUSCLE GROUPS
            Row (horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)){
                // MUSCLE GROUPS TEXT
                var muscleString = ""
                for (muscle in workout.muscleToList()) {
                    muscleString += "$muscle & "
                }
                muscleString = muscleString.dropLast(3)
                Spacer(modifier = Modifier.width(20.dp))
                BoldTableText(content = muscleString,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .weight(2.5f))
                // MUSCLE GROUPS ICONS
                Row(modifier = Modifier.weight(1f)){
                    for (id in workout.imageToList()){
                        CustomImage(content = "", id = id, modifier = Modifier
                            .size(40.dp)
                            .weight(1f),
                            color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }

            // the exercise list
            if (exerciseList.isEmpty()){
                CustomDivider(color = Color.Black, modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(bottom = 4.dp))
                Row {
                    Spacer(modifier = modifier.weight(1f))
                    BoldTableText(content = "You have no exercises this day",
                        color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
            } else {
                for(currentExercise in exerciseList){
                    CustomDivider(color = Color.Black, modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(bottom = 4.dp))
                    TableRow(
                        exerciseName = currentExercise.name,
                        icon = currentExercise.imageId,
                        weights = listOf(currentExercise.weight1,currentExercise.weight2,currentExercise.weight3),
                        sets = currentExercise.sets.toString(),
                        reps = currentExercise.reps.toString(),
                        isDropSet = currentExercise.dropset,
                        isVisual = true,
                        color1 = MaterialTheme.colorScheme.onPrimaryContainer,
                        id = 0,
                        counterValue = {boxid, selected ->}
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
    }
}