package com.yourbrand.todolist.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.Category
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.ScheduleCard
import com.yourbrand.todolist.ui.components.categoryIcon
import com.yourbrand.todolist.ui.theme.BrandBlack
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.ui.theme.CardGray
import com.yourbrand.todolist.viewmodel.ScheduleViewModel

@Composable
fun MyScheduleScreen(
    scheduleViewModel: ScheduleViewModel,
    onCreateSchedule: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val allSchedules by scheduleViewModel.allOneOff.collectAsState()
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            BrandHeader(showSettings = true, onSettingsClick = onSettingsClick)
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandBlack, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Today Schedule", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(
                        "You have ${allSchedules.size} schedules this week.",
                        color = Color(0xFFCFCFCF),
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onCreateSchedule() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Create Schedule", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Icon(Icons.Default.Add, contentDescription = "Create schedule", tint = BrandGreen)
            }
            Spacer(Modifier.height(16.dp))

            val categoryCounts = Category.values().associateWith { cat -> allSchedules.count { it.category == cat } }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(max = 260.dp)
            ) {
                item {
                    CategoryTile(
                        title = "All",
                        count = allSchedules.size,
                        icon = Icons.Default.List,
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null }
                    )
                }
                items(Category.values().toList()) { cat ->
                    CategoryTile(
                        title = cat.label,
                        count = categoryCounts[cat] ?: 0,
                        icon = categoryIcon(cat),
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            val filtered = if (selectedCategory == null) allSchedules else allSchedules.filter { it.category == selectedCategory }
            Text("Schedules", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            if (filtered.isEmpty()) {
                Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No schedules here yet.", color = Color(0xFFB0B0B0), fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filtered) { schedule ->
                        ScheduleCard(schedule = schedule, onDelete = { scheduleViewModel.deleteSchedule(schedule) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryTile(
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) BrandGreen else CardGray, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Icon(icon, contentDescription = null, tint = if (selected) Color.White else BrandBlack)
        Spacer(Modifier.height(10.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (selected) Color.White else BrandBlack)
        Text(
            "$count Schedule${if (count == 1) "" else "s"}",
            fontSize = 11.sp,
            color = if (selected) Color(0xFFEFEFEF) else Color(0xFF9B9B9B)
        )
    }
}
