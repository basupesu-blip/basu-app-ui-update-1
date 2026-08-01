package com.yourbrand.todolist.data.repository

import com.yourbrand.todolist.data.PasswordHasher
import com.yourbrand.todolist.data.local.dao.UserDao
import com.yourbrand.todolist.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun observeUser(userId: Long): Flow<UserEntity?> = userDao.observeById(userId)

    suspend fun getUser(userId: Long): UserEntity? = userDao.getById(userId)

    suspend fun signUp(name: String, email: String, phone: String, password: String): Result<Long> {
        if (userDao.countByEmail(email) > 0) {
            return Result.failure(Exception("An account with this email already exists"))
        }
        val user = UserEntity(
            name = name,
            username = email.substringBefore("@"),
            email = email,
            phone = phone,
            passwordHash = PasswordHasher.hash(password)
        )
        val id = userDao.insert(user)
        return Result.success(id)
    }

    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.findByEmail(email)
            ?: return Result.failure(Exception("No account found for this email"))
        return if (user.passwordHash == PasswordHasher.hash(password)) {
            Result.success(user)
        } else {
            Result.failure(Exception("Incorrect password"))
        }
    }

    suspend fun accountExists(email: String): Boolean = userDao.countByEmail(email) > 0

    suspend fun resetPassword(email: String, newPassword: String): Result<Unit> {
        val user = userDao.findByEmail(email) ?: return Result.failure(Exception("No account found for this email"))
        userDao.update(user.copy(passwordHash = PasswordHasher.hash(newPassword)))
        return Result.success(Unit)
    }

    suspend fun updateProfile(user: UserEntity) = userDao.update(user)
}
