package com.yourbrand.todolist.ui.screens.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.UserEntity
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.ScheduleCard
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.viewmodel.ScheduleViewModel
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun RoutineScreen(
    scheduleViewModel: ScheduleViewModel,
    currentUser: UserEntity?,
    onCreateRoutine: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val routines by scheduleViewModel.routines.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            BrandHeader(showSettings = true, onSettingsClick = onSettingsClick)
            Spacer(Modifier.height(20.dp))
            Text("Have a nice day", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(
                java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM yy", Locale.getDefault())),
                fontSize = 13.sp,
                color = BrandGreen,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(14.dp))
                    .border(1.dp, BrandGreen, RoundedCornerShape(14.dp))
                    .clickable { onCreateRoutine() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Create Routine Schedule",
                    color = BrandGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create routine",
                    tint = BrandGreen,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            if (routines.isEmpty()) {
                Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No routines yet. Create one above.", color = Color(0xFFB0B0B0), fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(routines) { routine ->
                        Column {
                            ScheduleCard(schedule = routine, onDelete = { scheduleViewModel.deleteSchedule(routine) })
                            routine.dayOfWeek?.let { dow ->
                                Text(
                                    "Every " + java.time.DayOfWeek.of(dow).getDisplayName(TextStyle.FULL, Locale.getDefault()),
                                    fontSize = 11.sp,
                                    color = Color(0xFFB0B0B0),
                                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
