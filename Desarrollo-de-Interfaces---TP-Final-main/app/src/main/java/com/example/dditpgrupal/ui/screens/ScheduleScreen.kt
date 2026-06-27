package com.example.dditpgrupal.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Course
import com.example.dditpgrupal.data.dummyCourseList
import com.example.dditpgrupal.ui.components.CalendarAlertCard
import com.example.dditpgrupal.ui.components.CourseMenu
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
fun ScheduleScreen(course: Course) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CalendarAlertCard()

        var expandedItems by remember { mutableStateOf(setOf<Int>()) }
        val allExpanded = expandedItems.size == course.importantDates.size

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp),
            )
            Text(
                text = "Fechas",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 12.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                expandedItems = if (allExpanded) emptySet() else course.importantDates.indices.toSet()
            }) {
                Icon(
                    imageVector = if (allExpanded) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                    contentDescription = if (allExpanded) "Contraer todo" else "Expandir todo",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
        }

        val sampleActivities =
            listOf(
                "Parcial 1" to Icons.AutoMirrored.Filled.Assignment,
                "Entrega TP" to Icons.Default.Description,
                "Recuperatorio" to Icons.Default.Edit,
                "Trabajo Final" to Icons.Default.CheckCircle,
            )

        val sampleTopics =
            listOf(
                listOf("Unidad 1: Introducción", "Unidad 2: Conceptos avanzados", "Unidad 3: Práctica integradora"),
                listOf("Consigna", "Formato de entrega", "Fecha límite", "Rúbrica de evaluación"),
                listOf("Temas a recuperar"),
                listOf("Definición del proyecto", "Planificación", "Implementación", "Pruebas", "Documentación", "Presentación final"),
            )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val referenceDate = LocalDate.of(2026, 6, 10)

                course.importantDates.forEachIndexed { index, date ->
                    val (activityName, icon) = sampleActivities[index % sampleActivities.size]
                    val topics = sampleTopics[index % sampleTopics.size]
                    val isExpanded = index in expandedItems
                    val isPast = date.isBefore(referenceDate)
                    val pastAlpha = if (isPast) 0.38f else 1f

                    Column(
                        modifier = Modifier.animateContentSize(),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint =
                                    if (isPast) {
                                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                                    } else {
                                        @Suppress("ktlint:standard:max-line-length")
                                        MaterialTheme.colorScheme.tertiary
                                    },
                                modifier = Modifier.size(24.dp),
                            )
                            Text(
                                text = activityName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = pastAlpha),
                                modifier = Modifier.padding(start = 12.dp, end = 8.dp),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${date.dayOfMonth}/${date.monthValue}/${date.year}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha),
                            )
                            IconButton(onClick = {
                                expandedItems = if (isExpanded) expandedItems - index else expandedItems + index
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = if (isExpanded) "Contraer" else "Expandir",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha),
                                    modifier = Modifier.rotate(if (isExpanded) 180f else 0f),
                                )
                            }
                        }

                        if (isExpanded) {
                            Column(
                                modifier = Modifier.padding(start = 36.dp, top = 8.dp, bottom = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                topics.forEach { topic ->
                                    Text(
                                        text = topic,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha),
                                    )
                                }
                            }
                        }
                    }

                    if (index < course.importantDates.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = MaterialTheme.colorScheme.outline,
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun CourseMenuPreview() {
    CourseMenu(course = dummyCourseList.first())
}
