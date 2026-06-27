package com.example.dditpgrupal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.IconButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.dditpgrupal.data.ClassModule
import com.example.dditpgrupal.data.StudyMaterial
import com.example.dditpgrupal.data.dummyModuleList
import com.example.dditpgrupal.ui.components.ClassModuleCard

@Suppress("ktlint:standard:function-naming")
@Composable
fun ClassModuleListScreen(moduleList: List<ClassModule> = dummyModuleList) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<String?>(null) }
    var showingDropdown by remember { mutableStateOf(false) }
    var allExpanded by remember { mutableStateOf(false) }
    var expandedModules by remember { mutableStateOf(setOf<String>()) }

    val allTypes =
        remember(moduleList) {
            moduleList.flatMap { it.content.map { m -> m.type } }.distinct()
        }

    fun materialMatches(material: StudyMaterial): Boolean {
        val matchesSearch = searchQuery.isBlank() || material.name.contains(searchQuery, ignoreCase = true)
        val matchesType = selectedType == null || material.type == selectedType
        return matchesSearch && matchesType
    }

    val filteredModuleList =
        remember(moduleList, searchQuery, selectedType) {
            moduleList.mapNotNull { module ->
                val filteredContent = module.content.filter { materialMatches(it) }
                if (filteredContent.isNotEmpty()) module.copy(content = filteredContent) else null
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar archivo...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            singleLine = true,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilterChip(
                selected = selectedType != null,
                onClick = { showingDropdown = true },
                label = {
                    Text(
                        text = selectedType ?: "Tipo",
                        style = MaterialTheme.typography.labelLarge,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                },
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                allExpanded = !allExpanded
                expandedModules = if (allExpanded) moduleList.map { it.moduleName }.toSet() else emptySet()
            }) {
                Icon(
                    imageVector = if (allExpanded) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                    contentDescription = if (allExpanded) "Contraer todo" else "Expandir todo",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }

            DropdownMenu(
                expanded = showingDropdown,
                onDismissRequest = { showingDropdown = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Todos") },
                    onClick = {
                        selectedType = null
                        showingDropdown = false
                    },
                )
                allTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            selectedType = type
                            showingDropdown = false
                        },
                    )
                }
            }
        }

        if (filteredModuleList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Sin resultados",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filteredModuleList, key = { it.moduleName }) { module ->
                    ClassModuleCard(
                        module = module,
                        isExpanded = module.moduleName in expandedModules,
                        onToggle = {
                            expandedModules = if (module.moduleName in expandedModules) {
                                expandedModules - module.moduleName
                            } else {
                                expandedModules + module.moduleName
                            }
                            allExpanded = filteredModuleList.all { it.moduleName in expandedModules }
                        },
                    )
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun ClassModuleListScreenPreview() {
    ClassModuleListScreen(moduleList = dummyModuleList)
}
