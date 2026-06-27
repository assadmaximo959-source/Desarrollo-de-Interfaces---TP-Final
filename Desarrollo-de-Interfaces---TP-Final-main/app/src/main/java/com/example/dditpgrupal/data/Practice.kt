package com.example.dditpgrupal.data

import com.example.dditpgrupal.data.enums.PracticeStatus

val dummyPracticeList =
    listOf(
        Practice("Diseño de formulario básico", PracticeStatus.PENDIENTE),
        Practice("Trabajo Práctico - Unidad 1", PracticeStatus.ENTREGADA),
        Practice("Actividad en clase - Unidad 2", PracticeStatus.CORREGIDA),
        Practice("Prueba escrita", PracticeStatus.PENDIENTE),
        Practice("Actividad en clase - Unidad 3", PracticeStatus.PENDIENTE),
    )

data class Practice(
    val name: String,
    val status: PracticeStatus,
)
