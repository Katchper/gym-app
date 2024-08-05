package com.example.gymapp.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.CustomTitleText
import com.example.gymapp.ui.components.objects.ExerciseTable
import com.example.gymapp.ui.components.objects.ExercisesFloatingActionButton
import com.example.gymapp.ui.components.objects.MainScreenTopAppBar
import com.example.gymapp.ui.components.objects.SnackbarWithTimeout
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme
import kotlinx.coroutines.launch

/**
 * The exercises screen composable, contains a table which displays
 * every exercise in the database. The user can press the FAB to add a new
 * blank exercise or they can press the edit button to enter exit mode
 * allowing them to press on any exercise
 */
@Composable
fun ExercisesScreen(navController: NavHostController,
                    exercisesViewModel: ExercisesViewModel = viewModel(),
                    workoutsViewModel: WorkoutsViewModel = viewModel()) {
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    for (workout in allWorkouts) {
        workout.timeCalc(allExercises)
        workoutsViewModel.updateWorkout(workout.id, workout)
    }

 TopLevelScaffold(
            onClick = {},
            snackbarContent = { data -> SnackbarWithTimeout(
                containerCol = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                textCol = MaterialTheme.colorScheme.onSurface,
                actionCol = MaterialTheme.colorScheme.onSurfaceVariant,
                data = data,
                modifier = Modifier.padding(bottom = 4.dp),onDismiss = { data.dismiss() })
            },
            topBar = {
                MainScreenTopAppBar(
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                    navController = navController,
                    route = Screen.Settings.route) {}
            },
            navController = navController,
            floatingActionButton = {
                ExercisesFloatingActionButton(navController = navController,icon=Icons.Filled.Add,  onClick = {
                    exercisesViewModel.addEmptyEntry()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "added new exercise",
                            actionLabel = "Ok",
                            duration = SnackbarDuration.Short
                        )
                    }
                })
            },
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    ExercisesScreenContent(navController = navController, exercises = allExercises)
                }
            },
     snackbarHostState = snackbarHostState,
     coroutineScope = coroutineScope,
     bottomAppBar = true)



}

/**
 * The screen content composable that gets passed through the scaffold function
 */
@Composable
fun ExercisesScreenContent(navController: NavHostController, exercises : List<Exercise>) {
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                    0.3f to MaterialTheme.colorScheme.background,
                )
            )
    ) {
        //TITLE TEXT BOX
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp))
        ExerciseTable(exerciseList = exercises, navController = navController)
        Spacer(modifier = Modifier.height(60.dp))
        if (exercises.isEmpty()){
            CustomTitleText(content = "Press the button to add an exercise", size = 20.sp, modifier = Modifier.align(
                Alignment.CenterHorizontally))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
        ExercisesScreenContent(navController = rememberNavController(), exercises = emptyList())
    }
}