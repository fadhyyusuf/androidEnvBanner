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

        // Note: No need to call showBanner() here anymore!
        // Banner automatically appears because of init() in MyApplication class
        // But we keep this for backward compatibility and environment switching demo

        // Button di pojok kanan atas untuk demo bahwa item di belakang banner bisa di-click
        findViewById<Button>(R.id.btnTopRight).setOnClickListener {
            Toast.makeText(this, "Button di belakang banner berhasil di-click!", Toast.LENGTH_SHORT).show()
        }

        // Button untuk mengubah environment
        findViewById<Button>(R.id.btnChangeEnv).setOnClickListener {
            currentEnvIndex = (currentEnvIndex + 1) % environments.size
            // Update environment globally - will apply to all activities
            EnvBannerUtil.updateEnvironment(environments[currentEnvIndex])
            // Also update current activity immediately
            EnvBannerUtil.showBanner(this, environments[currentEnvIndex])
            Toast.makeText(this, "Environment berubah ke: ${environments[currentEnvIndex].displayName}", Toast.LENGTH_SHORT).show()
        }
    }
}