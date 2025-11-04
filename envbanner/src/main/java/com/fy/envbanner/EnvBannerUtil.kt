package com.fy.envbanner

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
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
    private const val TAG = "EnvBannerUtil"
    private const val BANNER_TAG = "env_banner"

    private var currentEnvironment: Environment? = null
    private var isInitialized = false

    /**
     * Initialize the banner system with Application context.
     * Banner will be displayed on all activities with minimal flickering.
     *
     * @param application Your Application instance
     * @param environment The environment to display
     */
    fun init(application: Application, environment: Environment) {
        if (isInitialized) {
            Log.d(TAG, "Already initialized, updating environment")
            currentEnvironment = environment
            return
        }

        currentEnvironment = environment
        isInitialized = true

        Log.d(TAG, "Initializing banner system for ${environment.displayName}")

        // Register lifecycle callbacks
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Not needed
            }

            override fun onActivityStarted(activity: Activity) {
                // Not needed
            }

            override fun onActivityResumed(activity: Activity) {
                // Show banner when activity is visible
                currentEnvironment?.let { env ->
                    Log.d(TAG, "Activity resumed: ${activity.javaClass.simpleName}, showing banner")
                    showBannerInternal(activity, env)
                }
            }

            override fun onActivityPaused(activity: Activity) {
                // Not needed - keep banner visible
            }

            override fun onActivityStopped(activity: Activity) {
                // Not needed - banner will be removed when activity is destroyed
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                // Not needed
            }

            override fun onActivityDestroyed(activity: Activity) {
                // Clean up banner when activity is destroyed
                removeBannerFromActivity(activity)
            }
        })
    }

    /**
     * Show banner on specific activity.
     * Can be called directly without init() for per-activity control.
     *
     * @param activity The activity to show banner on
     * @param environment The environment to display
     */
    fun showBanner(activity: Activity, environment: Environment) {
        Log.d(TAG, "showBanner called for ${activity.javaClass.simpleName}")
        currentEnvironment = environment
        showBannerInternal(activity, environment)
    }

    /**
     * Update the environment for all future banners.
     *
     * @param environment The new environment to display
     */
    fun updateEnvironment(environment: Environment) {
        Log.d(TAG, "Updating environment to ${environment.displayName}")
        currentEnvironment = environment
    }

    /**
     * Remove banner from all activities.
     */
    fun removeBanner() {
        Log.d(TAG, "Removing banner system")
        currentEnvironment = null
        isInitialized = false
    }

    private fun showBannerInternal(activity: Activity, environment: Environment) {
        activity.window?.decorView?.post {
            try {
                val decorView = activity.window.decorView as? ViewGroup
                if (decorView == null) {
                    Log.e(TAG, "DecorView is null, cannot show banner")
                    return@post
                }

                // Check if banner already exists and update it instead of recreating
                val existingBanner = decorView.findViewWithTag<FrameLayout>(BANNER_TAG)
                if (existingBanner != null) {
                    // Banner exists, just update the text and color
                    updateExistingBanner(existingBanner, environment)
                    return@post
                }

                // Create new banner
                val banner = createBanner(activity, environment)
                if (banner == null) {
                    Log.e(TAG, "Failed to create banner")
                    return@post
                }

                // Add banner to decorView
                decorView.addView(banner)
                banner.bringToFront()

                Log.d(TAG, "Banner shown successfully for ${environment.displayName}")

            } catch (e: Exception) {
                Log.e(TAG, "Error showing banner", e)
                e.printStackTrace()
            }
        }
    }

    private fun createBanner(activity: Activity, environment: Environment): FrameLayout? {
        try {
            val inflater = LayoutInflater.from(activity)

            // Inflate banner layout
            val banner = inflater.inflate(R.layout.banner_environment, null) as? FrameLayout

            if (banner == null) {
                Log.e(TAG, "Failed to inflate banner layout")
                return null
            }

            // Set tag for identification
            banner.tag = BANNER_TAG

            // Configure banner properties
            banner.isClickable = false
            banner.isFocusable = false
            banner.elevation = 9999f

            // Configure text and color
            val textView = banner.findViewById<TextView>(R.id.envBannerText)
            if (textView == null) {
                Log.e(TAG, "TextView not found in banner layout")
                return null
            }

            textView.text = environment.displayName

            val background = textView.background as? GradientDrawable
            background?.setColor(Color.parseColor(environment.colorHex))

            return banner

        } catch (e: Exception) {
            Log.e(TAG, "Error creating banner", e)
            e.printStackTrace()
            return null
        }
    }

    private fun updateExistingBanner(banner: FrameLayout, environment: Environment) {
        try {
            val textView = banner.findViewById<TextView>(R.id.envBannerText)
            if (textView != null) {
                textView.text = environment.displayName

                val background = textView.background as? GradientDrawable
                background?.setColor(Color.parseColor(environment.colorHex))

                Log.d(TAG, "Existing banner updated to ${environment.displayName}")
            } else {
                Log.e(TAG, "TextView not found in existing banner")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating existing banner", e)
        }
    }

    private fun removeBannerFromActivity(activity: Activity) {
        try {
            val decorView = activity.window?.decorView as? ViewGroup ?: return
            val banner = decorView.findViewWithTag<FrameLayout>(BANNER_TAG)
            if (banner != null) {
                decorView.removeView(banner)
                Log.d(TAG, "Banner removed from ${activity.javaClass.simpleName}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error removing banner", e)
        }
    }
}

