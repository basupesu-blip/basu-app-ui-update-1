package com.yourbrand.todolist.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.UserEntity
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.ScheduleCard
import com.yourbrand.todolist.ui.theme.BrandBlack
import com.yourbrand.todolist.viewmodel.ScheduleViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(
    scheduleViewModel: ScheduleViewModel,
    currentUser: UserEntity?,
    onSettingsClick: () -> Unit
) {
    val schedules by scheduleViewModel.schedulesForSelectedDate.collectAsState()
    val selectedDate by scheduleViewModel.selectedDate.collectAsState()

    LaunchedEffect(Unit) {
        // Always show today when Home is entered
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            BrandHeader(showSettings = true, onSettingsClick = onSettingsClick)
            Spacer(Modifier.height(20.dp))
            Text(
                "Hello, ${currentUser?.name?.substringBefore(" ") ?: "there"}",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Text("Here's what's on your plate today", fontSize = 13.sp, color = Color(0xFF8C8C8C))
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandBlack, RoundedCornerShape(18.dp))
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Today Schedule", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Text("You have ${schedules.size} schedules today.", color = Color(0xFFCFCFCF), fontSize = 12.sp)
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${schedules.size}", fontWeight = FontWeight.Bold, color = BrandBlack)
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    selectedDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM yy", Locale.getDefault())),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            Spacer(Modifier.height(12.dp))
            if (schedules.isEmpty()) {
                Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No schedules for today yet.", color = Color(0xFFB0B0B0), fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(schedules) { schedule ->
                        ScheduleCard(
                            schedule = schedule,
                            onDelete = { scheduleViewModel.deleteSchedule(schedule) }
                        )
                    }
                }
            }
        }
    }
}
