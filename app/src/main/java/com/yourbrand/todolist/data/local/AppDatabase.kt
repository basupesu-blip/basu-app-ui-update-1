package com.yourbrand.todolist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourbrand.todolist.data.local.dao.ScheduleDao
import com.yourbrand.todolist.data.local.dao.UserDao
import com.yourbrand.todolist.data.local.entity.ScheduleEntity
import com.yourbrand.todolist.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ScheduleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todolist.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
