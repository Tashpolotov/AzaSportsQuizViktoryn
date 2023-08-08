package com.example.azasportsquizviktoryn.ui.obBoarding.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        "preferences",
        Context.MODE_PRIVATE
    )

    fun isBoardingShowed(): Boolean {
        return sharedPref.getBoolean("boarding_showed", false)
    }

    fun setBoardingShowed(isShown: Boolean) {
        sharedPref.edit().putBoolean("boarding_showed", isShown).apply()
    }
}