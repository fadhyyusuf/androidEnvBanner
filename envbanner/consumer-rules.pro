# EnvBanner Library ProGuard Rules
# Keep theme resources to ensure compatibility across all parent themes
-keep class com.fy.envbanner.** { *; }

# Keep all resources used by the library
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep Material Components classes used by the library
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

