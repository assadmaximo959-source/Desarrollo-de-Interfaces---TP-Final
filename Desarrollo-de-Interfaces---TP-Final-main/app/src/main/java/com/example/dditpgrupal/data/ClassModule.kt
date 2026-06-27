package com.example.dditpgrupal.data

val dummyModuleList =
    listOf(
        ClassModule("Módulo 1", dummyMaterials),
        ClassModule("Módulo 2", dummyMaterials),
        ClassModule("Módulo 3", dummyMaterials),
        ClassModule("Módulo 4", dummyMaterials),
        ClassModule("Bibliografía obligatoria", dummyMaterials),
        ClassModule("Bibliografía complementaria", dummyMaterials),
    )

data class ClassModule(
    val moduleName: String,
    val content: List<StudyMaterial>,
)
