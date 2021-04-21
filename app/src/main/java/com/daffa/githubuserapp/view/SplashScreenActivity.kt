package com.daffa.githubuserapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daffa.githubuserapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val icon: ImageView = findViewById(R.id.applogo)
        val slideDownAnim = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        icon.startAnimation(slideDownAnim)

        val splashText: TextView = findViewById(R.id.splash_text)
        val slideDownText = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        splashText.startAnimation(slideDownText)

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}