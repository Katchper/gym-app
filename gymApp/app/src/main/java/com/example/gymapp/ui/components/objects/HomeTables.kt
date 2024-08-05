package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.R
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * The home menu "Today" table, contains
 * the workout description, the exercises for the day
 * and a checkbox for every workout which is used in the
 * progress calculation for the progress bar
 */
@Composable
fun CustomTable(modifier: Modifier = Modifier, workout: Workout, exerciseList: List<Exercise>, progressValue: (Float) -> Unit){
    var checkedItems by rememberSaveable { mutableStateOf(emptyList<Int>()) }
    Box(modifier = modifier
        .padding(5.dp)
        .padding(top = 20.dp)
        .fillMaxWidth()
        .shadow(
            elevation = 15.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.primaryContainer)){

        Column {

            //ROW WHICH CONTAINS THE TITLE
            Row {
                TableTitle(content = "Today's Workout",
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                // APPROXIMATE WORKOUT TIME
                if (exerciseList.isNotEmpty()){
                    TimeText(content = "~"+(workout.time*5).toString()+" min",
                        color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            // SECOND ROW WHICH CONTAINS THE MUSCLE GROUPS
            Row (modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.Center){
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

            // the exercise list if empty
            if (exerciseList.isEmpty()){
                CustomDivider(color = Color.Black, modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(bottom = 4.dp))
                Row {
                    Spacer(modifier = modifier.weight(1f))
                    BoldTableText(content = "You have no exercises today",
                        color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = modifier.weight(1f))
                }
            // if its not empty
            } else {
                var doneCount = 0
                for(currentExercise in exerciseList){
                    doneCount += 1
                    Divider(thickness = 1.dp, color = Color.Black,modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(bottom = 4.dp))
                    TableRow(
                        exerciseName = currentExercise.name,
                        icon = currentExercise.imageId,
                        weights = listOf(currentExercise.weight1, currentExercise.weight2, currentExercise.weight3),
                        sets = currentExercise.sets.toString(),
                        reps = currentExercise.reps.toString(),
                        isDropSet = currentExercise.dropset,
                        isVisual = false,
                        color1 = MaterialTheme.colorScheme.onPrimaryContainer,
                        id = doneCount,
                        counterValue = { boxid, selected ->
                            // Handle the updated counter value here
                            checkedItems = if (selected) {
                                checkedItems + boxid
                            } else {
                                checkedItems - boxid
                            }
                            // You can perform any other actions based on the updated counter value
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    // calculate the progress bar value based on total checkboxes and the ones checked
    val temp1 = checkedItems.size.toFloat()
    val temp2 = exerciseList.size.toFloat()
    if (exerciseList.size != 0 && checkedItems.size != 0){
        progressValue(temp1/temp2)
    } else {
        progressValue(0f)
    }
}

/**
 * Tommorows table which contains the same information as the Todays one
 * but it is collapsed and a button needs to be pressed to expand the table
 */
@Composable
fun TommorowTable(modifier: Modifier = Modifier, workout: Workout, exerciseList: List<Exercise>){
    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = modifier
        .padding(5.dp)
        .padding(top = 20.dp)
        .fillMaxWidth()
        .shadow(
            elevation = 15.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)){

        Column (modifier = modifier){
            //ROW WHICH CONTAINS THE TITLE
            Row {
                TableTitle(content = "Tomorrow's Workout",
                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                // APPROXIMATE WORKOUT TIME
                if (exerciseList.isNotEmpty()){
                    TimeText(content = "~"+(workout.time*5).toString()+" min",
                        color = MaterialTheme.colorScheme.onSecondaryContainer)
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
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = modifier
                        .align(Alignment.CenterVertically)
                        .weight(2.5f))
                // MUSCLE GROUPS ICONS
                Row(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()){
                    for (id in workout.imageToList()){
                        CustomImage(content = "", id = id, modifier = Modifier
                            .size(40.dp)
                            .weight(1f),
                                color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }


            }
            if (exerciseList.isEmpty()){
                CustomDivider(color = Color.Black, modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(bottom = 4.dp))
                Row {
                    Spacer(modifier = modifier.weight(1f))
                    BoldTableText(content = "You have no exercises tomorrow",
                        color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))

            } else {
                if (isExpanded){
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
                            color1 = MaterialTheme.colorScheme.onSecondaryContainer,
                            id = 0,
                            counterValue = {boxid, selected ->}
                        )
                    }
                }
                // EXPANSION ROW WITH BUTTON
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp)){
                    //DROPDOWN BUTTON
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {isExpanded = !isExpanded}
                    ) {
                        if (!isExpanded){
                            Icon(
                                Icons.Filled.KeyboardArrowDown, "dropDown",
                                modifier = Modifier.fillMaxSize(1F),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        } else {
                            Icon(
                                Icons.Filled.KeyboardArrowUp, "dropUp",
                                modifier = Modifier.fillMaxSize(1F),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                }
            }
            // the exercise list

        }
    }
}

/**
 * The table row composable, takes in all the exercise information as parameters
 * the isVisual is for the checkbox
 * it returns the id and the checkbox state when it is changed
 */
@Composable
fun TableRow(exerciseName: String, icon: Int,
             id: Int,
             weights: List<Int>, sets: String, reps: String,
             isDropSet: Boolean, isVisual: Boolean, color1 : Color,
             counterValue: (Int, Boolean) -> Unit) {
    var checked by rememberSaveable { mutableStateOf(false) }
    var rowColor: Color
    // if this row is checked, grey it out slightly to show
    if (checked){
        rowColor = Color.Gray.copy(alpha = 0.5f)
    } else {
        rowColor = Color.Transparent
    }

    Box(modifier = Modifier
        .fillMaxWidth()){

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.background(rowColor)
        ){
            // check box
            if (!isVisual){
                CustomCheckBox(id = id, progressValue = { boxid, isChecked ->
                    counterValue(boxid, isChecked)
                    checked = isChecked
                })
            }
            Spacer(modifier = Modifier.width(10.dp))
            CustomImage(content = exerciseName, id = icon, modifier = Modifier
                .size(40.dp), color = color1)
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()){
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    if (isDropSet){
                        BoldTableText(content = "Drop Set", color = color1)
                    }
                    BoldTableText(content = exerciseName, color = color1)

                }
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TablePreview() {
    GymAppTheme(dynamicColor = false) {
        val exercise1 = Exercise(0, R.drawable.icons8_curls, "Bicep Curl", 3, 12,false,23,0,0)
        val exercise2 = Exercise(0, R.drawable.icons8_squats, "Test2", 4, 10,false,23,0,0)
        val exercise3 = Exercise(0, R.drawable.icons8_dumbbell, "Test1", 47, 10,true,23,2,1)
        val exercises = listOf(exercise1, exercise2, exercise3)
        val workouts = Workout(0,"Monday", "",
            "", 5, "")
        workouts.imagesToString(listOf(R.drawable.icons8_dumbbell, R.drawable.icons8_curls,R.drawable.icons8_squats))
        workouts.exercisesToString(listOf())
        workouts.musclesToString(listOf("Testeing", "wfwwfjowjoi", "wfwfg"))

        Column {
            CustomTable(workout = workouts, exerciseList = exercises, progressValue = {})
            TommorowTable(workout = workouts, exerciseList = exercises)
        }
    }
}