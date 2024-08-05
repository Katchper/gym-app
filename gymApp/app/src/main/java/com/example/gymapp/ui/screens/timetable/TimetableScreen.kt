package com.example.gymapp.ui.screens.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.gymapp.ui.components.objects.EditTimetableTable
import com.example.gymapp.ui.components.objects.MainScreenTopAppBar
import com.example.gymapp.ui.components.objects.TimetableFloatingActionButton
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * This is the edit timetable screen, it shows the workouts and all their exercises
 * and has buttons which allow the user to edit a specific day or delete it
 */
@Composable
fun TimetableScreen(navController: NavHostController,
                    exercisesViewModel: ExercisesViewModel = viewModel(),
                    workoutsViewModel: WorkoutsViewModel = viewModel()
) {
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
            onClick = {},
            topBar = {
                MainScreenTopAppBar(
                    navController = navController,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    route = Screen.Settings.route) {}
            },
            navController = navController,
            floatingActionButton = {
                if (allWorkouts.size < 7){
                    TimetableFloatingActionButton(navController = navController)
                }
            },
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    TimetableScreenContent(
                        exerciseList = allExercises,
                        workoutsViewModel = workoutsViewModel,
                        navController = navController)
                }
            },
        coroutineScope = coroutineScope,
        bottomAppBar = true)


}

@Composable
fun TimetableScreenContent(navController: NavHostController,
                           workoutsViewModel: WorkoutsViewModel = viewModel(),
                           exerciseList: List<Exercise>) {
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
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
            .height(20.dp))
        CustomTitleText(content = "Edit Timetable", size = 30.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        for (day in allWorkouts){
            EditTimetableTable(
                tableID = day.id,
                listSize = allWorkouts.size,
                workout = day,
                exerciseList = day.getAssociatedExercises(exerciseList),
                workoutsViewModel = workoutsViewModel,
                navController = navController)
        }
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
        TimetableScreen(navController = rememberNavController())
    }
}