package com.example.gymapp.ui.screens.addexercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.AlertDialogTemplate
import com.example.gymapp.ui.components.objects.CustomCheckBoxWithLabel
import com.example.gymapp.ui.components.objects.DropDownMenuSearch
import com.example.gymapp.ui.components.objects.EditButton
import com.example.gymapp.ui.components.objects.NumberTextField
import com.example.gymapp.ui.components.objects.TemplateCenterAlignedTopAppBar
import com.example.gymapp.ui.components.objects.TextInputField
import com.example.gymapp.ui.components.objects.TimetableViewRow
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * The add exercises screen which handles editing of an exercise
 * It has several input spaces for the user to edit information about the exercise
 * When the user is done they can press the save button to update the exercises DB
 * If they decide to delete they will be met by a dialog menu to confirm
 * if they do then it deletes it from the DB
 */

@Composable
fun AddExerciseScreen(idValue : Int, navController: NavHostController, exercisesViewModel: ExercisesViewModel = viewModel()
                      ,workoutsViewModel: WorkoutsViewModel = viewModel()) {
    var openAlertDialog = rememberSaveable { mutableStateOf(false) }
    var exercise by remember { mutableStateOf(exercisesViewModel.getEntryFromID(idValue)) }
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()
    // find the exercise that matches id with the id parameter
    for (exerciseTemp in allExercises){
        if (exerciseTemp.id == idValue){
            exercise = exerciseTemp
        }
    }
        TopLevelScaffold(
            onClick = {},
            topBar = {
                TemplateCenterAlignedTopAppBar(navController = navController,
                    title = "Change Exercise",
                    route = Screen.Exercises.route,
                    icon = Icons.Filled.Save,
                    iconTint = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick2 = {navController.navigateUp()},
                    onClick =
                    {
                        navController.navigateUp()
                        exercisesViewModel.updateExercise(idValue, exercise)
                        for (workout in allWorkouts){
                            workout.timeCalc(allExercises)
                            workoutsViewModel.updateWorkout(workout.id, workout)
                        }

                    })
            },
            navController = navController,
            floatingActionButton = {},
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    when {
                        openAlertDialog.value -> {
                            AlertDialogTemplate(
                                onDismissRequest = { openAlertDialog.value = false },
                                onConfirmation = {
                                    exercisesViewModel.deleteFromID(idValue)
                                    navController.navigateUp()
                                    openAlertDialog.value = false
                                },
                                dialogTitle = "Delete Confirmation",
                                dialogText = "Are you sure you want to delete this exercise?",
                                icon = Icons.Outlined.Delete
                            )
                        }
                    }
                    ExercisesScreen(exercise = exercise, insertExercise = { insertExercise -> exercise = insertExercise }, click = {isClicked -> if (isClicked){openAlertDialog.value = true}})
                }
            },
            coroutineScope = coroutineScope,
            bottomAppBar = false)


}

/**
 * The edit exercise screen content, contains several state variables holding the
 * information on the screen in a remember saveable format,
 * returns the exercise when a change is made,
 * returns a click boolean when the delete button is pressed
 */
