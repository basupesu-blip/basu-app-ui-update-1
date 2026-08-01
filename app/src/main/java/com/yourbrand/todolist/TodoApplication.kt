package com.yourbrand.todolist

import android.app.Application
import com.yourbrand.todolist.data.PreferencesManager
import com.yourbrand.todolist.data.local.AppDatabase
import com.yourbrand.todolist.data.repository.ScheduleRepository
import com.yourbrand.todolist.data.repository.UserRepository

class TodoApplication : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var userRepository: UserRepository
        private set
    lateinit var scheduleRepository: ScheduleRepository
        private set
    lateinit var preferencesManager: PreferencesManager
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        userRepository = UserRepository(database.userDao())
        scheduleRepository = ScheduleRepository(database.scheduleDao())
        preferencesManager = PreferencesManager(this)
    }
}
