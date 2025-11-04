# Kotlin Compatibility Guide

## ✅ Supported Kotlin Versions

This library is compatible with a wide range of Kotlin versions from the last 2 years (2023-2025).

### Tested & Supported Versions

| Kotlin Version | Status | Notes |
|----------------|--------|-------|
| **2.0.x** | ✅ Supported | Latest Kotlin 2.x series |
| **1.9.x** | ✅ Supported | Recommended for stability |
| **1.8.x** | ✅ Supported | Minimum required version |
| **1.7.x** | ⚠️ May work | Not officially tested |
| **< 1.7** | ❌ Not supported | Too old |

### Recommended Configuration

**For maximum compatibility:**
```kotlin
kotlinOptions {
    jvmTarget = "1.8"
    apiVersion = "1.8"
    languageVersion = "1.8"
}
```

---

## 📋 Compatibility Settings

### Library Configuration

The library is built with these compatibility settings:

| Setting | Value | Reason |
|---------|-------|--------|
| **minSdk** | 21 | Android 5.0+ (Lollipop) |
| **compileSdk** | 34 | Stable and widely supported |
| **targetSdk** | 34 | Modern but compatible |
| **JVM Target** | 1.8 | Java 8 compatibility |
| **Kotlin API** | 1.8 | Broad compatibility |
| **Kotlin Language** | 1.8 | Stable features only |

### Why These Choices?

✅ **MinSdk 21**: Covers 99%+ of Android devices  
✅ **Java 8**: Universal compatibility  
✅ **Kotlin 1.8**: Stable, mature, widely adopted  
✅ **CompileSdk 34**: Modern features without bleeding edge  

---

## 🔧 Usage with Different Kotlin Versions

### Kotlin 2.0.x (Latest)

**Your project's `gradle/libs.versions.toml`:**
```toml
[versions]
kotlin = "2.0.21"  # or any 2.0.x version
```

**Usage:**
```kotlin
// Works perfectly with Kotlin 2.0+
EnvBannerUtil.showBanner(this, Environment.DEV)
Environment.fromText("DEV-V1.2.3", "#CC2196F3")
```

✅ **Fully compatible** - No issues expected

---

### Kotlin 1.9.x (Recommended)

**Your project's `gradle/libs.versions.toml`:**
```toml
[versions]
kotlin = "1.9.0"  # or any 1.9.x version
```

**Usage:**
```kotlin
// Recommended version for stability
EnvBannerUtil.showBanner(this, Environment.DEV)
Environment.fromText("STAGING", "#CCFFC107")
```

✅ **Fully compatible** - Best balance of features and stability

---

### Kotlin 1.8.x (Minimum)

**Your project's `gradle/libs.versions.toml`:**
```toml
[versions]
kotlin = "1.8.0"  # or any 1.8.x version
```

**Usage:**
```kotlin
// Works with minimum supported version
EnvBannerUtil.showBanner(this, Environment.QA)
```

✅ **Fully compatible** - Minimum required version

---

## 🚀 Migration from Older Kotlin Versions

### From Kotlin 1.7.x or older

If your project uses Kotlin 1.7.x or older, you have two options:

#### Option 1: Upgrade Kotlin (Recommended)

Update your project to Kotlin 1.8+ for best compatibility:

```toml
[versions]
kotlin = "1.9.0"  # Recommended
```

**Benefits:**
- Better performance
- More features
- Better IDE support
- Long-term support

#### Option 2: Use with Caution

The library *may* work with Kotlin 1.7.x, but:
- ⚠️ Not officially tested
- ⚠️ May have issues
- ⚠️ No guarantee of compatibility

---

## 📊 Version Compatibility Matrix

### Full Compatibility Table

| Your Kotlin | Library Build | Compatibility | Notes |
|-------------|--------------|---------------|-------|
| 2.1.x | 1.9.0 | ✅ Yes | Future versions |
| 2.0.x | 1.9.0 | ✅ Yes | Latest stable |
| 1.9.x | 1.9.0 | ✅ Yes | Same version |
| 1.8.x | 1.9.0 | ✅ Yes | Backward compatible |
| 1.7.x | 1.9.0 | ⚠️ Maybe | Not tested |
| 1.6.x | 1.9.0 | ❌ No | Too old |

