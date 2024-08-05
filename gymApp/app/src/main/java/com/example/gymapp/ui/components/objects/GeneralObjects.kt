package com.example.gymapp.ui.components.objects

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.gymapp.R
import com.example.gymapp.ui.theme.GymAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Number text field composable, takes in content as a string, a label
 * and when the value ion the field changes returns the value
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberTextField(value: String, modifier: Modifier = Modifier, label : String, text: (String) -> Unit){
    val textValue = rememberSaveable{ mutableStateOf(value) }
    Column{
        Box(modifier = modifier
            .shadow(elevation = 15.dp, shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)){
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                ),
                textStyle = androidx.compose.ui.text.TextStyle.Companion.Default.copy(fontSize = 28.sp),
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
                value = textValue.value,
                onValueChange = {
                    // check if the box is empty or is only digits and less than
                    // or equal to 4 characters in length.
                    if (it.isEmpty() || it.isDigitsOnly() && it.length <= 4) {
                        textValue.value = it
                        if (it.isEmpty()){
                            text("0")
                        } else {
                            text(textValue.value)
                        }

                    }
                },
                singleLine = true,
                // number pad keyboard
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        LabelText(content = label, modifier = Modifier
            .padding(start = 5.dp)
            .align(Alignment.Start))
    }
}

/**
 * Text input field composable, takes in a label and any existing text
 * returns the contents of the input box when value changes
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(modifier: Modifier = Modifier, label: String, existingName: String, text: (String) -> Unit) {
    val textValue = rememberSaveable{ mutableStateOf(existingName) }

    Column {
        LabelText(content = label, modifier = Modifier
            .padding(start = 5.dp)
            .align(Alignment.Start))
        Box(modifier = modifier
            .shadow(elevation = 15.dp, shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)){
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            textStyle = androidx.compose.ui.text.TextStyle.Companion.Default.copy(fontSize = 16.sp),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            value = textValue.value,
            onValueChange = { textValue.value = it
                text(textValue.value)
            },
            singleLine = true,
        )
        }

    }
}

/**
 * Edit button composable
 */
@Composable
fun EditButton(modifier: Modifier = Modifier, buttonColor: Color, iconColor: Color, content: String, id: ImageVector, onClick: () -> Unit){
    OutlinedButton(
        border = BorderStroke(0.dp, Color.Unspecified),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = iconColor,
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(end = 5.dp)
            .padding(top = 5.dp)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp)
            )) {
        Icon(
            id,
            contentDescription = content,
            tint = iconColor,
        )
    }
}

/**
 * Close button composable
 */

@Composable
fun CloseButton(modifier: Modifier = Modifier, iconColor: Color, content: String, onClick: () -> Unit){
    IconButton(onClick = onClick,
        modifier = modifier.padding(5.dp)) {
        Icon(
            Icons.Filled.Close,
            modifier = modifier.padding(5.dp),
            contentDescription = content,
            tint = iconColor
        )
    }
}

/**
 * Custom linear progress bar, takes in current progress as a float
 * uses a class method to get the screen width and multiply it by 0.8x
 */
@Composable
fun CustomLinearProgressBar(modifier: Modifier = Modifier, currentProgress: Float){
    val size = ScreenSize()
    Column(modifier) {
        LinearProgressIndicator(
            progress = currentProgress,
            modifier = Modifier
                .width((size.width() * 0.8).dp)
                .height(5.dp),
            trackColor = MaterialTheme.colorScheme.primaryContainer,
            color = MaterialTheme.colorScheme.primary//progress color
        )
    }
}

/**
 * Custom checkbox used only in the table on the home screen
 * contains an id and returns its check state as well its id on change
 */
@Composable
fun CustomCheckBox(id: Int, progressValue: (Int, Boolean) -> Unit){
    var checked by rememberSaveable { mutableStateOf(false) }
    var boxid by rememberSaveable { mutableIntStateOf(id) }
    Checkbox(
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            uncheckedColor = MaterialTheme.colorScheme.primary,
            checkmarkColor = MaterialTheme.colorScheme.onPrimary
        ),
        checked = checked,
        onCheckedChange = {
            checked = it
            progressValue(boxid, checked)
        }
    )
}

/**
 * Custom check box, contains text with a checkbox next to it
 * organised together into a row.
 * Returns its checked state on change
 */
@Composable
fun CustomCheckBoxWithLabel(checked: Boolean, label: String, progressValue: (Boolean) -> Unit){
    var checked by rememberSaveable { mutableStateOf(checked) }
    Row (modifier = Modifier.fillMaxHeight()){
        LabelText(content = label, modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(start = 10.dp))
        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                uncheckedColor = MaterialTheme.colorScheme.secondary,
                checkmarkColor = MaterialTheme.colorScheme.onSecondary
            ),
            checked = checked,
            onCheckedChange = {
                checked = it
                progressValue(checked)
            }
        )
    }
}

/**
 * Custom divider composable, made so i dont have to
 * redefine the padding everytime i want a divider
 */
