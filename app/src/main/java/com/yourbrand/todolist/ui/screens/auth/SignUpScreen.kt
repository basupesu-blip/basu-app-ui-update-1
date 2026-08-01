package com.yourbrand.todolist.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun SignUpScreen(
    authViewModel: AuthViewModel,
    onSignUpSuccess: () -> Unit,
    onSignIn: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
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
            Text("Create a new account", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "Create an account so you can manage your personal schedule",
                fontSize = 13.sp,
                color = Color(0xFF8C8C8C)
            )
            Spacer(Modifier.height(24.dp))
            AppTextField(
                value = name, onValueChange = { name = it }, label = "Full Name",
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = email, onValueChange = { email = it }, label = "Email",
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = phone, onValueChange = { phone = it }, label = "Phone Number",
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardType = KeyboardType.Phone
            )
            Spacer(Modifier.height(14.dp))
            AppTextField(
                value = password, onValueChange = { password = it }, label = "Password", isPassword = true,
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }
            )
            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(Modifier.height(20.dp))
            PrimaryButton(
                text = "Sign Up",
                onClick = { authViewModel.signUp(name, email, phone, password, onSignUpSuccess) }
            )
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Already have an account? ", fontSize = 13.sp, color = Color(0xFF8C8C8C))
                Text(
                    "Sign In",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandGreen,
                    modifier = Modifier.clickableText { onSignIn() }
                )
            }
            Spacer(Modifier.weight(1f))
            FooterText()
        }
    }
}
