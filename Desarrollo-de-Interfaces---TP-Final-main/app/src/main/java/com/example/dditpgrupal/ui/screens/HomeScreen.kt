package com.example.dditpgrupal.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.example.dditpgrupal.data.dummyPracticeList
import com.example.dditpgrupal.data.enums.PracticeStatus

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onCoursesClick: () -> Unit = {},
    onPendingClick: () -> Unit = {},
    onCorrectedClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(26.dp),
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Estudiante",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            TextButton(
                onClick = onLogout,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Salir",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SystemStatusCards(
            onCoursesClick = onCoursesClick,
            onPendingClick = onPendingClick,
            onCorrectedClick = onCorrectedClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        UpcomingEventsSection()

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SystemStatusCards(
    onCoursesClick: () -> Unit,
    onPendingClick: () -> Unit,
    onCorrectedClick: () -> Unit,
) {
    val pendingCount = dummyPracticeList.count { it.status in listOf(PracticeStatus.PENDIENTE, PracticeStatus.ENTREGADA) }
    val correctedCount = dummyPracticeList.count { it.status in listOf(PracticeStatus.CORREGIDA, PracticeStatus.ACEPTADA) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StatusCard(
            icon = Icons.Default.School,
            title = "Materias",
            value = "4",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
            onClick = onCoursesClick,
        )
        StatusCard(
            icon = Icons.Default.Schedule,
            title = "Pendientes",
            value = "$pendingCount",
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f),
            onClick = onPendingClick,
        )
        StatusCard(
            icon = Icons.Default.Star,
            title = "Corregidas",
            value = "$correctedCount",
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f),
            onClick = onCorrectedClick,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun StatusCard(
    icon: ImageVector,
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(26.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color,
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
private fun UpcomingEventsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
            text = "Próximos eventos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

    Spacer(modifier = Modifier.height(14.dp))

    val events = listOf(
        EventItem("Entrega TP Final", LocalDate.of(2026, 7, 1), EventType.EXAMEN),
        EventItem("Parcial - Unidad 2", LocalDate.of(2026, 7, 13), EventType.PARCIAL),
        EventItem("Actividad en clase", LocalDate.of(2026, 6, 30), EventType.TAREA),
        EventItem("Recuperatorio", LocalDate.of(2026, 7, 20), EventType.EXAMEN),
    )

    events.forEach { event ->
        EventCard(event = event)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

private enum class EventType { PARCIAL, EXAMEN, TAREA }

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("ktlint:standard:function-naming")
@Composable
private fun EventCard(event: EventItem) {
    val daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), event.date)
    val (color, label) = when (event.type) {
        EventType.EXAMEN -> MaterialTheme.colorScheme.error to "Examen"
        EventType.PARCIAL -> MaterialTheme.colorScheme.tertiary to "Parcial"
        EventType.TAREA -> MaterialTheme.colorScheme.primary to "Tarea"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                        .size(48.dp)
                        .background(color.copy(alpha = 0.15f), RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = when (event.type) {
                        EventType.EXAMEN -> Icons.Default.Edit
                        EventType.PARCIAL -> Icons.Default.Description
                        EventType.TAREA -> Icons.Default.CheckCircle
                    },
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(26.dp),
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                        .size(8.dp)
                        .background(color, CircleShape),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = color,
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${event.date.dayOfMonth}/${event.date.monthValue}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (daysUntil >= 0) {
                    Text(
                        text = if (daysUntil == 0L) "Hoy" else "Faltan $daysUntil d\u00edas",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (daysUntil <= 3) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private data class EventItem(
    val title: String,
    val date: LocalDate,
    val type: EventType,
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