@Composable
fun ExercisesScreen(exercise: Exercise, insertExercise: (Exercise) -> Unit = {}, click: (Boolean) -> Unit = {}){
    val scrollState = rememberScrollState()
    var icon: Int by rememberSaveable { mutableIntStateOf(exercise.imageId) }
    var name: String by rememberSaveable { mutableStateOf(exercise.name) }
    var reps: Int by rememberSaveable { mutableIntStateOf(exercise.reps) }
    var sets: Int by rememberSaveable { mutableIntStateOf(exercise.sets) }
    var dropset: Boolean by rememberSaveable { mutableStateOf(exercise.dropset) }
    var weight1: Int by rememberSaveable { mutableIntStateOf(exercise.weight1) }
    var weight2: Int by rememberSaveable { mutableIntStateOf(exercise.weight2) }
    var weight3: Int by rememberSaveable { mutableIntStateOf(exercise.weight3) }
    var exercise by remember { mutableStateOf(Exercise(0, icon, name, sets,reps,dropset,weight1,weight2,weight3)) }

    Column (modifier = Modifier.fillMaxWidth().verticalScroll(state = scrollState)){
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        //TableTitle(content = exercise.name, color = MaterialTheme.colorScheme.onBackground)
        //TableTitle(content = exercise.imageId.toString(), color = MaterialTheme.colorScheme.onBackground)
        //TableTitle(content = exercise.weight1.toString(), color = MaterialTheme.colorScheme.onBackground)
        Box(modifier = Modifier
            .padding(5.dp)
            .padding(top = 20.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .background(MaterialTheme.colorScheme.primaryContainer)){
            TimetableViewRow(
                exerciseName = name,
                icon = icon,
                weights = listOf(
                    weight1,
                    weight2,
                    weight3
                ),
                sets = sets.toString(),
                reps = reps.toString(),
                isDropSet = dropset,
                isVisual = true,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                color1 = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        Row {

            Spacer(modifier = Modifier.weight(1f))
            TextInputField(modifier = Modifier
                .width(250.dp)
                .height(60.dp), label = "Name of exercise", existingName = name, text = {
                    setsString ->
                name = setsString
                exercise.name = name
                insertExercise(exercise)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            DropDownMenuSearch(selectedIcon = icon,
                menuValues = {
                    iconVal, _ ->
                icon = iconVal
                exercise.imageId = icon
                insertExercise(exercise)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            NumberTextField(value = reps.toString(),
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp), label = "Reps", text = {
                    repsString ->
                reps = repsString.toInt()
                exercise.reps = reps
                insertExercise(exercise)
            })
            Spacer(modifier = Modifier.weight(1f))
            NumberTextField(value = sets.toString(),
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp), label = "Sets", text = {
                    setsString ->
                sets = setsString.toInt()
                exercise.sets = sets
                insertExercise(exercise)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        Row(modifier = Modifier.height(100.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            CustomCheckBoxWithLabel(checked = dropset,
                label = "Drop Set", progressValue = { isdropset ->
                    dropset = isdropset
                exercise.dropset = dropset
                    if (!exercise.dropset){
                        weight2 = 0
                        weight3 = 0
                        exercise.weight2 = 0
                        exercise.weight3 = 0
                    }
                insertExercise(exercise)
            })
            var textLabel = "Weight in Kg"
            if (dropset){
                textLabel = "Weight One"
            }
            Spacer(modifier = Modifier.weight(1f))
            NumberTextField(value = weight1.toString(),
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp), label = textLabel, text = {
                    weight1String ->
                weight1 = weight1String.toInt()
                exercise.weight1 = weight1
                insertExercise(exercise)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))

        if (dropset){
            Row {
                Spacer(modifier = Modifier.weight(4f))
                NumberTextField(value = weight2.toString(),
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp), label = "Weight Two", text = {
                        weight2String ->
                    weight2 = weight2String.toInt()
                    exercise.weight2 = weight2
                    insertExercise(exercise)
                })
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            Row {
                Spacer(modifier = Modifier.weight(4f))
                NumberTextField(value = weight3.toString(),
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp), label = "Weight Three", text = {
                        weight3String ->
                    weight3 = weight3String.toInt()
                    exercise.weight3 = weight3
                    insertExercise(exercise)
                })
                Spacer(modifier = Modifier.weight(1f))
            }

        } else {
            weight2 = 0
            weight3 = 0
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row{
            Spacer(modifier = Modifier.weight(1f))
            EditButton(buttonColor = MaterialTheme.colorScheme.errorContainer,
                iconColor = MaterialTheme.colorScheme.onErrorContainer,
                content = "Delete Button",
                onClick = {
                    click(true)
                },
                id = Icons.Filled.Delete,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp))
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(240.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
        AddExerciseScreen(idValue = 0, navController = rememberNavController())
    }
}