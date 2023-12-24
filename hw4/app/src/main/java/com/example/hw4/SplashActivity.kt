package com.example.hw4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 使用 Handler 实现延时操作
        Handler().postDelayed({
            // 启动主页面或其他页面
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000) // 2000 毫秒表示延时 2 秒
    }
}
