package com.example.gymapp.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gymapp.model.classgroup.ExercisesViewModel
import com.example.gymapp.model.classgroup.WorkoutsViewModel
import com.example.gymapp.ui.components.TopLevelScaffold
import com.example.gymapp.ui.components.objects.AboutText
import com.example.gymapp.ui.components.objects.AlertDialogTemplate
import com.example.gymapp.ui.components.objects.BoldTableText
import com.example.gymapp.ui.components.objects.DeleteButton
import com.example.gymapp.ui.components.objects.TemplateCenterAlignedTopAppBar
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.theme.GymAppTheme

/**
 * The settings screen, contains 3 settings features, only 2 of which work presently
 * the reset exercises button, reset workouts button and dark mode switch.
 * This menu can be accessed from any main navigation screen.
 */
@Composable
fun SettingsScreen(navController: NavHostController,
                   exercisesViewModel: ExercisesViewModel = viewModel(),
                   workoutsViewModel: WorkoutsViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
            onClick = {},
            topBar = {
                TemplateCenterAlignedTopAppBar(navController = navController,
                    title = "Settings",
                    route = Screen.Home.route,
                    icon = Icons.Filled.Settings,
                    iconTint = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick2 = {navController.navigateUp()},
                    onClick = {})
            },
            navController = navController,
            floatingActionButton = {},
            pageContent = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    SettingsScreenContent(exercisesViewModel, workoutsViewModel)
                }
            },
        coroutineScope = coroutineScope,
            bottomAppBar = false)


}

@Composable

fun SettingsScreenContent(exercisesViewModel: ExercisesViewModel = viewModel(),
                          workoutsViewModel: WorkoutsViewModel = viewModel()) {
    var context: Context = LocalContext.current
    var isDarkMode by remember { mutableStateOf(loadDarkModeState(context)) }
    val scrollState = rememberScrollState()

    var openAlertDialog1 = remember { mutableStateOf(false) }
    var openAlertDialog2 = remember { mutableStateOf(false) }

    var buttonClicked = remember { mutableStateOf(false) }
    if (buttonClicked.value){
        when {
            openAlertDialog1.value -> {
                AlertDialogTemplate(
                    onDismissRequest = { openAlertDialog1.value = false
                        buttonClicked.value = false},
                    onConfirmation = {
                        buttonClicked.value = false
                        workoutsViewModel.clearDatabase()
                        workoutsViewModel.addEmptyEntry()
                        openAlertDialog1.value = false
                    },
                    dialogTitle = "Delete workouts",
                    dialogText = "This action will permanently delete your workouts, THIS IS IRREVERSIBLE.",
                    icon = Icons.Outlined.Delete
                )
            }
        }
        when {
            openAlertDialog2.value -> {
                AlertDialogTemplate(
                    onDismissRequest = { openAlertDialog2.value = false
                        buttonClicked.value = false},
                    onConfirmation = {
                        exercisesViewModel.clearDatabase()
                        openAlertDialog2.value = false
                        buttonClicked.value = false
                    },
                    dialogTitle = "Delete exercises",
                    dialogText = "This action will permanently delete your exercises, THIS IS IRREVERSIBLE.",
                    icon = Icons.Outlined.Delete
                )
            }
        }
    }


    Column(modifier = Modifier.verticalScroll(state = scrollState)
        .background(brush = Brush.verticalGradient(
            0.0f to MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f),
            0.3f to MaterialTheme.colorScheme.background,
        ))) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Row(
            modifier = Modifier
                .padding(5.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(14.dp)
                )
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            BoldTableText(modifier = Modifier.align(Alignment.CenterVertically),content = "Dark Mode", color = MaterialTheme.colorScheme.onTertiaryContainer)
            Spacer(modifier = Modifier.width(10.dp))
            Switch(checked = isDarkMode, onCheckedChange = {
                isDarkMode = it
                saveDarkModeState(context, isDarkMode)
            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier
                .padding(5.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(14.dp)
                )
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            DeleteButton(text = "Reset Exercises", modifier = Modifier.padding(vertical = 10.dp), onClick = {
                if (!buttonClicked.value){
                    openAlertDialog2.value = true
                    buttonClicked.value = true
                }

            })
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .padding(5.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(14.dp)
                )
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            DeleteButton(text = "Reset Workouts", modifier = Modifier.padding(vertical = 10.dp), onClick = {
                if (!buttonClicked.value){
                    openAlertDialog1.value = true
                    buttonClicked.value = true
                }
            })
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))
        AboutText(modifier = Modifier.align(Alignment.CenterHorizontally),content = "Version 1.0 (1)")
        AboutText(modifier = Modifier.align(Alignment.CenterHorizontally),content = "©2023 Kacper Dziedzic Icons by Icon8")
        Spacer(modifier = Modifier.height(150.dp))
    }
}


private fun loadDarkModeState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("DarkTheme", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("DarkMode", false)
}


private fun saveDarkModeState(context: Context, isDarkMode: Boolean) {
    val sharedPreferences = context.getSharedPreferences("DarkTheme", Context.MODE_PRIVATE)
    sharedPreferences.edit {
        putBoolean("DarkMode", isDarkMode)
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    GymAppTheme(dynamicColor = false) {
    }
}