package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gymapp.model.classgroup.Workout

/**
 * Timetable view top composable, it contains the composables contained within the
 * table top like the day, muscles and muscle icons
 */
@Composable
fun TimeTableViewTop(modifier: Modifier = Modifier, workout: Workout){
    Box(modifier = modifier
        .padding(5.dp)
        .padding(top = 20.dp)
        .fillMaxWidth()
        .shadow(
            elevation = 15.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)){

        Column (modifier = modifier) {
            //ROW WHICH CONTAINS THE TITLE
            Row {
                TableTitle(
                    content = workout.day,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

            }
            // SECOND ROW WHICH CONTAINS THE MUSCLE GROUPS
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                // MUSCLE GROUPS TEXT
                var muscleString = ""
                for (muscle in workout.muscleToList()) {
                    muscleString += "$muscle & "
                }
                muscleString = muscleString.dropLast(3)
                Spacer(modifier = Modifier.width(20.dp))
                BoldTableText(
                    content = muscleString,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .weight(2.5f)
                )
                // MUSCLE GROUPS ICONS
                Row(modifier = Modifier.weight(1f)) {
                    for (id in workout.imageToList()) {
                        CustomImage(
                            content = "", id = id, modifier = Modifier
                                .size(40.dp)
                                .weight(1f),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable which contains everything to create one table row
 * Contains the information about the exercise
 *
 * If visual is set to false it contains a remove button at the end of the row
 * for deleteing exercises
 */
@Composable
fun TimetableViewRow(exerciseName: String, icon: Int,
                     weights: List<Int>, sets: String, reps: String,
                     isDropSet: Boolean, isVisual: Boolean, color1 : Color,
                     onClick: () -> Unit,
                     containerColor : Color){

    Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .shadow(elevation = 0.dp, shape = RoundedCornerShape(14.dp))
                .background(containerColor)
        ){
        // the icon of the exercise
            Spacer(modifier = Modifier.width(10.dp))
            CustomImage(content = exerciseName, id = icon, modifier = Modifier
                .size(40.dp), color = color1)
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)){
                // row which contains the title
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    if (isDropSet){
                        BoldTableText(content = "Drop Set", color = color1)
                    }
                    BoldTableText(content = exerciseName, color = color1)

                }
                // row which contains the reps and sets and weight
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    // if the weight is 0 then display N/A
                    if (!isDropSet){
                        if (weights[0] <= 0){
                            TableText(content = "N/A", color = color1)
                        } else {
                            TableText(content = weights[0].toString() + "kg", color = color1)
                        }
                    }
                    TableText(content = "$sets Sets", color = color1)
                    TableText(content = "$reps Reps", color = color1)
                }
                // row which exists if there is a drop set, moving the weights onto
                // this row
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    if (isDropSet){
                        if (weights[0] <= 0){
                            TableText(content = "N/A", color = color1)
                        } else {
                            TableText(content = weights[0].toString() + "kg", color = color1)
                        }
                        if (weights[1] <= 0){
                            TableText(content = "N/A", color = color1)
                        } else {
                            TableText(content = weights[1].toString() + "kg", color = color1)
                        }
                        if (weights[2] <= 0){
                            TableText(content = "N/A", color = color1)
                        } else {
                            TableText(content = weights[2].toString() + "kg", color = color1)
                        }
                    }
                }
            }
        // delete button check
            if (!isVisual){
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .height(50.dp)
                        .width(50.dp),
                    onClick = onClick
                ) {
                    Icon(
                        Icons.Filled.Close, "close",
                        modifier = Modifier.fillMaxHeight(),
                        tint = color1)

                }
            }
        }

}


