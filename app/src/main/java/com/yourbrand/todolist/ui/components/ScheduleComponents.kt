package com.yourbrand.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.Category
import com.yourbrand.todolist.data.local.entity.ScheduleEntity
import com.yourbrand.todolist.navigation.Screen
import com.yourbrand.todolist.ui.theme.BrandBlack
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.ui.theme.BrandGreenLight
import com.yourbrand.todolist.ui.theme.CardGray

fun categoryIcon(category: Category): ImageVector = when (category) {
    Category.PERSONAL -> Icons.Default.Person
    Category.WORK -> Icons.Default.Laptop
    Category.STUDY -> Icons.Default.MenuBook
    Category.HOME -> Icons.Default.Home
    Category.MOVIE -> Icons.Default.Theaters
    Category.TRAVEL -> Icons.Default.Flight
}

@Composable
fun ScheduleCard(
    schedule: ScheduleEntity,
    highlighted: Boolean = false,
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (highlighted) BrandGreenLight else CardGray,
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(BrandBlack, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(categoryIcon(schedule.category), contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(schedule.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = BrandBlack)
            Text(schedule.category.label, fontSize = 12.sp, color = Color(0xFF8C8C8C))
            Text(
                "${schedule.startTime} - ${schedule.endTime}",
                fontSize = 11.sp,
                color = Color(0xFFA6A6A6)
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color(0xFF8C8C8C))
        }
    }
}

data class NavItem(val label: String, val icon: ImageVector, val route: String)

private val navItems = listOf(
    NavItem("Home", Icons.Default.Home, Screen.Home.route),
    NavItem("Calendar", Icons.Default.CalendarMonth, Screen.Calendar.route),
    NavItem("Routine", Icons.Default.CheckCircle, Screen.Routine.route),
    NavItem("My Schedule", Icons.Default.List, Screen.MySchedule.route),
    NavItem("Profile", Icons.Default.Person, Screen.Profile.route),
)

@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(containerColor = Color.White, tonalElevation = 4.dp) {
        navItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandGreen,
                    selectedTextColor = BrandGreen,
                    unselectedIconColor = Color(0xFFB0B0B0),
                    unselectedTextColor = Color(0xFFB0B0B0),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