### Android Gradle Plugin (AGP) Compatibility

| AGP Version | Kotlin Version | Library Compatible |
|-------------|----------------|-------------------|
| 8.2.x | 1.8.0 - 2.0.x | ✅ Yes |
| 8.1.x | 1.8.0 - 1.9.x | ✅ Yes |
| 8.0.x | 1.7.0 - 1.9.x | ⚠️ Maybe |
| 7.4.x | 1.7.0 - 1.8.x | ⚠️ Maybe |

---

## 🔍 Checking Your Kotlin Version

### In your project's `gradle/libs.versions.toml`:
```toml
[versions]
kotlin = "1.9.0"  # Your version here
```

### Or in root `build.gradle.kts`:
```kotlin
plugins {
    kotlin("android") version "1.9.0" apply false
}
```

### Verify in Terminal:
```bash
./gradlew --version
```

---

## 🛠️ Troubleshooting

### Issue: "Kotlin version mismatch"

**Error:**
```
Kotlin version mismatch: The library was compiled with Kotlin 1.9.0 
but your project uses Kotlin 1.7.0
```

**Solution 1: Update Kotlin (Recommended)**
```toml
[versions]
kotlin = "1.9.0"
```

**Solution 2: Add Explicit API Mode**
```kotlin
kotlinOptions {
    apiVersion = "1.8"
}
```

---

### Issue: "Unsupported class file version"

**Error:**
```
Unsupported class file major version 55
```

**Solution: Update JVM Target**
```kotlin
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
```

---

### Issue: Build fails with Kotlin 2.0+

**Possible Cause:** Using features not available in library's target

**Solution:**
The library is built to be compatible with Kotlin 2.0+. If issues occur:

1. Clean and rebuild:
   ```bash
   ./gradlew clean build
   ```

2. Invalidate caches (Android Studio):
   - File → Invalidate Caches → Invalidate and Restart

3. Check AGP compatibility:
   ```toml
   [versions]
   agp = "8.2.0"  # Use 8.2+ for Kotlin 2.0
   ```

---

## 📝 Best Practices

### For Library Users

✅ **Use Kotlin 1.9.x** for best compatibility  
✅ **Keep JVM target at 1.8** for maximum compatibility  
✅ **Update regularly** to get latest fixes  
✅ **Test with your Kotlin version** before production  

### For Contributors

✅ **Target Kotlin 1.8 API** for broadest support  
✅ **Avoid Kotlin 2.0+ exclusive features**  
✅ **Test with multiple Kotlin versions**  
✅ **Document breaking changes**  

---

## 🎯 Summary

| Aspect | Value |
|--------|-------|
| **Minimum Kotlin** | 1.8.0 |
| **Recommended Kotlin** | 1.9.0+ |
| **Maximum Tested** | 2.0.x |
| **JVM Target** | 1.8 (Java 8) |
| **Android MinSdk** | 21 (Android 5.0) |
| **Android TargetSdk** | 34 |

---

## 📚 Additional Resources

- [Kotlin Releases](https://kotlinlang.org/docs/releases.html)
- [Kotlin Compatibility Guide](https://kotlinlang.org/docs/compatibility-guide.html)
- [Android AGP Compatibility](https://developer.android.com/studio/releases/gradle-plugin)

---

## 🆘 Need Help?

If you encounter Kotlin compatibility issues:

1. Check this guide first
2. Search existing [GitHub Issues](https://github.com/YOUR_USERNAME/envbanner/issues)
3. Create a new issue with:
   - Your Kotlin version
   - Your AGP version
   - Error messages
   - Build configuration

---

**Last Updated**: November 4, 2025  
**Library Version**: 1.0.0  
**Kotlin Compatibility**: 1.8.0 - 2.0.x

