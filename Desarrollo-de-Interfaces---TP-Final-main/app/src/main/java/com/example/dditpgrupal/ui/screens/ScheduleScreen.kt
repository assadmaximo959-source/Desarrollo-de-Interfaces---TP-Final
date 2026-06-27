package com.example.dditpgrupal.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Course
import com.example.dditpgrupal.data.dummyCourseList
import com.example.dditpgrupal.ui.components.CalendarAlertCard
import com.example.dditpgrupal.ui.components.CourseMenu
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
fun ScheduleScreen(course: Course) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CalendarAlertCard()

        var expandedItems by remember { mutableStateOf(setOf<Int>()) }
        val allExpanded = expandedItems.size == course.importantDates.size

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Calendario académico",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                    )
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

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                Spacer(modifier = Modifier.height(8.dp))

                val referenceDate = LocalDate.now()

                course.importantDates.forEachIndexed { index, date ->
                    val sampleActivities = listOf(
                        "Parcial 1" to Icons.AutoMirrored.Filled.Assignment,
                        "Entrega TP" to Icons.Default.Description,
                        "Recuperatorio" to Icons.Default.Edit,
                        "Trabajo Final" to Icons.Default.CheckCircle,
                    )
                    val sampleTopics = listOf(
                        listOf("Unidad 1: Introducción", "Unidad 2: Conceptos avanzados", "Unidad 3: Práctica integradora"),
                        listOf("Consigna", "Formato de entrega", "Fecha límite", "Rúbrica de evaluación"),
                        listOf("Temas a recuperar"),
                        listOf("Definición del proyecto", "Planificación", "Implementación", "Pruebas", "Documentación", "Presentación final"),
                    )

                    val (activityName, icon) = sampleActivities[index % sampleActivities.size]
                    val topics = sampleTopics[index % sampleTopics.size]
                    val isExpanded = index in expandedItems
                    val daysUntil = ChronoUnit.DAYS.between(referenceDate, date)
                    val isPast = daysUntil < 0
                    val isSoon = daysUntil in 0..7
                    val pastAlpha = if (isPast) 0.38f else 1f

                    Column(
                        modifier = Modifier.animateContentSize(),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        when {
                                            isPast -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                            isSoon -> MaterialTheme.colorScheme.error
                                            else -> MaterialTheme.colorScheme.tertiary
                                        },
                                        CircleShape,
                                    ),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isPast) {
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                                } else {
                                    MaterialTheme.colorScheme.tertiary
                                },
                                modifier = Modifier.size(22.dp),
                            )
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = activityName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = pastAlpha),
                                )
                                Text(
                                    text = "${date.dayOfMonth}/${date.monthValue}/${date.year}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha),
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column(horizontalAlignment = Alignment.End) {
                                if (!isPast) {
                                    Text(
                                        text = if (daysUntil == 0L) "Hoy" else "-$daysUntil d\u00edas",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSoon) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
                                    )
                                }
                            }
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                            .size(4.dp)
                                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha), CircleShape),
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = topic,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = pastAlpha),
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (index < course.importantDates.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 6.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
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
