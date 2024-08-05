package com.example.gymapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

/**
 * My main top level scaffold funciton which i use for every screen
 * Contains: screen content, bottom app bar, top app bar, FAB, snackbar
 */

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun TopLevelScaffold(
    snackbarContent: @Composable (SnackbarData) -> Unit = {},
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState? = null,
    onClick: () -> Unit,
    navController: NavHostController,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {},
    topBar: @Composable () -> Unit = { },
    floatingActionButton: @Composable () -> Unit = { },
    bottomAppBar: Boolean
) {
        Scaffold(
            snackbarHost = {
                snackbarHostState?.let {
                    SnackbarHost(hostState = snackbarHostState)
                    { data -> snackbarContent(data) }
                }
            },
            topBar = topBar,
            bottomBar = {
                if (bottomAppBar){
                    MainPageNavigationBar(onClick, navController)
                }
            },
            floatingActionButton = floatingActionButton,

            content = { innerPadding ->
                pageContent(innerPadding)
            }
        )


}
