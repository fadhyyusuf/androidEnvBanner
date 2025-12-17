# Environment Banner Library

[![](https://jitpack.io/v/fadhyyusuf/envbanner.svg)](https://jitpack.io/#fadhyyusuf/envbanner)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

An Android library to display environment banners in the top-right corner of your app, helping testers and developers identify which environment they're currently using.

> **⚠️ AI-Assisted Project**: This library was created with the assistance of AI technology. While all code has been tested and verified, users are encouraged to review and test thoroughly before production use. See [DISCLAIMER.md](DISCLAIMER.md) for details.

## Features

✨ **10 Predefined Environments**: DEV, QA, STAGING, UAT, PROD, DEMO, SANDBOX, PREPROD, INTERNAL, CUSTOM  
🎨 **Color-coded**: Each environment has a distinct color for quick identification  
📝 **Flexible Custom Text**: Use custom text for banners based on your needs  
👻 **Transparent**: Banner has 80% opacity so items behind it remain visible  
🖱️ **Click-through**: Items behind the banner can still be clicked  
📐 **Minimalist Design**: Banner only appears in the top-right corner with minimal size  
🔝 **Always On Top**: Banner stays visible above all UI elements (never hidden behind content)  
⚡ **Zero Flickering**: WindowManager overlay - banner truly floating with ZERO flickering  
🎯 **True Persistent**: Banner created once and stays across all activities  
🎭 **Universal Theme Compatibility**: Works with ALL Android themes (AppCompat, Material3, MaterialComponents, etc.)  
> **📦 Available on JitPack:** This library is published on JitPack for easy integration.  
🚀 **Easy Integration**: Only one line of code needed for implementation  

### Method 1: Via JitPack (Recommended)

#### Step 1: Add JitPack repository

**For Kotlin DSL (`settings.gradle.kts` or root `build.gradle.kts`):**
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**For Groovy DSL (`settings.gradle` or root `build.gradle`):**
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2: Add dependency

**For Kotlin DSL (`app/build.gradle.kts`):**
```kotlin
dependencies {
    implementation("com.github.fadhyyusuf:androidEnvBanner:x.x.x")
}
```

**For Groovy DSL (`app/build.gradle`):**
```groovy
dependencies {
    implementation 'com.github.fadhyyusuf:androidEnvBanner:x.x.x'
}
```

### Method 2: Local Module (For Development)

## Installation

> **📘 Note for Groovy Users:** If you're using Gradle Groovy DSL, see the detailed [Groovy Setup Guide](GROOVY_SETUP_GUIDE.md) for complete instructions with Java examples.

### 1. Add the envbanner module to your project

**For Kotlin DSL (`settings.gradle.kts`):**
```kotlin
include(":envbanner")
```

**For Groovy DSL (`settings.gradle`):**
```groovy
include ':envbanner'
```

### 2. Add dependency in your app module

**For Kotlin DSL (`app/build.gradle.kts`):**
```kotlin
dependencies {
    implementation(project(":envbanner"))
    // ... other dependencies
}
```

**For Groovy DSL (`app/build.gradle`):**
```groovy
dependencies {
    implementation project(':envbanner')
    // ... other dependencies
}
```

## Usage

### Recommended: Application-Level Init (No Flickering)

For best experience with smooth transitions between activities:

#### Step 1: Create Application Class

```kotlin
import android.app.Application
import com.fy.envbanner.EnvBannerUtil
import com.fy.envbanner.Environment

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize banner once for entire app
        // Banner will automatically appear on ALL activities
        EnvBannerUtil.init(this, Environment.DEV)
    }
}
```

#### Step 2: Register in AndroidManifest.xml

```xml
<application
    android:name=".MyApplication"
    android:icon="@mipmap/ic_launcher"
    ...>
    <!-- Your activities -->
</application>
```

#### Step 3: Done! 🎉

Banner automatically appears on all activities without flickering!

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // No need to call showBanner() here!
        // Banner automatically appears ✅
    }
}
```

> **💡 Benefits**: No flickering between activities, less code, automatic consistency across all screens.

---

### Alternative: Per-Activity (Legacy Method)

If you prefer manual control per activity:

```kotlin
import com.fy.envbanner.EnvBannerUtil
import com.fy.envbanner.Environment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Display environment banner
        EnvBannerUtil.showBanner(this, Environment.DEV)
    }
}
```

> **⚠️ Note**: This method may have slight flickering when navigating between activities.

### Available Environments

**Predefined Environments:**
```kotlin
Environment.DEV        // Blue (#CC2196F3)
Environment.QA         // Green (#CC4CAF50)
Environment.STAGING    // Yellow (#CCFFC107)
Environment.UAT        // Purple (#CC9C27B0)
Environment.PROD       // Red (#CCF44336)
Environment.DEMO       // Teal (#CC009688)
Environment.SANDBOX    // Brown (#CC795548)
Environment.PREPROD    // Gray (#CC607D8B)
Environment.INTERNAL   // Pink (#CCE91E63)
```

**Custom Text with Custom Color:**
```kotlin
// Custom text with custom color
Environment.fromText("DEV-V1.2.3", "#CC2196F3")
Environment.fromText("BETA", "#CCFF5722")
Environment.fromText("RC-1.0", "#CC9C27B0")

