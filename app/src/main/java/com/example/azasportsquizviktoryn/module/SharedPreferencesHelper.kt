package com.example.azasportsquizviktoryn.module

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

    fun saveCompletedLevels(completedLevels: Set<Int>) {
        val editor = sharedPreferences.edit()
        val completedLevelsAsString = completedLevels.map { it.toString() }.toSet()
        editor.putStringSet("completed_levels", completedLevelsAsString)
        editor.apply()
        Log.e("SharedPreferencesHelper", "Saving completed levels: $completedLevels")
    }

    fun loadCompletedLevels(): Set<Int> {
        val completedLevelsAsString = sharedPreferences.getStringSet("completed_levels", emptySet())
        val completedLevels = completedLevelsAsString?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        Log.e("SharedPreferencesHelper", "Loaded Completed Levels: $completedLevels")
        return completedLevels
    }

    fun saveLastUnlockedLevel(lastUnlockedLevel: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("last_unlocked_level", lastUnlockedLevel)
        editor.apply()
        Log.e("SharedPreferencesHelper", "Saving last unlocked level: $lastUnlockedLevel")
    }

    fun getLastUnlockedLevel(): Int {
        val lastUnlockedLevel = sharedPreferences.getInt("last_unlocked_level", 1)
        Log.e("SharedPreferencesHelper", "Loaded Last Unlocked Level: $lastUnlockedLevel")
        return lastUnlockedLevel
    }
}