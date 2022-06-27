package com.example.mydemoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity: AppCompatActivity() {
    var img:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        img= findViewById(R.id.img_view)
        img?.animation = AnimationUtils.loadAnimation(this,R.anim.bottom_up)
        Handler().postDelayed(Runnable {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        },2000)


    }
}