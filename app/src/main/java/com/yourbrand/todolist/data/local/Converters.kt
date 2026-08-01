package com.yourbrand.todolist.data.local

import androidx.room.TypeConverter
import com.yourbrand.todolist.data.local.entity.Category

class Converters {
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(value: String): Category = Category.valueOf(value)
}
