package com.example.dditpgrupal.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Practice
import com.example.dditpgrupal.data.dummyPracticeList
import com.example.dditpgrupal.data.enums.PracticeStatus

private fun PracticeStatus.icon(): ImageVector =
    when (this) {
        PracticeStatus.PENDIENTE -> Icons.Default.Schedule
        PracticeStatus.ENTREGADA -> Icons.AutoMirrored.Filled.Assignment
        PracticeStatus.CORREGIDA -> Icons.Default.CheckCircle
    }

@Composable
private fun PracticeStatus.statusColor(): Color =
    when (this) {
        PracticeStatus.PENDIENTE -> MaterialTheme.colorScheme.error
        PracticeStatus.ENTREGADA -> MaterialTheme.colorScheme.primary
        PracticeStatus.CORREGIDA -> MaterialTheme.colorScheme.tertiary
    }

@Suppress("ktlint:standard:function-naming")
@Composable
fun PracticeCard(
    practice: Practice,
    onSubmitClick: () -> Unit = {},
    onViewStatusClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = practice.status.icon(),
                contentDescription = practice.status.name,
                tint = practice.status.statusColor(),
                modifier = Modifier.size(48.dp),
            )

            Text(
                text = practice.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(start = 12.dp),
            )

            IconButton(
                onClick = {
                    if (practice.status == PracticeStatus.PENDIENTE) onSubmitClick() else onViewStatusClick()
                },
                modifier = Modifier.padding(start = 16.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ver detalle",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PracticeCardPreview() {
    PracticeCard(practice = dummyPracticeList[1])
}
