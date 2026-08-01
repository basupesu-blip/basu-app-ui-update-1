package com.yourbrand.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourbrand.todolist.data.PreferencesManager
import com.yourbrand.todolist.data.local.entity.UserEntity
import com.yourbrand.todolist.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val loggedInUserId: StateFlow<Long?> = preferencesManager.loggedInUserId
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _infoMessage = MutableStateFlow<String?>(null)
    val infoMessage: StateFlow<String?> = _infoMessage

    fun clearMessages() {
        _errorMessage.value = null
        _infoMessage.value = null
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                _errorMessage.value = "Please fill in email and password"
                return@launch
            }
            val result = userRepository.login(email.trim(), password)
            result.onSuccess { user ->
                preferencesManager.setLoggedInUser(user.id)
                _currentUser.value = user
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }

    fun signUp(name: String, email: String, phone: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                _errorMessage.value = "Please fill in all fields"
                return@launch
            }
            val result = userRepository.signUp(name.trim(), email.trim(), phone.trim(), password)
            result.onSuccess { userId ->
                preferencesManager.setLoggedInUser(userId)
                _currentUser.value = userRepository.getUser(userId)
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }

    fun sendResetLink(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (userRepository.accountExists(email.trim())) {
                _infoMessage.value = "Reset instructions saved locally. Set a new password."
                onSuccess()
            } else {
                _errorMessage.value = "No account found for this email"
            }
        }
    }

    fun resetPassword(email: String, newPassword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.resetPassword(email.trim(), newPassword).onSuccess {
                _infoMessage.value = "Password updated. Please log in."
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            _currentUser.value = userRepository.getUser(userId)
        }
    }

    fun updateProfile(user: UserEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            userRepository.updateProfile(user)
            _currentUser.value = user
            onDone()
        }
    }

    fun logOut(onDone: () -> Unit) {
        viewModelScope.launch {
            preferencesManager.logOut()
            _currentUser.value = null
            onDone()
        }
    }

    class Factory(
        private val userRepository: UserRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(userRepository, preferencesManager) as T
        }
    }
}
