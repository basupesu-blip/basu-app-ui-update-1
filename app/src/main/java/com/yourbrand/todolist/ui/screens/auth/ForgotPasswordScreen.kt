package com.yourbrand.todolist.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.ui.components.*
import com.yourbrand.todolist.ui.theme.BrandGreenLight
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var linkSent by remember { mutableStateOf(false) }
    val error by authViewModel.errorMessage.collectAsState()
    val info by authViewModel.infoMessage.collectAsState()

    LaunchedEffect(Unit) { authViewModel.clearMessages() }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            BrandHeader()
            Spacer(Modifier.height(24.dp))
            Text("Forgot Password?", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(BrandGreenLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(40.dp))
                }
            }
            Spacer(Modifier.height(20.dp))

            if (!linkSent) {
                Text(
                    "Please enter your registered Email ID. We'll check it locally and let you set a new password.",
                    fontSize = 13.sp,
                    color = Color(0xFF8C8C8C)
                )
                Spacer(Modifier.height(20.dp))
                AppTextField(
                    value = email, onValueChange = { email = it }, label = "Email",
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
                )
                error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = Color.Red, fontSize = 12.sp)
                }
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Send", onClick = {
                    authViewModel.sendResetLink(email) { linkSent = true }
                })
            } else {
                info?.let {
                    Text(it, fontSize = 13.sp, color = BrandGreen)
                    Spacer(Modifier.height(16.dp))
                }
                AppTextField(
                    value = newPassword, onValueChange = { newPassword = it },
                    label = "New Password", isPassword = true,
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }
                )
                error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = Color.Red, fontSize = 12.sp)
                }
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Reset Password", onClick = {
                    authViewModel.resetPassword(email, newPassword, onBackToLogin)
                })
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Back to Log In",
                fontSize = 13.sp,
                color = BrandGreen,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickableText { onBackToLogin() }
            )
            Spacer(Modifier.weight(1f))
            FooterText()
        }
    }
}
