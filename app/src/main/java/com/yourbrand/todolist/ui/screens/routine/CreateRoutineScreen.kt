package com.yourbrand.todolist.ui.screens.routine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.Category
import com.yourbrand.todolist.ui.components.AppTextField
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.PrimaryButton
import com.yourbrand.todolist.ui.components.SectionChip
import com.yourbrand.todolist.viewmodel.ScheduleViewModel
import java.time.DayOfWeek

@Composable
fun CreateRoutineScreen(
    scheduleViewModel: ScheduleViewModel,
    onCreated: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.PERSONAL) }
    var dayOfWeek by remember { mutableStateOf(DayOfWeek.MONDAY) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            BrandHeader()
            Spacer(Modifier.height(20.dp))
            Text("Create Routine Schedule", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.height(20.dp))
            AppTextField(value = name, onValueChange = { name = it }, label = "Schedule Name")
            Spacer(Modifier.height(20.dp))
            Text("Select Category", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(Category.values()) { cat ->
                    SectionChip(text = cat.label, selected = category == cat, onClick = { category = cat })
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Select Day", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(DayOfWeek.values().toList()) { day ->
                    SectionChip(
                        text = day.name.take(3).lowercase().replaceFirstChar { it.uppercase() },
                        selected = dayOfWeek == day,
                        onClick = { dayOfWeek = day }
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTextField(
                    value = startTime, onValueChange = { startTime = it }, label = "Start Time (HH:mm)",
                    modifier = Modifier.weight(1f)
                )
                AppTextField(
                    value = endTime, onValueChange = { endTime = it }, label = "End Time (HH:mm)",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(28.dp))
            PrimaryButton(
                text = "Create",
                onClick = {
                    if (name.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()) {
                        scheduleViewModel.addRoutine(name, category, dayOfWeek, startTime, endTime)
                        onCreated()
                    }
                }
            )
        }
    }
}
