package com.example.dditpgrupal.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
val dummyNotesList =
    listOf(
        Note("Clase 1", "Hola", false, LocalDate.of(2026, 4, 10)),
        Note(
            "Clase 2",
            "Importante: completar actividad en clase",
            true,
            LocalDate.of(2026, 4, 17),
        ),
        Note("Clase 3", "Descargar material de actividades", false, LocalDate.of(2026, 4, 26)),
        Note("TP", "Completar carátula de trabajo", false, LocalDate.of(2026, 5, 2)),
    )

data class Note(
    val name: String,
    val storedText: String,
    val isImportant: Boolean,
    val creationDate: LocalDate,
)
