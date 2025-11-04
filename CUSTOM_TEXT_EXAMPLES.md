# Environment Banner - Custom Text Examples

## Custom Text Usage Examples

### 1. Custom Text with Custom Color

```kotlin
// Simple text with custom color
EnvBannerUtil.showBanner(this, Environment.fromText("DEV-V1.2.3", "#CC2196F3"))

// Text with different colors
EnvBannerUtil.showBanner(this, Environment.fromText("BETA", "#CCFF5722"))
EnvBannerUtil.showBanner(this, Environment.fromText("RC-1.0", "#CC9C27B0"))
```

### 2. Multi-line Text

```kotlin
// Text with 2 lines
EnvBannerUtil.showBanner(this, Environment.fromText("TEST\nSERVER", "#CC9C27B0"))

// Text with version and environment
EnvBannerUtil.showBanner(this, Environment.fromText("DEV\nV2.3.1", "#CC2196F3"))
```

### 3. Custom Text with Predefined Color

```kotlin
// Using color from Environment.DEV (blue)
EnvBannerUtil.showBanner(
    this, 
    Environment.fromTextWithPredefinedColor("DEV-LOCAL", Environment.DEV)
)

// Using color from Environment.PROD (red)
EnvBannerUtil.showBanner(
    this, 
    Environment.fromTextWithPredefinedColor("PROD-BACKUP", Environment.PROD)
)

// Using color from Environment.STAGING (yellow)
EnvBannerUtil.showBanner(
    this, 
    Environment.fromTextWithPredefinedColor("STAGING-V2", Environment.STAGING)
)
```

### 4. Dynamic Environment with BuildConfig

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Determine environment based on build type
        val envText = when (BuildConfig.BUILD_TYPE) {
            "debug" -> "DEV-${BuildConfig.VERSION_NAME}"
            "staging" -> "STAGING-${BuildConfig.VERSION_NAME}"
            "release" -> "PROD-${BuildConfig.VERSION_NAME}"
            else -> "UNKNOWN"
        }
        
        val env = when (BuildConfig.BUILD_TYPE) {
            "debug" -> Environment.fromTextWithPredefinedColor(envText, Environment.DEV)
            "staging" -> Environment.fromTextWithPredefinedColor(envText, Environment.STAGING)
            "release" -> Environment.fromTextWithPredefinedColor(envText, Environment.PROD)
            else -> Environment.fromText(envText)
        }
        
        EnvBannerUtil.showBanner(this, env)
    }
}
```

### 5. List of Environments for Testing

```kotlin
class MainActivity : AppCompatActivity() {
    private val environments = listOf(
        // Predefined environments
        Environment.DEV,
        Environment.QA,
        Environment.STAGING,
        
        // Custom with version
        Environment.fromText("DEV-V1.2.3", "#CC2196F3"),
        Environment.fromText("DEV-V1.2.4", "#CC2196F3"),
        
        // Custom with server
        Environment.fromText("DEV-SERVER-1", "#CC2196F3"),
        Environment.fromText("DEV-SERVER-2", "#CC2196F3"),
        
        // Multi-line
        Environment.fromText("TEST\nSERVER", "#CC9C27B0"),
        Environment.fromText("BETA\nV1.0", "#CCFF5722"),
        
        // With predefined color
        Environment.fromTextWithPredefinedColor("DEV-LOCAL", Environment.DEV),
        Environment.fromTextWithPredefinedColor("PROD-BACKUP", Environment.PROD)
    )
    
    private var currentEnvIndex = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        showCurrentEnvironment()
        
        // Button to cycle through environments
        findViewById<Button>(R.id.btnChangeEnv).setOnClickListener {
            currentEnvIndex = (currentEnvIndex + 1) % environments.size
            showCurrentEnvironment()
        }
    }
    
    private fun showCurrentEnvironment() {
        val decorView = window.decorView as ViewGroup
        decorView.findViewWithTag<View>("env_banner")?.let {
            decorView.removeView(it)
        }
        EnvBannerUtil.showBanner(this, environments[currentEnvIndex])
    }
}
```

### 6. Color Format (ARGB Hex)

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

### 7. Best Practices

```kotlin
// ✅ GOOD: Short text, easy to read
Environment.fromText("DEV", "#CC2196F3")
Environment.fromText("STAGING", "#CCFFC107")

// ✅ GOOD: With version information
Environment.fromText("DEV-V1.2", "#CC2196F3")

// ✅ GOOD: Multi-line for long text
Environment.fromText("STAGING\nV2.0", "#CCFFC107")

// ❌ AVOID: Text too long
Environment.fromText("DEVELOPMENT ENVIRONMENT VERSION 1.2.3", "#CC2196F3")

// ❌ AVOID: Too many lines
Environment.fromText("DEV\nSERVER\nV1.2.3\nBUILD 123", "#CC2196F3")
```

## Color Palette

Use easily distinguishable colors:

| Environment | Color Hex | Visual |
|------------|-----------|--------|
| DEV | #CC2196F3 | 🔵 Blue |
| QA | #CC4CAF50 | 🟢 Green |
| STAGING | #CCFFC107 | 🟡 Yellow |
| UAT | #CC9C27B0 | 🟣 Purple |
| PROD | #CCF44336 | 🔴 Red |
| DEMO | #CC009688 | 🔷 Teal |
| SANDBOX | #CC795548 | 🟤 Brown |
| PREPROD | #CC607D8B | ⚫ Gray |
| INTERNAL | #CCE91E63 | 🔺 Pink |

---

**Tips**: Use consistent colors for each environment so testers can easily identify the environment just by looking at the banner color.

