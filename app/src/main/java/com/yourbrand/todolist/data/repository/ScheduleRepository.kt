package com.yourbrand.todolist.data.repository

import com.yourbrand.todolist.data.local.dao.ScheduleDao
import com.yourbrand.todolist.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    fun observeByDate(userId: Long, date: String): Flow<List<ScheduleEntity>> =
        scheduleDao.observeByDate(userId, date)

    fun observeRoutines(userId: Long): Flow<List<ScheduleEntity>> =
        scheduleDao.observeRoutines(userId)

    fun observeAllOneOff(userId: Long): Flow<List<ScheduleEntity>> =
        scheduleDao.observeAllOneOff(userId)

    suspend fun add(schedule: ScheduleEntity): Long = scheduleDao.insert(schedule)

    suspend fun update(schedule: ScheduleEntity) = scheduleDao.update(schedule)

    suspend fun delete(schedule: ScheduleEntity) = scheduleDao.delete(schedule)
}
