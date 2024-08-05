package com.example.gymapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.R
import com.example.gymapp.ui.navigation.Screen
import com.example.gymapp.ui.navigation.screens

/**
 * The navigation bar which is present on the main screens of the app
 * contains, home, timetable, timetable edit, exercises
 */

@Composable
fun MainPageNavigationBar(navClick: () -> Unit,
    navController: NavController, ) {
    // the icon groups for the navigation bar
    val icons = mapOf(
        Screen.Home to IconGroup(
            filledIcon = Icons.Filled.Home,
            outlineIcon = Icons.Outlined.Home,
            label = stringResource(id = R.string.home)
        ),
        Screen.TimetableView to IconGroup(
            filledIcon = Icons.Filled.CalendarToday,
            outlineIcon = Icons.Outlined.CalendarToday,
            label = stringResource(id = R.string.calendar)
        ),
        Screen.Timetable to IconGroup(
            filledIcon = Icons.Filled.EditCalendar,
            outlineIcon = Icons.Outlined.EditCalendar,
            label = stringResource(id = R.string.calendar)
        ),
        Screen.Exercises to IconGroup(
            filledIcon = Icons.Filled.EditNote,
            outlineIcon = Icons.Outlined.EditNote,
            label = stringResource(id = R.string.editcalendar)
        )
    )
    // the navigation bar objectt
    NavigationBar (
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        modifier = Modifier
            .height(58.dp)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        // for each screen present in the nav bar create an item
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val labelText = icons[screen]!!.label
            NavigationBarItem(modifier = Modifier.fillMaxSize()
                ,
                icon = {
                    Icon(
                        imageVector = (if (isSelected)
                            icons[screen]!!.filledIcon
                        else
                            icons[screen]!!.outlineIcon),
                        contentDescription = labelText,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    if (currentDestination != null) {
                        if (screen.route != currentDestination.route){
                            navClick()
                        }
                    }
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavPreview() {
    MainPageNavigationBar(navClick = {}, navController = rememberNavController())
}