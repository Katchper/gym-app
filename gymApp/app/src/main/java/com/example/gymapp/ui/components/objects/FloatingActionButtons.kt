package com.example.gymapp.ui.components.objects

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.gymapp.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * floating action button for navigating to the addDay screen
 */
@Composable
fun TimetableFloatingActionButton(navController: NavController) {
    LargeFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onClick = {
                navController.navigate(Screen.AddDay.route){
                    // dont remove the exercise screen route when clicking on add exercise FAB
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true

            }
        },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

/**
 * FAB for the exercises screen, has a delay of 300ms between presses
 */
@Composable
fun ExercisesFloatingActionButton(navController: NavController, icon: ImageVector, onClick: () -> Unit ) {
    var enabled: Boolean by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    LargeFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onClick = {
            if (enabled){
                enabled = false
                onClick()
                coroutineScope.launch {
                    delay(300)
                    enabled = true
                }
            }
        },
    ) {
        Icon(icon, "Floating action button.")
    }
}
