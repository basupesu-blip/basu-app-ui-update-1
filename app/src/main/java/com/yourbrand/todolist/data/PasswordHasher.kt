package com.yourbrand.todolist.data

import java.security.MessageDigest

/** Basic local SHA-256 hashing so raw passwords are never stored on-device. */
object PasswordHasher {
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
