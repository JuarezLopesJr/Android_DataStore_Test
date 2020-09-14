package com.example.datastoretest.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "preference_name"

class DataStoreRepository(context: Context) {

    private object PreferenceKeys {
        val prefKeyName = preferencesKey<String>("my_name")
    }


    /* Initializing datastore */
    private val dataStore = context.createDataStore(
        name = PREFERENCE_NAME
    )

    /* edit method is async, must be called inside a suspend function */
    suspend fun saveDataStore(name: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.prefKeyName] = name
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.d("DataStore", e.message.toString())
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preference ->
            val myName = preference[PreferenceKeys.prefKeyName] ?: "empty"
            myName
        }
}