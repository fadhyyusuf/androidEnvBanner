package com.fy.envbanner

import android.app.Application

/**
 * Example Application class showing how to initialize EnvBanner.
 *
 * This library was created with AI assistance (November 2025).
 * See DISCLAIMER.md for more information.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize environment banner once for the entire app
        // Banner will automatically appear on all activities without flickering
        EnvBannerUtil.init(this, Environment.DEV)

        // You can also use custom environment
        // EnvBannerUtil.init(this, Environment.fromText("DEV-V1.2.3", "#CC2196F3"))
    }
}