// Multi-line text
Environment.fromText("TEST\nSERVER", "#CC9C27B0")
```

**Custom Text with Predefined Color:**
```kotlin
// Use color from predefined environment
Environment.fromTextWithPredefinedColor("DEV-LOCAL", Environment.DEV)
Environment.fromTextWithPredefinedColor("PROD-BACKUP", Environment.PROD)
Environment.fromTextWithPredefinedColor("STAGING-V2", Environment.STAGING)
```

### Dynamic Environment (BuildConfig)

Use BuildConfig to automatically determine the environment:

**Kotlin:**
```kotlin
val currentEnv = when (BuildConfig.BUILD_TYPE) {
    "debug" -> Environment.DEV
    "staging" -> Environment.STAGING
    "release" -> Environment.PROD
    else -> Environment.fromText("UNKNOWN")
}

EnvBannerUtil.showBanner(this, currentEnv)
```

**Java:**
```java
Environment currentEnv;
switch (BuildConfig.BUILD_TYPE) {
    case "debug":
        currentEnv = Environment.DEV;
        break;
    case "staging":
        currentEnv = Environment.STAGING;
        break;
    case "release":
        currentEnv = Environment.PROD;
        break;
    default:
        currentEnv = Environment.fromText("UNKNOWN", "#CC3F51B5");
        break;
}

EnvBannerUtil.INSTANCE.showBanner(this, currentEnv);
```

### Conditional Display

Only show banner in non-production environments:

**Kotlin:**
```kotlin
if (BuildConfig.DEBUG) {
    EnvBannerUtil.showBanner(this, Environment.DEV)
}
```

**Java:**
```java
if (BuildConfig.DEBUG) {
    EnvBannerUtil.INSTANCE.showBanner(this, Environment.DEV);
}
```

## Customization

### Changing Environment at Runtime

**Kotlin:**
```kotlin
// Remove old banner
val decorView = window.decorView as ViewGroup
decorView.findViewWithTag<View>("env_banner")?.let {
    decorView.removeView(it)
}

// Show new banner
EnvBannerUtil.showBanner(this, Environment.STAGING)
```

**Java:**
```java
// Remove old banner
ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
View oldBanner = decorView.findViewWithTag("env_banner");
if (oldBanner != null) {
    decorView.removeView(oldBanner);
}

