package com.example.gymapp.ui.navigation

/**
 * Wraps all the screens together for use within the nav graph
 */

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Timetable : Screen("timetable")
    object Exercises : Screen("exercises")
    object AddExercise : Screen("addexercise")
    object AddDay : Screen("addday")
    object Settings : Screen("settings")
    object TimetableView : Screen("timetableview")
    object EditDay : Screen("editday")
}

/**
 * List of screens within the navigation bar
 */
val screens = listOf(
    Screen.Home,
    Screen.TimetableView,
    Screen.Timetable,
    Screen.Exercises,
)