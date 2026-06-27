package com.example.dditpgrupal.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.ClassModule
import com.example.dditpgrupal.data.dummyModuleList

@Suppress("ktlint:standard:function-naming")
@Composable
fun ClassModuleCard(
    module: ClassModule,
    isExpanded: Boolean = false,
    onToggle: () -> Unit = {},
    onInfoClick: () -> Unit = {},
) {
    var showDownloadDialog by remember { mutableStateOf(false) }
    var downloadChecked by remember { mutableStateOf(false) }

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onToggle() }
                        .padding(start = 12.dp, end = 4.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = downloadChecked,
                    onCheckedChange = { downloadChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                    ),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = module.moduleName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Información",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if (isExpanded) "Contraer" else "Expandir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.rotate(if (isExpanded) 180f else 0f),
                )
            }

            if (isExpanded) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    module.content.forEach { material ->
                        StudyMaterialCard(
                            material = material,
                            elevation = 0.dp,
                            containerColor = Color.Transparent,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        IconButton(onClick = { showDownloadDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Descargar unidad completa",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
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
                    text = "Descargar unidad completa",
                    fontWeight = FontWeight.SemiBold,
                )
            },
            text = {
                Text(
                    text = "¿Deseas descargar todos los archivos de \"${module.moduleName}\"? (${module.content.size} archivos)",
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDownloadDialog = false
                }) {
                    Text("Descargar todo")
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
private fun ClassModuleCardPreview() {
    ClassModuleCard(module = dummyModuleList.first())
}
