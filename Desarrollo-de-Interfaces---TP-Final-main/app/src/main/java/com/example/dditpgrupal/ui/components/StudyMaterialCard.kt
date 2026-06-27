package com.example.dditpgrupal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.StudyMaterial

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudyMaterialCard(
    material: StudyMaterial,
    onDownloadClick: () -> Unit = {},
    elevation: Dp = 4.dp,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    var showDownloadDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = material.icon,
                contentDescription = material.type,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = material.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = material.type,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            IconButton(onClick = { showDownloadDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Descargar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }

    if (showDownloadDialog) {
        AlertDialog(
            onDismissRequest = { showDownloadDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            title = {
                Text(
                    text = "Descargar archivo",
                    fontWeight = FontWeight.SemiBold,
                )
            },
            text = {
                Text(
                    text = "¿Deseas descargar \"${material.name}\" (${material.type})?",
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDownloadDialog = false
                    onDownloadClick()
                }) {
                    Text("Descargar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDownloadDialog = false }) {
                    Text("Cancelar")
                }
            },
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun StudyMaterialCardPreview() {
    StudyMaterialCard(
        material = StudyMaterial("Guía de estudio", "PDF", Icons.Default.Description),
    )
}
