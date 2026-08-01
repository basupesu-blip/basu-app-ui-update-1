package com.yourbrand.todolist.ui.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.FooterText
import com.yourbrand.todolist.ui.components.PrimaryButton
import com.yourbrand.todolist.ui.theme.BrandGreen

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            BrandHeader()
            Spacer(Modifier.height(32.dp))
            Text("Welcome to", fontWeight = FontWeight.Bold, fontSize = 26.sp)
            Text("To Do List Apps", fontWeight = FontWeight.Bold, fontSize = 26.sp, color = BrandGreen)
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BrandGreen,
                    modifier = Modifier.size(140.dp)
                )
            }
            Text(
                "Organize your day, build routines, and never miss a schedule again. " +
                    "Everything is saved right on your device.",
                fontSize = 13.sp,
                color = Color(0xFF8C8C8C)
            )
            Spacer(Modifier.height(20.dp))
            PrimaryButton(text = "Get Started", onClick = onGetStarted)
            Spacer(Modifier.height(12.dp))
            FooterText()
        }
    }
}
