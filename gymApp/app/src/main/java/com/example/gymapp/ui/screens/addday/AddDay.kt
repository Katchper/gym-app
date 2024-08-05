package com.example.gymapp.ui.screens.addday

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gymapp.R
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.CustomViewBox
import com.example.gymapp.ui.components.objects.DropDownMenuDay
import com.example.gymapp.ui.components.objects.DropDownMenuMuscles
import com.example.gymapp.ui.components.objects.SnackbarWithTimeout
import com.example.gymapp.ui.components.objects.TemplateCenterAlignedTopAppBar
import com.example.gymapp.ui.components.objects.TimeTableViewTop
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme
import kotlinx.coroutines.launch

/**
 * The add day screen composable, it is the screen which contains two main
 * input regions for the user to use to add an available day to the list of
 * workouts DB
 */
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun AddDayScreen(navController: NavHostController,
                 workoutsViewModel: WorkoutsViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var workout by remember { mutableStateOf(Workout(0, "", muscles = "Biceps", imageids = R.drawable.icons8_biceps.toString(), 0, exercises = "")) }
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
    var daysAllowed by rememberSaveable  { mutableStateOf(mutableListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")) }

    // checks the list of workouts and removes the day from the list if present in the workout
    for (workoutDay in allWorkouts){
        when (workoutDay.day) {
            "Monday" -> daysAllowed.remove("Monday")
            "Tuesday" -> daysAllowed.remove("Tuesday")
            "Wednesday" -> daysAllowed.remove("Wednesday")
            "Thursday" -> daysAllowed.remove("Thursday")
            "Friday" -> daysAllowed.remove("Friday")
            "Saturday" -> daysAllowed.remove("Saturday")
            "Sunday" -> daysAllowed.remove("Sunday")
        }
    }

     TopLevelScaffold(
            onClick = {},
            snackbarContent = { data -> SnackbarWithTimeout(
                containerCol = MaterialTheme.colorScheme.errorContainer,
                textCol = MaterialTheme.colorScheme.onErrorContainer,
                actionCol = MaterialTheme.colorScheme.onError,
                data = data,
                modifier = Modifier.padding(bottom = 4.dp),onDismiss = { data.dismiss() }) },
            topBar = {
                TemplateCenterAlignedTopAppBar(navController = navController,
                    title = "Add Day",
                    route = Screen.Timetable.route,
                    icon = Icons.Filled.Save,
                    iconTint = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick2 = {navController.navigateUp()},
                    onClick = {
                        var isValid = true
                        if (workout.day == ""){
                            isValid = false
                        }
                        for (workoutDay in allWorkouts){
                            if (workout.day == workoutDay.day){
                                isValid = false
                            }
                        }
                        if (isValid){
                            workoutsViewModel.insertWorkout(workout)
                            navController.navigateUp()
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Day invalid, select a different day.",
                                    actionLabel = "Ok",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                    })
            },
            navController = navController,
            floatingActionButton = {
            },
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    AddDayScreen(daysAllowed = daysAllowed, insertWorkout = {workoutListed -> workout = workoutListed })
                }
            },
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            bottomAppBar = false)


}

/**
 * The add day screen content which includes, the drop down menus to select
 * muscles and the day
 * This will then return the choice back up to the main screen method above
 * Parameter: days allowed is the list of days available to choose from
 */

@Composable
fun AddDayScreen(daysAllowed: MutableList<String>, insertWorkout: (Workout) -> Unit = {}){
    val scrollState = rememberScrollState()
    var day: String by rememberSaveable { mutableStateOf("") }
    var daysAllowed by rememberSaveable  { mutableStateOf(daysAllowed) }
    var listOfMuscleIcons by rememberSaveable  { mutableStateOf(mutableListOf<Int>()) }
    var listOfMuscleNames by rememberSaveable  { mutableStateOf(mutableListOf<String>()) }
    var workout by remember { mutableStateOf(Workout(0, day, muscles = listOfMuscleNames.joinToString(","), imageids = listOfMuscleIcons.joinToString(","), 0, exercises = "")) }

    Column (modifier = Modifier.verticalScroll(state = scrollState)){
        //TableTitle(content = listOfMuscleIcons.joinToString(","), color = MaterialTheme.colorScheme.onBackground)
        //TableTitle(content = listOfMuscleNames.joinToString(","), color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        TimeTableViewTop(workout = workout)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            DropDownMenuDay(selectedDay = day, availableDays = daysAllowed, dateSelected = { dayName ->
                day = dayName
                workout.day = day
                insertWorkout(workout)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            DropDownMenuMuscles(menuValues = { icon, name ->
                if (!listOfMuscleIcons.contains(icon)){
                    if (name != ""){
                        listOfMuscleIcons = (listOfMuscleIcons + icon).toMutableList()
                        listOfMuscleNames = (listOfMuscleNames + name).toMutableList()
                        workout.muscles = listOfMuscleNames.joinToString(",")
                        workout.imageids = listOfMuscleIcons.joinToString(",")
                        insertWorkout(workout)
                    }

                }
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        for (currentIcon in listOfMuscleIcons){
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
            Row {
                var index = listOfMuscleIcons.indexOf(currentIcon)
                Spacer(modifier = Modifier.weight(1f))
                CustomViewBox(image = currentIcon, text = listOfMuscleNames[index], modifier = Modifier.width(300.dp), onClick = {
                    listOfMuscleIcons = listOfMuscleIcons.filter { it != currentIcon }.toMutableList()
                    listOfMuscleNames = listOfMuscleNames.filter { it != listOfMuscleNames[index] }.toMutableList()
                    workout.muscles = listOfMuscleNames.joinToString(",")
                    workout.imageids = listOfMuscleIcons.joinToString(",")
                    insertWorkout(workout)
                })
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(240.dp))

    }

}


@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
        AddDayScreen(mutableListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"), insertWorkout = {})
    }
}