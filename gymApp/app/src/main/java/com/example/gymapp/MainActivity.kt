package com.example.gymapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.navigation.Screen.Home
import com.example.gymapp.ui.navigation.Screen.Timetable
import com.example.gymapp.ui.screens.addday.AddDayScreen
import com.example.gymapp.ui.screens.addexercise.AddExerciseScreen
import com.example.gymapp.ui.screens.editday.EditDayScreen
import com.example.gymapp.ui.screens.exercises.ExercisesScreen
import com.example.gymapp.ui.screens.home.HomeScreen
import com.example.gymapp.ui.screens.settings.SettingsScreen
import com.example.gymapp.ui.screens.timetable.TimetableScreen
import com.example.gymapp.ui.screens.timetableview.TimetableViewScreen
import com.example.gymapp.ui.theme.GymAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * The main activity method, this is what is called when the app is opened,
 * It sets the theme, disables multitouch, sets colours in the app,
 * Builds the navigation graph which handles everything within the app
 */

class MainActivity : ComponentActivity() {
    //turning off multitouches, as they cause problems with a couple screens
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ev?.getPointerCount() == 1 && super.dispatchTouchEvent(ev)
    }

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme(dynamicColor = false) {
                SetStatusBarColor(bottomColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    topColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

/**
 * Sets the colours of the top app bar and the bottom nav bar
 * AESTHETIC REASONS
 */
@Composable
fun SetStatusBarColor(bottomColor: Color, topColor : Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(bottomColor)
        systemUiController.setStatusBarColor(topColor)
    }
}

/**
 * BuildNavigationGraph is very important comopsable function
 * which handles the navigation throughout the app, switching between screens,
 * navigating backwards and communication between screens
 */

@Composable
private fun BuildNavigationGraph(exercisesViewModel: ExercisesViewModel = viewModel(),
                                 workoutsViewModel: WorkoutsViewModel = viewModel()) {
    // The NavController is in a place where all
    // our composables can access it.
    val navController = rememberNavController()

    // Each NavController is associated with a NavHost.
    // This links the NavController with a navigation graph.
    // As we navigate between composables the content of
    // the NavHost is automatically recomposed.
    // Each composable destination in the graph is associated with a route.
    NavHost(
        enterTransition = {fadeIn(initialAlpha = 0f)},
        exitTransition = { fadeOut(targetAlpha = 0f) },
        popEnterTransition = {fadeIn(initialAlpha = 0f)},
        popExitTransition = {fadeOut(targetAlpha = 0f)},
        navController = navController,
        startDestination = Home.route
    ) {
        // LIST OF ALL THE COMPOSABLES WITH THE DESIGNATED ROUTES, SENDS
        // THE CORRESPONDING PARAMTERS AS WELL AS VARIABLES BETWEEN SCREENS
        composable(Home.route) { HomeScreen(navController, exercisesViewModel, workoutsViewModel) }
        composable(Timetable.route) {TimetableScreen(navController, exercisesViewModel, workoutsViewModel) }
        composable(Screen.Exercises.route) {ExercisesScreen(navController, exercisesViewModel, workoutsViewModel) }
        // allow for an id to be passed through to the edit exercise screen
        composable(Screen.AddExercise.route +"/{intValue}",
            arguments = listOf(navArgument("intValue") { type = NavType.IntType })
        ){ backStackEntry ->
            val intValue = backStackEntry.arguments?.getInt("intValue") ?: 0
            AddExerciseScreen(intValue, navController, exercisesViewModel, workoutsViewModel)
        }
        composable(Screen.AddDay.route) {AddDayScreen(navController, workoutsViewModel) }
        composable(Screen.TimetableView.route) { TimetableViewScreen(navController, exercisesViewModel, workoutsViewModel) }
        composable(Screen.EditDay.route +"/{intValue}",
            arguments = listOf(navArgument("intValue") { type = NavType.IntType })
        ){ backStackEntry ->
            val intValue = backStackEntry.arguments?.getInt("intValue") ?: 0
            EditDayScreen(intValue, navController, exercisesViewModel, workoutsViewModel)
        }
        composable(Screen.Settings.route) { SettingsScreen(navController, exercisesViewModel, workoutsViewModel) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymAppTheme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}