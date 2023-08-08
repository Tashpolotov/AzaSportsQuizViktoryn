package com.example.azasportsquizviktoryn.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.ActivityMainBinding
import com.example.azasportsquizviktoryn.ui.fragments.HomeFragment
import com.example.azasportsquizviktoryn.ui.fragments.ProfileFragment
import com.example.azasportsquizviktoryn.ui.obBoarding.PageFragment
import com.example.azasportsquizviktoryn.ui.obBoarding.utils.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var preferences: Preferences
    private lateinit var onBoardingCompletedKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBoardingCompletedKey = "onBoardingCompleted"
        preferences.setBoardingShowed(false)

        if (savedInstanceState == null) {
            if (!preferences.isBoardingShowed()) {
                supportFragmentManager.beginTransaction().add(R.id.fr_container, PageFragment()).commit()
                binding.bottomNavView.visibility = View.GONE
            } else {
                supportFragmentManager.beginTransaction().add(R.id.fr_container, HomeFragment()).commit()
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_container, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu_rank -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_container, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_container, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(onBoardingCompletedKey, preferences.isBoardingShowed())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        preferences.setBoardingShowed(savedInstanceState.getBoolean(onBoardingCompletedKey, false))
    }
}