// Show new banner
EnvBannerUtil.INSTANCE.showBanner(this, Environment.STAGING);
```

## Advanced Usage

### Using with Build Variants

Configure different environments for build variants:

**Kotlin DSL (`app/build.gradle.kts`):**
```kotlin
android {
    buildTypes {
        debug {
            buildConfigField("String", "ENV_NAME", "\"DEV\"")
            buildConfigField("String", "ENV_COLOR", "\"#CC2196F3\"")
        }
        create("staging") {
            buildConfigField("String", "ENV_NAME", "\"STAGING\"")
            buildConfigField("String", "ENV_COLOR", "\"#CCFFC107\"")
        }
        release {
            buildConfigField("String", "ENV_NAME", "\"PROD\"")
            buildConfigField("String", "ENV_COLOR", "\"#CCF44336\"")
        }
    }
}
```

**Groovy DSL (`app/build.gradle`):**
```groovy
android {
    buildTypes {
        debug {
            buildConfigField 'String', 'ENV_NAME', '"DEV"'
            buildConfigField 'String', 'ENV_COLOR', '"#CC2196F3"'
        }
        staging {
            buildConfigField 'String', 'ENV_NAME', '"STAGING"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCFFC107"'
        }
        release {
            buildConfigField 'String', 'ENV_NAME', '"PROD"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCF44336"'
        }
    }
}
```

Then in your Activity:
```kotlin
val env = Environment.fromText(BuildConfig.ENV_NAME, BuildConfig.ENV_COLOR)
EnvBannerUtil.showBanner(this, env)
```

## How It Works

1. **Always On Top**: Banner uses maximum elevation (9999dp) and `bringToFront()` to ensure it's always visible above all UI elements
2. **Opacity**: Banner has 80% alpha channel (CC in ARGB hex) for transparency
3. **Click-through**: Banner has `clickable=false` and `focusable=false` so touch events pass through to views behind it
4. **Rounded Corners**: Uses GradientDrawable with 6dp corner radius
5. **High Elevation**: 9999dp elevation ensures banner is never hidden by other views

> **🎯 Z-Index Fix**: The banner is guaranteed to appear on top of all content, including RecyclerViews, Fragments, AppBars, FABs, and any other UI elements. For technical details, see [Z_INDEX_FIX.md](Z_INDEX_FIX.md).

## Sample App

See the complete implementation in the `app` module for examples:
- Click-through demo with button behind banner
- Dynamic environment changes
- All 10 available environments
- Custom text examples

## Color Format (ARGB Hex)

Format: `#AARRGGBB`
- `AA` = Alpha (opacity): `CC` = 80%, `FF` = 100%, `80` = 50%
- `RRGGBB` = RGB color

Examples:
```kotlin
// 80% opacity blue
Environment.fromText("DEV", "#CC2196F3")

// 100% opacity red (not transparent)
Environment.fromText("PROD", "#FFF44336")

// 50% opacity green (very transparent)
Environment.fromText("TEST", "#804CAF50")
```

## Best Practices

✅ **GOOD**: Short text, easy to read
```kotlin
Environment.fromText("DEV", "#CC2196F3")
Environment.fromText("STAGING", "#CCFFC107")
```

✅ **GOOD**: With version information
```kotlin
Environment.fromText("DEV-V1.2", "#CC2196F3")
```

✅ **GOOD**: Multi-line for longer text
```kotlin
Environment.fromText("STAGING\nV2.0", "#CCFFC107")
```

❌ **AVOID**: Text too long
```kotlin
Environment.fromText("DEVELOPMENT ENVIRONMENT VERSION 1.2.3", "#CC2196F3")
```

❌ **AVOID**: Too many lines
```kotlin
Environment.fromText("DEV\nSERVER\nV1.2.3\nBUILD 123", "#CC2196F3")
```

## Requirements

- **minSdk**: 21 (Android 5.0 Lollipop)
- **targetSdk**: 34
- **Kotlin**: 1.8.0 - 2.0.x (see [KOTLIN_COMPATIBILITY.md](KOTLIN_COMPATIBILITY.md))
- **Java**: 8+
- **Theme**: Compatible with ALL Android themes (AppCompat, Material3, MaterialComponents, etc.)

> 📘 **Kotlin Compatibility**: This library supports Kotlin 1.8+ through 2.0+. For detailed compatibility information, see [KOTLIN_COMPATIBILITY.md](KOTLIN_COMPATIBILITY.md).

> 🎭 **Theme Compatibility**: This library works with any Android theme! No need to change your app's theme. See [THEME_COMPATIBILITY_FIX.md](THEME_COMPATIBILITY_FIX.md) for technical details.

## License

MIT License - Free to use in your projects

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues, questions, or suggestions, please open an issue on the GitHub repository.

## Disclaimer

This project was created with the assistance of AI technology. All code has been tested and verified to work correctly. For more information, see [DISCLAIMER.md](DISCLAIMER.md).

## Acknowledgments

- Created with AI assistance (November 2025)
- Thanks to the Android developer community
- Built with Kotlin and Android best practices

---

**Made with ❤️ (and 🤖 AI) for easier testing**

