package com.example.gymapp.ui.screens.editday

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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.CustomDivider
import com.example.gymapp.ui.components.objects.CustomViewBox
import com.example.gymapp.ui.components.objects.DropDownMenuDay
import com.example.gymapp.ui.components.objects.DropDownMenuExercises
import com.example.gymapp.ui.components.objects.DropDownMenuMuscles
import com.example.gymapp.ui.components.objects.TemplateCenterAlignedTopAppBar
import com.example.gymapp.ui.components.objects.TimeTableViewTop
import com.example.gymapp.ui.components.objects.TimetableViewRow
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme


/**
 * The edit day screen which is very similar to the add day screen but contains
 * an extra dropdown menu for exercises.
 */
@Composable
fun EditDayScreen(idValue : Int, navController: NavHostController, exercisesViewModel: ExercisesViewModel = viewModel()
                  ,workoutsViewModel: WorkoutsViewModel = viewModel()) {
    var workout by remember { mutableStateOf(workoutsViewModel.getEntryFromID(idValue)) }
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    var daysAllowed by rememberSaveable  { mutableStateOf(mutableListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")) }
    val coroutineScope = rememberCoroutineScope()

    for (workoutTemp in allWorkouts){
        if (workoutTemp.id == idValue){
            workout = workoutTemp
        }
    }
    // remove any days from the list apart from the one which the workout day is

    for (workoutDay in allWorkouts){
        if (workoutDay.id != idValue){
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
    }
    TopLevelScaffold(
            onClick = {},
            topBar = {
                TemplateCenterAlignedTopAppBar(navController = navController,
                    title = "Change Exercise",
                    route = Screen.Timetable.route,
                    icon = Icons.Filled.Save,
                    iconTint = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick2 = {navController.navigateUp()},
                    onClick =
                    {
                        navController.navigateUp()
                        workout.timeCalc(allExercises)
                        workoutsViewModel.updateWorkout(idValue, workout)
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

                    EditDayScreenContent(daysAllowed = daysAllowed, allExercises = allExercises, workout = workout, insertWorkout = {workoutTemp-> workout = workoutTemp})
                }
            },
        coroutineScope = coroutineScope,
        bottomAppBar = false)



}


@Composable
fun EditDayScreenContent(daysAllowed: MutableList<String>, allExercises : List<Exercise>, workout: Workout, insertWorkout: (Workout) -> Unit = {}){
    val scrollState = rememberScrollState()
    var day: String by rememberSaveable { mutableStateOf(workout.day) }
    var muscleIconString: String by rememberSaveable  { mutableStateOf(workout.imageids) }
    var musclesString: String by rememberSaveable  { mutableStateOf(workout.muscles) }
    var exercisesString: String by rememberSaveable  { mutableStateOf(workout.exercises) }
    var workout by remember { mutableStateOf(Workout(0, day, musclesString, muscleIconString, 0, exercisesString)) }

    var daysAllowed by rememberSaveable  { mutableStateOf(daysAllowed) }

    var listOfMuscleIcons by rememberSaveable  { mutableStateOf(workout.imageToList()) }
    var listOfMuscleNames by rememberSaveable  { mutableStateOf(workout.muscleToList()) }
    var listOfExercises by rememberSaveable  { mutableStateOf(workout.exercisesToList()) }

    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(state = scrollState)){
        //TableTitle(content = workout.day, color = MaterialTheme.colorScheme.onBackground)
        //TableTitle(content = workout.muscles, color = MaterialTheme.colorScheme.onBackground)
        //TableTitle(content = workout.imageids, color = MaterialTheme.colorScheme.onBackground)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        TimeTableViewTop(workout = workout)
        Spacer(modifier = Modifier.height(20.dp))
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
            .height(20.dp))
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
        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))

            val exerciseNames = mutableListOf<String>()
            val exerciseIcons = mutableListOf<Int>()
            val exerciseIds = mutableListOf<Int>()
            for (exercise in allExercises){
                if (!exerciseIds.contains(exercise.id)){
                    exerciseNames.add(exercise.name)
                    exerciseIcons.add(exercise.imageId)
                    exerciseIds.add(exercise.id)
                }
            }
            DropDownMenuExercises(
                names = exerciseNames,
                idValues = exerciseIds,
                icons = exerciseIcons,
                menuValues = { id ->
                    listOfExercises = (listOfExercises + id).toMutableList()
                    workout.exercises = listOfExercises.joinToString(",")
                    insertWorkout(workout)

            })
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))
        var counter = 0
        Box(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)){
            Column {
                for (currentExercise in listOfExercises){
                    var listID = counter
                    var exerciseFill = Exercise(name = "$53nh5t7F6d3sa$")
                    //find the corresponding exercise name and image

                    for(tempExercise in allExercises){
                    if (tempExercise.id == currentExercise){
                    exerciseFill = tempExercise
                        }
                    }
                    if (exerciseFill.name != "$53nh5t7F6d3sa$"){
                        TimetableViewRow(
                            exerciseName = exerciseFill.name,
                            icon = exerciseFill.imageId,
                            weights = listOf(exerciseFill.weight1, exerciseFill.weight2, exerciseFill.weight3) ,
                            sets = exerciseFill.sets.toString(),
                            reps = exerciseFill.reps.toString(),
                            isDropSet = exerciseFill.dropset,
                            isVisual = false,
                            color1 = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            onClick = {
                                val tempList = listOfExercises.toMutableList()
                                tempList.removeAt(listID)
                                listOfExercises = tempList
                                workout.exercises = listOfExercises.joinToString(",")
                                insertWorkout(workout)
                            })
                        if (listID != listOfExercises.size-1){
                            CustomDivider(color = Color.Black, modifier = Modifier
                                .padding(top = 4.dp)
                                .padding(bottom = 4.dp))
                        }

                    }
                    counter++
                }
            }

        }
        Spacer(modifier = Modifier.height(240.dp))
    }



}

@Preview(showBackground = true)
@Composable
fun EditDayPreview() {
    GymAppTheme(dynamicColor = false) {
        EditDayScreen(idValue = 0, navController = rememberNavController())
    }
}