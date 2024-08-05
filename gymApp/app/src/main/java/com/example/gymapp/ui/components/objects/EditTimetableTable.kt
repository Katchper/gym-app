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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.navigation.Screen

/**
 * edit timetable table composable, takes in table ID, listsize, workout, exercise list
 * the nav controller and workouts view model
 * nav controller is passed through because the table has an edit button
 * which changes the scene.
 */

@Composable
fun EditTimetableTable(tableID : Int, modifier: Modifier = Modifier, listSize: Int, workout: Workout, exerciseList: List<Exercise>, navController: NavHostController, workoutsViewModel: WorkoutsViewModel = viewModel()
){
    // code for the alert dialog when deleting this table
    var openAlertDialog = remember { mutableStateOf(false) }
    when {
        openAlertDialog.value -> {
            AlertDialogTemplate(
                onDismissRequest = { openAlertDialog.value = false},
                onConfirmation = {
                    // delete this workout
                    workoutsViewModel.deleteFromID(id = tableID)
                    openAlertDialog.value = false
                },
                dialogTitle = "Delete Confirmation",
                dialogText = "Are you sure you want to delete this day?",
                icon = Icons.Outlined.Delete
            )
        }
    }
    // the surrounding box for the table
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
                TableTitle(content = workout.day,
                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                // APPROXIMATE WORKOUT TIME
                Box(modifier = Modifier.weight(1f)){
                    EditButton(buttonColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        iconColor = MaterialTheme.colorScheme.onSurface,
                        content = "Edit Button",
                        onClick = {
                                navController.navigate(Screen.EditDay.route + "/$tableID") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                        },
                        id = Icons.Filled.Edit,
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp)
                            .align(Alignment.CenterEnd))
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
                Row(modifier = Modifier.weight(1f)){
                    for (id in workout.imageToList()){
                        CustomImage(content = "", id = id, modifier = Modifier
                            .size(40.dp)
                            .weight(1f),
                            color = MaterialTheme.colorScheme.onSecondaryContainer)
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
                        color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = modifier.weight(1f))
                }
                Spacer(modifier = modifier.height(10.dp))
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
                        color1 = MaterialTheme.colorScheme.onSecondaryContainer,
                        id = 0,
                        counterValue = {boxid, selected ->}
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            // check if the total workouts != 1
            if (listSize != 1){
                // this row contains the delete button
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)){
                    Spacer(modifier = Modifier.weight(1f))
                    EditButton(buttonColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        iconColor = MaterialTheme.colorScheme.error,
                        content = "Delete Button",
                        onClick = {
                            openAlertDialog.value = true
                        },
                        id = Icons.Filled.Delete,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}