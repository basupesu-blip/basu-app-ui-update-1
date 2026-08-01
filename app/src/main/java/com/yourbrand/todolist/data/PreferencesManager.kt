package com.yourbrand.todolist.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class PreferencesManager(private val context: Context) {

    private object Keys {
        val USER_ID = longPreferencesKey("logged_in_user_id")
        val EMAIL_NOTIF = booleanPreferencesKey("email_notification")
        val ACTIVITY_NOTIF = booleanPreferencesKey("activity_notification")
    }

    val loggedInUserId: Flow<Long?> = context.dataStore.data.map { it[Keys.USER_ID] }
    val emailNotification: Flow<Boolean> = context.dataStore.data.map { it[Keys.EMAIL_NOTIF] ?: true }
    val activityNotification: Flow<Boolean> = context.dataStore.data.map { it[Keys.ACTIVITY_NOTIF] ?: false }

    suspend fun setLoggedInUser(userId: Long) {
        context.dataStore.edit { it[Keys.USER_ID] = userId }
    }

    suspend fun logOut() {
        context.dataStore.edit { it.remove(Keys.USER_ID) }
    }

    suspend fun setEmailNotification(enabled: Boolean) {
        context.dataStore.edit { it[Keys.EMAIL_NOTIF] = enabled }
    }

    suspend fun setActivityNotification(enabled: Boolean) {
        context.dataStore.edit { it[Keys.ACTIVITY_NOTIF] = enabled }
    }
}
