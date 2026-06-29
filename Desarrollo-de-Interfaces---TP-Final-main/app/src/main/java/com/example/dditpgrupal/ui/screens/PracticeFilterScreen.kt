package com.example.dditpgrupal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Practice
import com.example.dditpgrupal.data.dummyPracticeList
import com.example.dditpgrupal.data.enums.PracticeStatus
import com.example.dditpgrupal.ui.components.PracticeCard

@Suppress("ktlint:standard:function-naming")
@Composable
fun PracticeFilterScreen(
    initialFilter: PracticeStatus = PracticeStatus.PENDIENTE,
    practices: List<Practice> = dummyPracticeList,
    onBackClick: () -> Unit = {},
    onPracticeClick: (Practice) -> Unit = {},
) {
    var selectedPractice by remember { mutableStateOf<Practice?>(null) }
    var selectedFilter by remember { mutableIntStateOf(if (initialFilter == PracticeStatus.PENDIENTE) 0 else 1) }
    val filters = listOf(
        PracticeStatus.PENDIENTE,
        PracticeStatus.ENTREGADA,
        PracticeStatus.CORREGIDA,
        PracticeStatus.SOLICITADA,
        PracticeStatus.ACEPTADA,
        PracticeStatus.RECHAZADA,
        PracticeStatus.REVISION,
    )
    val filterGroups = listOf(
        "Pendientes" to listOf(PracticeStatus.PENDIENTE, PracticeStatus.ENTREGADA),
        "Corregidas" to listOf(PracticeStatus.CORREGIDA, PracticeStatus.ACEPTADA),
        "Revisión" to listOf(PracticeStatus.SOLICITADA, PracticeStatus.RECHAZADA, PracticeStatus.REVISION),
    )
    val filteredPractices = practices.filter { it.status in filterGroups[selectedFilter].second }
    val counts = filterGroups.map { (_, statuses) -> practices.count { it.status in statuses } }

    selectedPractice?.let { practice ->
        PracticeStatusScreen(
            practice = practice,
            onBackClick = { selectedPractice = null },
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = "Prácticas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            filterGroups.forEachIndexed { index, (title, _) ->
                FilterButton(
                    icon = when (index) {
                        0 -> Icons.Default.Schedule
                        1 -> Icons.Default.CheckCircle
                        else -> Icons.Default.RateReview
                    },
                    title = title,
                    count = counts[index],
                    isSelected = selectedFilter == index,
                    onClick = { selectedFilter = index },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredPractices.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = when (selectedFilter) {
                        0 -> Icons.Default.Schedule
                        1 -> Icons.Default.Star
                        else -> Icons.Default.RateReview
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(64.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = when (selectedFilter) {
                        0 -> "No hay prácticas pendientes"
                        1 -> "No hay prácticas corregidas"
                        else -> "No hay prácticas en revisión"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(filteredPractices, key = { it.name }) { practice ->
                    PracticeCard(
                        practice = practice,
                        onSubmitClick = { selectedPractice = practice },
                        onViewStatusClick = { selectedPractice = practice },
                    )
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun FilterButton(
    icon: ImageVector,
    title: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant,
        ),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$title ($count)",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PracticeFilterScreenPreview() {
    PracticeFilterScreen()
}