@Composable
fun CustomDivider(modifier: Modifier = Modifier, color: Color){
    Divider(thickness = 1.dp, color = color, modifier = modifier
        .padding(start = 10.dp)
        .padding(end = 10.dp))
}

/**
 * Custom image composable
 * displays an image and gives it a tint
 */
@Composable
fun CustomImage(modifier: Modifier = Modifier, content: String, id: Int, color: Color){
    Image(
        painter = painterResource(id = id),
        contentDescription = content,
        modifier = modifier,
        colorFilter = ColorFilter.tint(color = color)

    )
}

/**
 * Regular image composable
 * Was made becuase i dont know how to make image normal colour
 * without if statements
 */
@Composable
fun CustomImageReg(modifier: Modifier = Modifier, content: String, id: Int){
    Image(
        painter = painterResource(id = id),
        contentDescription = content,
        modifier = modifier
    )
}

/**
 * This is a dropdown menu which handles the exercise icons, it has a
 * hardcoded list of names and icons it uses for the menu items. It returns
 * the icon ID as well as the name associated
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuSearch(selectedIcon : Int, menuValues: (Int, String) -> Unit){
    // hard coded icons
    val icons = listOf(R.drawable.icons8_dumbbell, R.drawable.icons8_battle_ropes, R.drawable.icons8_bench_press, R.drawable.icons8_squats, R.drawable.icons8_cable, R.drawable.icons8_curls, R.drawable.icons8_plank, R.drawable.icons8_deadlift, R.drawable.icons8_pushups, R.drawable.icons8_running)
    val names = listOf("Dumbbell", "Ropes", "Bench Press", "Squats", "Cables", "Curls", "Plank", "Deadlift", "Pushups", "Running")

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(names[icons.indexOf(selectedIcon)]) }

    var selectedIcon by remember { mutableIntStateOf(selectedIcon) }

    val shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
    else RoundedCornerShape(8.dp)

    ExposedDropdownMenuBox(
        modifier = Modifier.width(200.dp),
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}) {

        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            textStyle = androidx.compose.ui.text.TextStyle.Default.copy(fontSize = 14.sp,
                fontWeight=  FontWeight.Light),
            readOnly = true,
            value = selectedOptionText,
            label = { Text("Select Icon", fontWeight = FontWeight.Bold) },
            leadingIcon = { CustomImage(content = selectedOptionText, id = selectedIcon, color = Black)},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            onValueChange = {},
            shape = shape,

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            names.forEach { currentName ->
                DropdownMenuItem(
                    leadingIcon = { CustomImage(content = selectedOptionText, id = icons[names.indexOf(currentName)], color = Black)},
                    text = { Text(currentName) },
                    onClick = {
                        selectedOptionText = currentName
                        selectedIcon = icons[names.indexOf(currentName)]
                        expanded = false
                        menuValues(selectedIcon, selectedOptionText)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/**
 * This is a custom box which will be used to display the selected muscle groups
 * within add/edit day, it contains the icon + text + a remove button at the end
 */
@Composable
fun CustomViewBox(image : Int, text : String, modifier: Modifier, onClick: () -> Unit){
    Box (modifier = modifier
        .shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(14.dp)
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)){
        Row {
            Spacer(modifier = Modifier.width(10.dp))
            CustomImageReg(content = "text", id = image, modifier = Modifier
                .align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.weight(1f))
            TableTitle(content = text, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier
                .align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(50.dp),
                onClick = onClick
            ) {
                Icon(
                    Icons.Filled.Close, "close",
                    modifier = Modifier.fillMaxHeight(),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer)

            }
        }
    }
}


