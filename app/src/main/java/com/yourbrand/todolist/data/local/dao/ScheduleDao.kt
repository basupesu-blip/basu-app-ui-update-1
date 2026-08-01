package com.yourbrand.todolist.data.local.dao

import androidx.room.*
import com.yourbrand.todolist.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insert(schedule: ScheduleEntity): Long

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Delete
    suspend fun delete(schedule: ScheduleEntity)

    @Query("SELECT * FROM schedules WHERE userId = :userId ORDER BY startTime ASC")
    fun observeAll(userId: Long): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE userId = :userId AND isRoutine = 0 AND date = :date ORDER BY startTime ASC")
    fun observeByDate(userId: Long, date: String): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE userId = :userId AND isRoutine = 1 ORDER BY startTime ASC")
    fun observeRoutines(userId: Long): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE userId = :userId AND isRoutine = 0")
    fun observeAllOneOff(userId: Long): Flow<List<ScheduleEntity>>
}
