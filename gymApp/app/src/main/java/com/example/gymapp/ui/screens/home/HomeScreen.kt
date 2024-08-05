package com.example.gymapp.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gymapp.R
import com.example.gymapp.model.classgroup.Exercise
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.Workout
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.CustomImageReg
import com.example.gymapp.ui.components.objects.CustomLinearProgressBar
import com.example.gymapp.ui.components.objects.CustomTable
import com.example.gymapp.ui.components.objects.MainScreenTopAppBar
import com.example.gymapp.ui.components.objects.ProgressText
import com.example.gymapp.ui.components.objects.TommorowTable
import com.example.gymapp.ui.components.objects.WelcomeText
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme
import java.util.Calendar

/**
 * The home screen which is the first screen the user sees, contains
 * 2 tables which display the workouts for today and tommorow
 * with emphasis on todays table.
 */
@Composable
fun HomeScreen(navController: NavHostController,
               exercisesViewModel: ExercisesViewModel = viewModel(),
               workoutsViewModel: WorkoutsViewModel = viewModel()) {
    val allExercises by exercisesViewModel.exercisesList.observeAsState(listOf())
    val allWorkouts by workoutsViewModel.workoutList.observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
            onClick = {},
            topBar = {
                MainScreenTopAppBar(
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                    navController = navController,
                    route = Screen.Settings.route) {
                }
            },
            navController = navController,
            floatingActionButton = {},
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    HomeScreenContent(allExercises, allWorkouts)
                }
            },
        coroutineScope = coroutineScope,
        bottomAppBar = true)

}

/**
 * The content of the home screen, the text, tables, progress bar.
 */
@Composable
fun HomeScreenContent(exerciseList: List<Exercise>, workoutsList: List<Workout>){
    var progress: Float by rememberSaveable { mutableFloatStateOf(0f) }
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
    ){
        //TITLE TEXT BOX
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
        ){
            WelcomeText(
                content = "Hello there !",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp)
            )
        }
        // PROGRESS BAR TEXT + ICONS
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(50.dp)){
            val percentage = (progress*100).toString().substringBefore(".")
            if (percentage.equals("100")){
                CustomImageReg(
                    content = "confetti",
                    id = R.drawable.icons8_confetti,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .size(40.dp)
                        .scale(scaleX = -1f, scaleY = 1f)
                )
            }

            ProgressText(
                content = "$percentage% done",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(end = 12.dp)
                    .align(Alignment.Bottom)
            )
            if (percentage.equals("100")){
                CustomImageReg(
                    content = "confetti",
                    id = R.drawable.icons8_confetti,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .size(40.dp)
                )
            }

        }
        //PROGRESS BAR
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()){
            CustomLinearProgressBar(
                modifier = Modifier
                    .padding(top = 10.dp),
                currentProgress = progress
            )
        }
        //Gym Timetable
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()){

        }
        var todayWorkout = Workout(id = 0, day = returnToday(), muscles = "", imageids = "", time = 0, exercises = "")
        var tomorrowWorkout = Workout(id = 0, day = returnTommorow(), muscles = "", imageids = "", time = 0, exercises = "")

        for (workout in workoutsList){
            if (workout.day == returnToday()){
                todayWorkout = workout
            }
            if (workout.day == returnTommorow()){
                tomorrowWorkout = workout
            }
        }
        // test values for the customTable
        CustomTable(workout = todayWorkout, exerciseList = todayWorkout.getAssociatedExercises(exerciseList),progressValue = { updatedCounter ->
            // Handle the updated counter value here
            progress = updatedCounter
        })
        TommorowTable(workout = tomorrowWorkout, exerciseList = tomorrowWorkout.getAssociatedExercises(exerciseList))
        //TableRow(exerciseName = "Bicep Curls", icon = R.drawable.icons8_abs, weight = "72kg", sets = "3 Sets", reps = "12 Reps", isDropSet = false)
        //TESTING
        Spacer(modifier = Modifier.height(150.dp))
    }
}


fun returnToday() : String{
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return returnDay(dayOfWeek)
}

fun returnTommorow() : String{
    val calendar = Calendar.getInstance()
    var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)+1
    if (dayOfWeek > 7){
        dayOfWeek = 1
    }
    return returnDay(dayOfWeek)
}

fun returnDay(day : Int) : String {
    val dayOfWeek = day
    var dayString = ""

    dayString = when (dayOfWeek) {
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        Calendar.SUNDAY-> "Sunday"
        else -> {
            ""
        }
    }

    return dayString
}

private fun loadDarkModeState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("DarkTheme", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("DarkMode", false)
}

@Preview(showBackground = true)
@Composable
fun TablePreview() {
    GymAppTheme(dynamicColor = false) {
        HomeScreenContent(exerciseList = emptyList(), workoutsList = emptyList())
    }
}

