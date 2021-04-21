package com.daffa.githubuserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daffa.githubuserapp.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.beginTransaction().replace(R.id.setting_holder, SettingsPreferenceFragment()).commit()
    }
}