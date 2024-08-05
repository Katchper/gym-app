package com.example.gymapp.ui.screens.timetableview

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
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.CustomTitleText
import com.example.gymapp.ui.components.objects.MainScreenTopAppBar
import com.example.gymapp.ui.components.objects.TimetableTable
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * This is a purely functional screen used for viewing the timetable, it
 * uses the same tables as in home but
 */
@Composable
fun TimetableViewScreen(navController: NavHostController,
                    exercisesViewModel: ExercisesViewModel = viewModel(),
                    workoutsViewModel: WorkoutsViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())

    TopLevelScaffold(
            onClick = {},
            topBar = {
                MainScreenTopAppBar(
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                    navController = navController,
                    route = Screen.Settings.route) {}
            },
            navController = navController,
            floatingActionButton = {},
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    TimetableViewScreenContent(exerciseList = allExercises, workoutsList = allWorkouts)
                }
            },
            coroutineScope = coroutineScope,
            bottomAppBar = true)


}

@Composable
fun TimetableViewScreenContent(exerciseList: List<Exercise>, workoutsList: List<Workout>) {
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to  MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                    0.3f to MaterialTheme.colorScheme.background,
                )
            )
    ) {
        //TITLE TEXT BOX
        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
        CustomTitleText(content = "Timetable", size = 30.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        for (day in workoutsList){
            TimetableTable(workout = day, exerciseList = day.getAssociatedExercises(exerciseList))
        }
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
        TimetableViewScreen(navController = rememberNavController())
    }
}