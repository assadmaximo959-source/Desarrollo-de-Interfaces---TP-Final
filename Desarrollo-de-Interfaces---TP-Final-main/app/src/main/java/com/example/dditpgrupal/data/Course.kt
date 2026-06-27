package com.example.dditpgrupal.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.example.dditpgrupal.data.enums.CourseMode
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
val dummyCourseList =
    mutableListOf(
        Course(
            "Desarrollo de Interfaces",
            "01-3900",
            "Turno noche, 19:00 a 23:00",
            "Ingeniería e Investigaciones Tecnológicas",
            CourseMode.PRESENCIAL,
            listOf("Heliana Vera", "Gonzalo Rivas"),
            listOf(
                LocalDate.of(2026, 4, 22),
                LocalDate.of(2026, 5, 20),
                LocalDate.of(2026, 6, 30),
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 13),
            ),
            listOf(),
            absences = 0,
            studentsAtRisk = null,
            color = Color.Green,
        ),
        Course(
            "Matemática General",
            "01-6300",
            "Turno mañana, 8:00 a 12:00",
            "Ingeniería e Investigaciones Tecnológicas",
            CourseMode.VIRTUAL,
            listOf("Liliana Romano", "Federico Pileci"),
            listOf(
                LocalDate.of(2026, 5, 26),
                LocalDate.of(2026, 6, 18),
            ),
            dummyNotesList,
            absences = 1,
            studentsAtRisk = null,
            color = Color.Blue,
        ),
        Course(
            "Matemática General",
            "01-2900",
            "Turno noche, 19:00 a 23:00",
            "Ingeniería e Investigaciones Tecnológicas",
            CourseMode.SEMIPRESENCIAL,
            listOf("Liliana Romano", "Federico Pileci"),
            listOf(
                LocalDate.of(2026, 5, 26),
                LocalDate.of(2026, 6, 18),
            ),
            dummyNotesList,
            absences = 1,
            studentsAtRisk = null,
            color = Color.Cyan,
        ),
        Course(
            "Diseño de Aplicaciones",
            "01-9900",
            "Turno noche, 19:00 a 23:00",
            "Ingeniería e Investigaciones Tecnológicas",
            CourseMode.SEMIPRESENCIAL,
            listOf("Juan Perez"),
            listOf(
                LocalDate.of(2026, 5, 26),
                LocalDate.of(2026, 6, 21),
            ),
            dummyNotesList,
            absences = 3,
            studentsAtRisk = null,
            color = Color.Gray,
        ),
    )

data class Course(
    val name: String,
    val commission: String,
    val shiftAndSchedule: String,
    val departament: String,
    val mode: CourseMode,
    val teacher: List<String>,
    val importantDates: List<LocalDate>,
    val courseNotes: List<Note>,
    val absences: Int?,
    val studentsAtRisk: Int?,
    val color: Color,
)
