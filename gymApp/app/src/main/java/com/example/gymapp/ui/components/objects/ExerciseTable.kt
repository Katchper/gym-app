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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.R
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * The exercise table composable, similar to the other tables but has different content
 * Has a title and displays the entire list of exercises
 */
@Composable
fun ExerciseTable(navController: NavHostController, modifier: Modifier = Modifier, exerciseList: List<Exercise>){
    var editMode by remember { mutableStateOf(false) }
    Box(modifier = modifier
        .padding(5.dp)
        .padding(top = 20.dp)
        .fillMaxWidth()
        .shadow(
            elevation = 15.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)){

        Column {
            //ROW WHICH CONTAINS THE TITLE
            Row (modifier = Modifier.fillMaxWidth()){
                TableTitle(content = "Exercise List",
                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                    if (exerciseList.isNotEmpty()){
                        Spacer(modifier = Modifier.weight(1f))
                        if (!editMode){
                            // custom edit button, when pressed enables edit mode
                            EditButton(buttonColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                iconColor = MaterialTheme.colorScheme.onSurface,
                                content = "Edit Button",
                                onClick = {editMode = !editMode},
                                id = Icons.Filled.Edit,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(50.dp))
                        } else {
                            // when edit mode is on the close button displays in place
                            // of the edit button
                            CloseButton(iconColor = MaterialTheme.colorScheme.onBackground,
                                onClick = {editMode = !editMode},
                                content = "Close button",
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(50.dp))
                        }
                    }
            }

            // the exercise list if empty
            if (exerciseList.isEmpty()){
                CustomDivider(color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(bottom = 4.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    BoldTableText(content = stringResource(R.string.no_exercises_listed), color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = Modifier.weight(1f))
                }

            // the exercise table rows
            } else {
                for(currentExercise in exerciseList){
                    CustomDivider(color = MaterialTheme.colorScheme.onSecondaryContainer,modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(bottom = 4.dp))
                    ExercisesTableRow(
                        rowID = currentExercise.id,
                        navController = navController,
                        exerciseName = currentExercise.name,
                        icon = currentExercise.imageId,
                        weights = listOf(currentExercise.weight1,currentExercise.weight2,currentExercise.weight3),
                        sets = currentExercise.sets.toString(),
                        reps = currentExercise.reps.toString(),
                        isDropSet = currentExercise.dropset,
                        editMode = editMode,
                        color1 = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

/**
 * The custom exercises table row which contains an edit button at the end which turns
 * on when edit mode is true
 * Takes in nav controller as it will navigate to the next scene when the button is pressed
 */
@Composable
fun ExercisesTableRow(rowID: Int, navController: NavHostController, exerciseName: String, icon: Int, weights: List<Int>, sets: String, reps: String, isDropSet: Boolean, color1 : Color, editMode: Boolean) {
    // regular exercise table row
    Box(modifier = Modifier
        .fillMaxWidth()){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.width(20.dp))
            CustomImage(content = exerciseName, id = icon, modifier = Modifier
                .size(40.dp), color = color1)
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)){
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
                            TableText(content = stringResource(R.string.n_a), color = color1)
                        } else {
                            TableText(content = weights[0].toString() + stringResource(R.string.kg), color = color1)
                        }
                    }
                    TableText(content = stringResource(R.string.sets, sets), color = color1)
                    TableText(content = stringResource(R.string.reps, reps), color = color1)
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
            // this button is what navigates to the addexercise scene when pressed
            if (editMode){
                Box(modifier = Modifier
                    .width(50.dp)
                    .background(Color.Transparent)
                    .height(40.dp)){
                    EditButton(buttonColor = MaterialTheme.colorScheme.surface,
                        iconColor = MaterialTheme.colorScheme.onSurface,
                        content = "Edit Button",
                        onClick = {
                            navController.navigate(Screen.AddExercise.route + "/$rowID") {
                        }},
                        id = Icons.Filled.Edit,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .align(Alignment.CenterStart))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseTablePreview() {
    GymAppTheme(dynamicColor = false) {
        val exercises = listOf(
        Exercise(0, R.drawable.icons8_curls,"Bicep Curl", 3, 12, false, 29,0,0),
        Exercise(0, R.drawable.icons8_squats,"Squats", 4, 16, false, 21,0,0),
        Exercise(0, R.drawable.icons8_curls,"Hammer Curls", 3, 8, true, 22,3,2)
        )

        ExerciseTable(navController = rememberNavController(), exerciseList = exercises)

    }
}