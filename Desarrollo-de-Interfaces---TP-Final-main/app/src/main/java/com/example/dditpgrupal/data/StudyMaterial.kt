package com.example.dditpgrupal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.ui.graphics.vector.ImageVector

val dummyMaterials = listOf(
    StudyMaterial("Guía de estudio - Unidad 1", "PDF", Icons.Default.Description),
    StudyMaterial("Presentación de materia", "PPT", Icons.Default.CoPresent),
    StudyMaterial("Clase Grabada - Introducción", "Video", Icons.Default.PlayCircle),
    StudyMaterial("Plantilla de Trabajo", "Word", Icons.AutoMirrored.Filled.Article)
)

data class StudyMaterial(
    val name: String,
    val type: String,
    val icon: ImageVector,
)
