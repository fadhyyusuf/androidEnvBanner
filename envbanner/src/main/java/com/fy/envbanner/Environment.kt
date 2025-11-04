package com.fy.envbanner

/**
 * Environment sealed class for environment banner configuration.
 *
 * This library was created with AI assistance (November 2025).
 * All code has been tested and verified to work correctly.
 *
 * @see [DISCLAIMER.md](https://github.com/fadhyyusuf/envbanner/blob/main/DISCLAIMER.md)
 */
sealed class Environment(val displayName: String, val colorHex: String) {
    object DEV : Environment("DEV", "#CC2196F3")
    object QA : Environment("QA", "#CC4CAF50")
    object STAGING : Environment("STAGING", "#CCFFC107")
    object UAT : Environment("UAT", "#CC9C27B0")
    object PROD : Environment("PROD", "#CCF44336")
    object DEMO : Environment("DEMO", "#CC009688")
    object SANDBOX : Environment("SANDBOX", "#CC795548")
    object PREPROD : Environment("PREPROD", "#CC607D8B")
    object INTERNAL : Environment("INTERNAL", "#CCE91E63")
    object CUSTOM : Environment("CUSTOM", "#CC3F51B5")

    /**
     * Custom environment dengan text dan warna sesuai keinginan
     * @param text Text yang akan ditampilkan di banner
     * @param color Warna background dalam format hex ARGB (contoh: "#CC2196F3")
     */
    class Custom(text: String, color: String = "#CC3F51B5") : Environment(text, color)

    companion object {
        /**
         * Membuat environment dari text custom
         * @param text Text yang akan ditampilkan di banner (bisa multiple line dengan "\n")
         * @param color Warna background (opsional, default indigo)
         */
        fun fromText(text: String, color: String = "#CC3F51B5"): Environment {
            return Custom(text, color)
        }

        /**
         * Membuat environment dari text custom dengan warna predefined
         * @param text Text yang akan ditampilkan
         * @param predefinedColor Environment predefined untuk ambil warnanya
         */
        fun fromTextWithPredefinedColor(text: String, predefinedColor: Environment): Environment {
            return Custom(text, predefinedColor.colorHex)
        }
    }
}

