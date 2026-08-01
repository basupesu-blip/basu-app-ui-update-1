package com.yourbrand.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourbrand.todolist.data.local.entity.Category
import com.yourbrand.todolist.data.local.entity.ScheduleEntity
import com.yourbrand.todolist.data.repository.ScheduleRepository
import kotlinx.coroutines.flow.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScheduleViewModel(
    private val repository: ScheduleRepository,
    private val userId: Long
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    val schedulesForSelectedDate: StateFlow<List<ScheduleEntity>> = _selectedDate
        .flatMapLatest { date -> observeMerged(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val routines: StateFlow<List<ScheduleEntity>> = repository.observeRoutines(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOneOff: StateFlow<List<ScheduleEntity>> = repository.observeAllOneOff(userId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun observeMerged(date: LocalDate): Flow<List<ScheduleEntity>> {
        val dow = date.dayOfWeek.value // 1=Mon .. 7=Sun
        return combine(
            repository.observeByDate(userId, date.format(dateFormatter)),
            repository.observeRoutines(userId)
        ) { oneOff, routines ->
            (oneOff + routines.filter { it.dayOfWeek == dow })
                .sortedBy { it.startTime }
        }
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun addSchedule(
        name: String,
        category: Category,
        date: LocalDate,
        startTime: String,
        endTime: String
    ) {
        viewModelScope.launch {
            repository.add(
                ScheduleEntity(
                    userId = userId,
                    name = name,
                    category = category,
                    date = date.format(dateFormatter),
                    startTime = startTime,
                    endTime = endTime,
                    isRoutine = false
                )
            )
        }
    }

    fun addRoutine(
        name: String,
        category: Category,
        dayOfWeek: DayOfWeek,
        startTime: String,
        endTime: String
    ) {
        viewModelScope.launch {
            repository.add(
                ScheduleEntity(
                    userId = userId,
                    name = name,
                    category = category,
                    date = LocalDate.now().format(dateFormatter),
                    startTime = startTime,
                    endTime = endTime,
                    isRoutine = true,
                    dayOfWeek = dayOfWeek.value
                )
            )
        }
    }

    fun deleteSchedule(schedule: ScheduleEntity) {
        viewModelScope.launch { repository.delete(schedule) }
    }

    class Factory(
        private val repository: ScheduleRepository,
        private val userId: Long
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScheduleViewModel(repository, userId) as T
        }
    }
}
