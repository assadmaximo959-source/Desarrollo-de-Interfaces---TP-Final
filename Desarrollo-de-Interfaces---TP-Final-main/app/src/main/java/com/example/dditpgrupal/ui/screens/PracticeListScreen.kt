package com.example.dditpgrupal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dditpgrupal.data.Practice
import com.example.dditpgrupal.data.dummyPracticeList
import com.example.dditpgrupal.data.enums.PracticeStatus
import com.example.dditpgrupal.ui.components.PracticeCard

@Suppress("ktlint:standard:function-naming")
@Composable
fun PracticeListScreen(
    practiceList: List<Practice> = dummyPracticeList,
    onPracticeSubmit: (Practice) -> Unit = {},
    onPracticeViewStatus: (Practice) -> Unit = {},
) {
    var selectedStatus by remember { mutableStateOf<PracticeStatus?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val filteredList = if (selectedStatus == null) practiceList else practiceList.filter { it.status == selectedStatus }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (selectedStatus == null) "Todas las prácticas" else "Filtrado por: ${selectedStatus!!.name}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
            )

            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "Filtrar",
                    tint = if (selectedStatus != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Todas") },
                    onClick = {
                        selectedStatus = null
                        expanded = false
                    },
                )
                PracticeStatus.entries.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status.name) },
                        onClick = {
                            selectedStatus = status
                            expanded = false
                        },
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(filteredList, key = { it.name }) { practice ->
                PracticeCard(
                    practice = practice,
                    onSubmitClick = { onPracticeSubmit(practice) },
                    onViewStatusClick = { onPracticeViewStatus(practice) },
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PracticeListScreenPreview() {
    PracticeListScreen(practiceList = dummyPracticeList)
}
