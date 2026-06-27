package com.example.dditpgrupal.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Course
import com.example.dditpgrupal.data.Note
import com.example.dditpgrupal.data.Practice
import com.example.dditpgrupal.data.dummyCourseList
import com.example.dditpgrupal.ui.screens.ClassModuleListScreen
import com.example.dditpgrupal.ui.screens.NotepadScreen
import com.example.dditpgrupal.ui.screens.NoteCreationScreen
import com.example.dditpgrupal.ui.screens.PracticeListScreen
import com.example.dditpgrupal.ui.screens.PracticeStatusScreen
import com.example.dditpgrupal.ui.screens.PracticeSubmitScreen
import com.example.dditpgrupal.ui.screens.ScheduleScreen

private val tabs = listOf("Cronograma", "Material", "Práctica", "Bloc de notas", "Info")

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
enum class ScreenView { MENU_PRINCIPAL, ENVIAR_PRACTICA, CREAR_NOTA, VER_ESTADO_PRACTICA }

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
fun CourseMenu(
    course: Course,
    onBackClick: () -> Unit = {},
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var currentScreen by remember { mutableStateOf(ScreenView.MENU_PRINCIPAL) }
    var selectedPractice by remember { mutableStateOf<Practice?>(null) }
    val notes = remember { mutableStateListOf<Note>().also { it.addAll(course.courseNotes) } }

    if (currentScreen == ScreenView.VER_ESTADO_PRACTICA && selectedPractice != null) {
        PracticeStatusScreen(
            practice = selectedPractice!!,
            onBackClick = { currentScreen = ScreenView.MENU_PRINCIPAL },
        )
        return
    }

    if (currentScreen == ScreenView.ENVIAR_PRACTICA) {
        PracticeSubmitScreen(
            onBackClick = { currentScreen = ScreenView.MENU_PRINCIPAL },
            onSaveDraft = { _ -> },
            onSend = { _, _ -> currentScreen = ScreenView.MENU_PRINCIPAL },
        )
        return
    }

    if (currentScreen == ScreenView.CREAR_NOTA) {
        NoteCreationScreen(
            onBackClick = { currentScreen = ScreenView.MENU_PRINCIPAL },
            onSave = { text, isImportant ->
                notes.add(Note(text, text, isImportant, java.time.LocalDate.now()))
                currentScreen = ScreenView.MENU_PRINCIPAL
            },
        )
        return
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = course.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
            )
        }

        SecondaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color =
                                if (selectedTabIndex == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                        )
                    },
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                ScheduleScreen(course)
            }

            1 -> {
                ClassModuleListScreen()
            }

            2 -> {
                PracticeListScreen(
                    onPracticeSubmit = { practice ->
                        selectedPractice = practice
                        currentScreen = ScreenView.ENVIAR_PRACTICA
                    },
                    onPracticeViewStatus = { practice ->
                        selectedPractice = practice
                        currentScreen = ScreenView.VER_ESTADO_PRACTICA
                    },
                )
            }

            3 -> {
                NotepadScreen(
                    notes = notes,
                    onAddNote = { currentScreen = ScreenView.CREAR_NOTA },
                    onDeleteNote = { note -> notes.remove(note) },
                )
            }

            4 -> {
                CourseDetails(course)
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CourseMenuPreview() {
    CourseMenu(dummyCourseList.first())
}
