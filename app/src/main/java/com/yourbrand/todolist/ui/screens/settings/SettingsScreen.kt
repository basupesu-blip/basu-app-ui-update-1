package com.yourbrand.todolist.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.PreferencesManager
import com.yourbrand.todolist.data.local.entity.UserEntity
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.ui.theme.CardGray
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    currentUser: UserEntity?,
    preferencesManager: PreferencesManager,
    onBack: () -> Unit,
    onLogOut: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val emailNotif by preferencesManager.emailNotification.collectAsState(initial = true)
    val activityNotif by preferencesManager.activityNotification.collectAsState(initial = false)

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Setting", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(56.dp).background(CardGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFB0B0B0))
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(currentUser?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Text(currentUser?.email ?: "", fontSize = 12.sp, color = Color(0xFF9B9B9B))
                }
            }
            Spacer(Modifier.height(20.dp))
            SettingsNavRow("Account")
            SettingsToggleRow(
                "Email Notification",
                emailNotif
            ) { checked -> scope.launch { preferencesManager.setEmailNotification(checked) } }
            SettingsToggleRow(
                "Activities Notification",
                activityNotif
            ) { checked -> scope.launch { preferencesManager.setActivityNotification(checked) } }
            SettingsNavRow("Language")
            SettingsNavRow("About Us")
            SettingsNavRow("Privacy Policy")
            SettingsNavRow("Terms & Condition")
            Spacer(Modifier.height(10.dp))
            Text(
                "Log Out",
                color = Color.Red,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogOut() }
                    .padding(vertical = 14.dp)
            )
        }
    }
}

@Composable
private fun SettingsNavRow(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFB0B0B0))
    }
    Divider(color = Color(0xFFF0F0F0))
}

@Composable
private fun SettingsToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = BrandGreen)
        )
    }
    Divider(color = Color(0xFFF0F0F0))
}
