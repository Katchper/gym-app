package com.example.gymapp.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Simple data class for holding the icons filled
 * and outline state with the content label
 */
data class IconGroup(
    val filledIcon: ImageVector,
    val outlineIcon: ImageVector,
    val label: String
)