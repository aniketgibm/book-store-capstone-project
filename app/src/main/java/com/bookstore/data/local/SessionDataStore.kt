package com.bookstore.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Singleton
class SessionDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")
        private val IS_GUEST_KEY = booleanPreferencesKey("is_guest")
    }

    val currentUserId: Flow<Long?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]?.takeIf { it > 0 }
    }

    val isGuest: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_GUEST_KEY] ?: false
    }

    suspend fun saveSession(userId: Long) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
            prefs[IS_GUEST_KEY] = false
        }
    }

    suspend fun setGuestMode() {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = -1L
            prefs[IS_GUEST_KEY] = true
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
