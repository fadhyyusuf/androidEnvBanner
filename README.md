# Environment Banner Library

An Android library to display environment banners in the top-right corner of your app, helping testers and developers identify which environment they're currently using.

## Features

✨ **10 Predefined Environments**: DEV, QA, STAGING, UAT, PROD, DEMO, SANDBOX, PREPROD, INTERNAL, CUSTOM  
🎨 **Color-coded**: Each environment has a distinct color for quick identification  
📝 **Flexible Custom Text**: Use custom text for banners based on your needs  
👻 **Transparent**: Banner has 80% opacity so items behind it remain visible  
🖱️ **Click-through**: Items behind the banner can still be clicked  
📐 **Minimalist Design**: Banner only appears in the top-right corner with minimal size  
🎯 **Easy Integration**: Only one line of code needed for implementation  

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

### Basic Implementation

In `MainActivity.kt` or your Base Activity:

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

1. **Opacity**: Banner has 80% alpha channel (CC in ARGB hex) for transparency
2. **Click-through**: Banner has `clickable=false` and `focusable=false` so touch events pass through to views behind it
3. **Rounded Corners**: Uses GradientDrawable with 6dp corner radius
4. **Elevation**: 4dp shadow for depth effect

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

- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 36
- **Kotlin**: 1.9+

## License

MIT License - Free to use in your projects

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues, questions, or suggestions, please open an issue on the GitHub repository.

---

**Made with ❤️ for easier testing**

