package com.fy.envbanner

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val environments = listOf(
        Environment.DEV,
        Environment.QA,
        Environment.STAGING,
        Environment.UAT,
        Environment.PROD,
        Environment.DEMO,
        Environment.SANDBOX,
        Environment.PREPROD,
        Environment.INTERNAL,
        // Contoh custom text dengan warna custom
        Environment.fromText("DEV-V1.2.3", "#CC2196F3"),
        Environment.fromText("BETA", "#CCFF5722"),
        Environment.fromText("TEST\nSERVER", "#CC9C27B0"),
        Environment.fromText("CUSTOM", "#CC3F51B5"),
        // Contoh custom text dengan warna predefined
        Environment.fromTextWithPredefinedColor("DEV-LOCAL", Environment.DEV),
        Environment.fromTextWithPredefinedColor("PROD-BACKUP", Environment.PROD)
    )
    private var currentEnvIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tampilkan banner environment pertama kali
        showCurrentEnvironment()

        // Button di pojok kanan atas untuk demo bahwa item di belakang banner bisa di-click
        findViewById<Button>(R.id.btnTopRight).setOnClickListener {
            Toast.makeText(this, "Button di belakang banner berhasil di-click!", Toast.LENGTH_SHORT).show()
        }

        // Button untuk mengubah environment
        findViewById<Button>(R.id.btnChangeEnv).setOnClickListener {
            currentEnvIndex = (currentEnvIndex + 1) % environments.size
            showCurrentEnvironment()
            Toast.makeText(this, "Environment berubah ke: ${environments[currentEnvIndex].displayName}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCurrentEnvironment() {
        // Hapus banner lama jika ada (untuk demo perubahan environment)
        val decorView = window.decorView as android.view.ViewGroup
        val bannerTag = "env_banner"
        decorView.findViewWithTag<android.view.View>(bannerTag)?.let {
            decorView.removeView(it)
        }

        // Tampilkan banner environment
        EnvBannerUtil.showBanner(this, environments[currentEnvIndex])
    }
}