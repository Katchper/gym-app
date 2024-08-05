package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gymapp.R

/**
 * The Regular top app bar used on add/edit screens,
 * contains a save button and a return button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateCenterAlignedTopAppBar(navController: NavHostController, iconTint: Color, icon: ImageVector, title: String, route: String, onClick: () -> Unit, onClick2: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var enabled: Boolean by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()


    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                enabled = enabled,
                onClick = {
                    if (enabled){
                        enabled = false
                        onClick2()
                        enabled = true
                    }
                }

            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(
                enabled = enabled,
                onClick = {
                    if (enabled) {
                        enabled = false
                        onClick()
                        enabled = true
                    }

                    }) {
                Icon(
                    tint = iconTint,
                    imageVector = icon,
                    contentDescription = "delete button"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )

}

/**
 * The main screen top app bar which contains the title, icon and settings button
 * Main screens are all the menus present on the navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopAppBar(containerColor: Color, titleContentColor: Color, navController: NavHostController, route: String, onClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var enabled: Boolean by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
        ),
        title = {
            Row {
                CustomTitleText(size = 34.sp,
                    content = "Gym Planner",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(start = 40.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                CustomImage(
                    content = "ico",
                    id = R.drawable.icons8_dumbbell,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 10.dp),
                    color = titleContentColor
                )
            }

        },
        navigationIcon = {
            IconButton(
                enabled = enabled,
                onClick = {
                    if (enabled){
                        enabled = false
                        onClick()
                        navController.navigate(route) {
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                        enabled = true
                    }
                }
            ) {
                Icon(
                    tint = titleContentColor,
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        },
        actions = {},
        scrollBehavior = scrollBehavior,
    )
}