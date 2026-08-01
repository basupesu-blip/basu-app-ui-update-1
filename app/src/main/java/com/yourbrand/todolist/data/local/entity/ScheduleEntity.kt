package com.yourbrand.todolist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single schedule / to-do item.
 * If [isRoutine] is true, [dayOfWeek] (1=Mon .. 7=Sun) is used to recur every week
 * instead of a fixed [date].
 */
@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val name: String,
    val category: Category,
    val date: String,        // yyyy-MM-dd, used for one-off schedules
    val startTime: String,   // HH:mm
    val endTime: String,     // HH:mm
    val isRoutine: Boolean = false,
    val dayOfWeek: Int? = null // 1=Mon ... 7=Sun, only for routines
)
