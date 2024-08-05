package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The kotlin file dedicated to holding all the different types of
 * custom text objects for reuse throughout the code.
 *
 * Different parameters, sizes, colours
 */

@Composable
fun CustomTitleText(content: String, modifier: Modifier = Modifier, size: TextUnit) {
    Text(
        text = content,
        fontSize = size,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TableTitle(content: String, modifier: Modifier = Modifier, color: Color){
    Text(
        color = color,
        fontWeight = FontWeight.Bold,
        text = content,
        fontSize = 24.sp,
        modifier = modifier
            .padding(start = 10.dp)
    )
}

@Composable
fun TimeText(content: String, modifier: Modifier = Modifier, color: Color){
    Text(
        color = color,
        textAlign = TextAlign.End,
        fontWeight = FontWeight.W300,
        text = content,
        fontSize = 19.sp,
        modifier = Modifier
            .padding(end = 15.dp)
            .padding(start = 7.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TableText(content: String, modifier: Modifier = Modifier, color: Color){
    Text(
        color = color,
        fontWeight = FontWeight.W300,
        text = content,
        fontSize = 19.sp,
        modifier = modifier
            .padding(end = 7.dp)
            .padding(start = 7.dp)
    )
}

@Composable
fun BoldTableText(content: String, modifier: Modifier = Modifier, color: Color){
    Text(
        softWrap = true,
        color = color,
        fontWeight = FontWeight.W400,
        text = content,
        fontSize = 21.sp,
        modifier = modifier
            .padding(end = 10.dp)

    )
}

@Composable
fun AboutText(content: String, modifier: Modifier = Modifier){
    Text(
        softWrap = true,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        text = content,
        fontSize = 12.sp,
        modifier = modifier
            .padding(end = 10.dp)

    )
}

@Composable
fun WelcomeText(content: String, modifier: Modifier = Modifier){
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = content,
        fontSize = 16.sp,
        modifier = modifier
    )
}

@Composable
fun ProgressText(content: String, modifier: Modifier = Modifier){
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        text = content,
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Composable
fun LabelText(content: String, modifier: Modifier = Modifier){
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = content,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        modifier = modifier
    )
}