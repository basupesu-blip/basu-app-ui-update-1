package com.yourbrand.todolist.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourbrand.todolist.data.local.entity.UserEntity
import com.yourbrand.todolist.ui.components.AppTextField
import com.yourbrand.todolist.ui.components.BrandHeader
import com.yourbrand.todolist.ui.components.PrimaryButton
import com.yourbrand.todolist.ui.components.clickableText
import com.yourbrand.todolist.ui.theme.CardGray
import com.yourbrand.todolist.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    currentUser: UserEntity?,
    onSettingsClick: () -> Unit
) {
    var editing by remember { mutableStateOf(false) }
    var name by remember(currentUser) { mutableStateOf(currentUser?.name ?: "") }
    var username by remember(currentUser) { mutableStateOf(currentUser?.username ?: "") }
    var email by remember(currentUser) { mutableStateOf(currentUser?.email ?: "") }
    var phone by remember(currentUser) { mutableStateOf(currentUser?.phone ?: "") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BrandHeader()
                Text(
                    if (editing) "Save" else "Edit",
                    color = com.yourbrand.todolist.ui.theme.BrandGreen,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickableText {
                            if (editing) {
                                currentUser?.let {
                                    authViewModel.updateProfile(
                                        it.copy(name = name, username = username, email = email, phone = phone)
                                    )
                                }
                            }
                            editing = !editing
                        }
                )
            }
            Spacer(Modifier.height(20.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(CardGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color(0xFFB0B0B0))
                }
            }
            Spacer(Modifier.height(10.dp))
            if (!editing) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Text(email, fontSize = 12.sp, color = Color(0xFF9B9B9B), modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
            Spacer(Modifier.height(20.dp))

            if (editing) {
                AppTextField(value = name, onValueChange = { name = it }, label = "Name")
                Spacer(Modifier.height(12.dp))
                AppTextField(value = username, onValueChange = { username = it }, label = "Username")
                Spacer(Modifier.height(12.dp))
                AppTextField(value = email, onValueChange = { email = it }, label = "Email")
                Spacer(Modifier.height(12.dp))
                AppTextField(value = phone, onValueChange = { phone = it }, label = "Phone")
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Save Changes", onClick = {
                    currentUser?.let {
                        authViewModel.updateProfile(
                            it.copy(name = name, username = username, email = email, phone = phone)
                        )
                    }
                    editing = false
                })
            } else {
                ProfileRow("Username", username)
                ProfileRow("Email", email)
                ProfileRow("Phone", phone)
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Edit Profile", onClick = { editing = true })
            }
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(label, fontSize = 12.sp, color = Color(0xFF9B9B9B))
        Text(value.ifBlank { "-" }, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        Divider(Modifier.padding(top = 10.dp), color = Color(0xFFF0F0F0))
    }
}
