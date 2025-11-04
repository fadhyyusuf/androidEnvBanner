package com.fy.envbanner

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Utility object for displaying environment banners.
 *
 * This library was created with AI assistance (November 2025).
 * See DISCLAIMER.md for more information.
 */
object EnvBannerUtil {
    fun showBanner(activity: Activity, environment: Environment) {
        val decorView = activity.window.decorView as ViewGroup
        val inflater = LayoutInflater.from(activity)
        val banner = inflater.inflate(
            activity.resources.getIdentifier("banner_environment", "layout", activity.packageName),
            decorView,
            false
        ) as FrameLayout

        // Set tag untuk identifikasi banner
        banner.tag = "env_banner"

        // Set banner tidak clickable agar touch event diteruskan ke view di belakangnya
        banner.isClickable = false
        banner.isFocusable = false

        val textView = banner.findViewById<TextView>(activity.resources.getIdentifier("envBannerText", "id", activity.packageName))
        textView.text = environment.displayName

        // Update background color dengan mempertahankan rounded corners
        val background = textView.background as? GradientDrawable
        background?.setColor(Color.parseColor(environment.colorHex))

        // Set elevation yang lebih tinggi agar banner selalu di atas
        banner.elevation = 9999f

        // Tambahkan banner di posisi terakhir (index paling atas) agar selalu terlihat
        decorView.addView(banner)

        // Pastikan banner di bring to front
        banner.bringToFront()
    }
}

