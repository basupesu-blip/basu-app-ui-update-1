package com.yourbrand.todolist.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.ui.components.*
import com.yourbrand.todolist.ui.theme.BrandGreen
import com.yourbrand.todolist.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by authViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) { authViewModel.clearMessages() }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            BrandHeader()
            Spacer(Modifier.height(28.dp))
            Text("Welcome Back", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "I am so happy to see you. You can continue to login to manage your schedule",
                fontSize = 13.sp,
                color = Color(0xFF8C8C8C)
            )
            Spacer(Modifier.height(28.dp))
            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true,
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }
            )
            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Forgot password?",
                    color = BrandGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickableText { onForgotPassword() }
                )
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(text = "Log In", onClick = { authViewModel.login(email, password, onLoginSuccess) })
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", fontSize = 13.sp, color = Color(0xFF8C8C8C))
                Text(
                    "Sign Up",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandGreen,
                    modifier = Modifier.clickableText { onSignUp() }
                )
            }
            Spacer(Modifier.weight(1f))
            FooterText()
        }
    }
}