/**
 * Custom drop down for exercises which takes in a list of idValues, icons and names
 * this will be used to return the correct information back to where it's called from
 *
 * this drop down will display the icons in the dropdown menu items along with the name
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuExercises(idValues: List<Int>, icons: List<Int>, names: List<String>, menuValues: (Int) -> Unit){
    // default values
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableIntStateOf(0) }
    var selectedID by remember { mutableStateOf(0) }

    val shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
    else RoundedCornerShape(8.dp)

    ExposedDropdownMenuBox(
        modifier = Modifier.width(200.dp),
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}) {

        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            textStyle = androidx.compose.ui.text.TextStyle.Default.copy(fontSize = 14.sp,
                fontWeight=  FontWeight.Light),
            readOnly = true,
            value = "Add Exercise",
            label = { Text("Select Exercises", fontWeight = FontWeight.Bold) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            onValueChange = {},
            shape = shape,
            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            var id = 0
            names.forEach { currentName ->
                // each item has an id variable  (so it can return the corresponding item)
                var thisID = id
                id++
                DropdownMenuItem(
                    leadingIcon = { CustomImage(content = currentName, id = icons[names.indexOf(currentName)], color = Black)},
                    text = { Text(currentName) },
                    onClick = {
                        selectedOptionText = currentName
                        selectedIcon = icons[thisID]
                        selectedID = idValues[thisID]
                        expanded = false
                        // return the id of the exercise
                        menuValues(selectedID)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/**
 * drop down menu for the muscle groups, the icons and names are hardcoded
 * this can be changed in the future to get the list from a function to reduce
 * on screen space
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuMuscles(menuValues: (Int, String) -> Unit){
    // hard coded icons
    val icons = listOf(R.drawable.icons8_biceps, R.drawable.icons8_quadriceps, R.drawable.icons8_abs, R.drawable.icons8_back_extensors, R.drawable.icons8_bodybuilder, R.drawable.icons8_chest, R.drawable.icons8_calves, R.drawable.icons8_hamstrings, R.drawable.icons8_forearm, R.drawable.icons8_shoulders, R.drawable.icons8_trapezius, R.drawable.icons8_triceps)
    val names = listOf("Biceps","Quadriceps","Abs","Middle Back", "Lower Back", "Chest", "Calves", "Hamstrings", "Forearm", "Shoulders", "Traps", "Triceps")

    var expanded by remember { mutableStateOf(false) }
    // by default the first option in the list which is the biceps
    var selectedOptionText by remember { mutableStateOf(names[0]) }
    var selectedIcon by remember { mutableIntStateOf(icons[0]) }

    val shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
    else RoundedCornerShape(8.dp)

    ExposedDropdownMenuBox(
        modifier = Modifier.width(200.dp),
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}) {

        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            textStyle = androidx.compose.ui.text.TextStyle.Default.copy(fontSize = 14.sp,
                fontWeight=  FontWeight.Light),
            readOnly = true,
            value = "Add a muscle",
            label = { Text("Select Muscle groups", fontWeight = FontWeight.Bold) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            onValueChange = {},
            shape = shape,

            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            names.forEach { currentName ->
                DropdownMenuItem(
                    leadingIcon = { CustomImage(content = selectedOptionText, id = icons[names.indexOf(currentName)], color = Black)},
                    text = { Text(currentName) },
                    onClick = {
                        // get the name of the muscle, find its index in the original list and
                        // return the icon at the same index with the name
                        selectedOptionText = currentName
                        selectedIcon = icons[names.indexOf(currentName)]
                        expanded = false
                        // return the muscle name and icon
                        menuValues(selectedIcon, selectedOptionText)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}


/**
 * Drop down menu for days, it takes in available days
 * and the currently selected day and returns a day when a
 * day is chosen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuDay(selectedDay: String, availableDays : MutableList<String>, dateSelected: (String) -> Unit){
    val days = availableDays
    var expanded by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf(selectedDay) }

    val shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
    else RoundedCornerShape(8.dp)

    ExposedDropdownMenuBox(
        modifier = Modifier.width(200.dp),
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}) {

        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            textStyle = androidx.compose.ui.text.TextStyle.Default.copy(fontSize = 14.sp,
                fontWeight=  FontWeight.Light),
            readOnly = true,
            value = selectedDay,
            label = { Text("Select Day", fontWeight = FontWeight.Bold) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            onValueChange = {},
            shape = shape,

            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            days.forEach { currentDay ->
                DropdownMenuItem(
                    text = { Text(currentDay) },
                    onClick = {
                        selectedDay = currentDay
                        expanded = false
                            // return the day selected
                        dateSelected(selectedDay)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

/**
 * Custom Delete button using a delay in a
 * coroutine to ensure it cant be double tapped
 */
@Composable
fun DeleteButton(text: String, modifier: Modifier, onClick: () -> Unit){
    var enabled: Boolean by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    OutlinedButton(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer,
            disabledContainerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = modifier,
        onClick = {
            if (enabled){
                enabled = false
                onClick()
                coroutineScope.launch {
                    delay(300)
                    enabled = true
                }
            }
        }
    ){
        BoldTableText(content = text, color = MaterialTheme.colorScheme.onErrorContainer)
    }
}

/**
 * Alert Dialog Box Template for use in important tasks which
 * are irrevesable like deletion or resetting
 */
@Composable
fun AlertDialogTemplate(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = dialogTitle)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

/**
 * Custom snackbar object with custom colours
 */
@Composable
fun SnackbarWithTimeout(data: SnackbarData,
                        containerCol: Color,
                        textCol: Color,
                        actionCol: Color,
                        modifier: Modifier = Modifier,
                        onDismiss: () -> Unit) {
    Snackbar(
        containerColor = containerCol,
        contentColor = textCol,
        actionContentColor = actionCol,
        modifier = modifier.padding(20.dp),
        content = {
            Text(text = data.visuals.message)
        },
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                TextButton(
                    onClick = {onDismiss()}) {
                    Text(text = actionLabel)
                }
            }
        }
    )
}


/**
 * Test Object Preview
 */
@Preview(showBackground = true)
@Composable
fun ObjectPreview() {
    GymAppTheme(dynamicColor = false) {
       TextInputField(modifier = Modifier
           .width(200.dp)
           .height(60.dp), label = "test", existingName = "test", text = {test ->})
    }
}