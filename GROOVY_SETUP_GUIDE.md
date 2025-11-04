# Environment Banner - Gradle Groovy Setup Guide

This guide is specifically for projects using **Gradle Groovy DSL** (build.gradle files).

## Installation for Groovy Projects

### Step 1: Add Module to Settings

In your **`settings.gradle`** file:

```groovy
include ':app'
include ':envbanner'
```

### Step 2: Add Dependency

In your **`app/build.gradle`** file:

```groovy
dependencies {
    implementation project(':envbanner')
    
    // Your other dependencies
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
}
```

### Step 3: Use in Activity (Java)

```java
import com.fy.envbanner.EnvBannerUtil;
import com.fy.envbanner.Environment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Display environment banner
        EnvBannerUtil.INSTANCE.showBanner(this, Environment.DEV);
    }
}
```

### Step 3: Use in Activity (Kotlin)

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

## Advanced Configuration with Build Types

### Configure Build Types (Groovy DSL)

In your **`app/build.gradle`**:

```groovy
android {
    compileSdk 34
    
    defaultConfig {
        applicationId "com.example.myapp"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
    
    buildTypes {
        debug {
            buildConfigField 'String', 'ENV_NAME', '"DEV"'
            buildConfigField 'String', 'ENV_COLOR', '"#CC2196F3"'
            applicationIdSuffix ".dev"
            versionNameSuffix "-dev"
        }
        
        staging {
            buildConfigField 'String', 'ENV_NAME', '"STAGING"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCFFC107"'
            applicationIdSuffix ".staging"
            versionNameSuffix "-staging"
        }
        
        release {
            buildConfigField 'String', 'ENV_NAME', '"PROD"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCF44336"'
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

### Use BuildConfig in Activity

**Java:**
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Use environment from BuildConfig
        Environment env = Environment.fromText(
            BuildConfig.ENV_NAME, 
            BuildConfig.ENV_COLOR
        );
        EnvBannerUtil.INSTANCE.showBanner(this, env);
    }
}
```

**Kotlin:**
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Use environment from BuildConfig
        val env = Environment.fromText(
            BuildConfig.ENV_NAME, 
            BuildConfig.ENV_COLOR
        )
        EnvBannerUtil.showBanner(this, env)
    }
}
```

## Product Flavors Configuration

If you use product flavors (for different servers/clients):

```groovy
android {
    flavorDimensions "environment"
    
    productFlavors {
        dev {
            dimension "environment"
            applicationIdSuffix ".dev"
            versionNameSuffix "-dev"
            buildConfigField 'String', 'ENV_NAME', '"DEV"'
            buildConfigField 'String', 'ENV_COLOR', '"#CC2196F3"'
            buildConfigField 'String', 'BASE_URL', '"https://dev.api.example.com"'
        }
        
        staging {
            dimension "environment"
            applicationIdSuffix ".staging"
            versionNameSuffix "-staging"
            buildConfigField 'String', 'ENV_NAME', '"STAGING"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCFFC107"'
            buildConfigField 'String', 'BASE_URL', '"https://staging.api.example.com"'
        }
        
        production {
            dimension "environment"
            buildConfigField 'String', 'ENV_NAME', '"PROD"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCF44336"'
            buildConfigField 'String', 'BASE_URL', '"https://api.example.com"'
        }
    }
}
```

## Conditional Display (Only Non-Production)

**Java:**
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Only show banner in debug builds
        if (BuildConfig.DEBUG) {
            Environment env = Environment.fromText(
                BuildConfig.ENV_NAME, 
                BuildConfig.ENV_COLOR
            );
            EnvBannerUtil.INSTANCE.showBanner(this, env);
        }
    }
}
```

**Kotlin:**
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Only show banner in debug builds
        if (BuildConfig.DEBUG) {
            val env = Environment.fromText(
                BuildConfig.ENV_NAME, 
                BuildConfig.ENV_COLOR
            )
            EnvBannerUtil.showBanner(this, env)
        }
    }
}
```

## Custom Text Examples (Java)

```java
// Predefined environments
EnvBannerUtil.INSTANCE.showBanner(this, Environment.DEV);
EnvBannerUtil.INSTANCE.showBanner(this, Environment.STAGING);
EnvBannerUtil.INSTANCE.showBanner(this, Environment.PROD);

// Custom text with custom color
Environment custom1 = Environment.fromText("DEV-V1.2.3", "#CC2196F3");
EnvBannerUtil.INSTANCE.showBanner(this, custom1);

// Custom text with predefined color
Environment custom2 = Environment.fromTextWithPredefinedColor("DEV-LOCAL", Environment.DEV);
EnvBannerUtil.INSTANCE.showBanner(this, custom2);

// Multi-line text
Environment custom3 = Environment.fromText("TEST\nSERVER", "#CC9C27B0");
EnvBannerUtil.INSTANCE.showBanner(this, custom3);
```

## Dynamic Environment Change (Java)

```java
public class MainActivity extends AppCompatActivity {
    private int currentEnvIndex = 0;
    private Environment[] environments = {
        Environment.DEV,
        Environment.QA,
        Environment.STAGING,
        Environment.PROD
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        showCurrentEnvironment();
        
        Button btnChange = findViewById(R.id.btnChangeEnv);
        btnChange.setOnClickListener(v -> {
            currentEnvIndex = (currentEnvIndex + 1) % environments.length;
            showCurrentEnvironment();
            Toast.makeText(this, "Changed to: " + environments[currentEnvIndex].getDisplayName(), 
                Toast.LENGTH_SHORT).show();
        });
    }
    
    private void showCurrentEnvironment() {
        // Remove old banner
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        View oldBanner = decorView.findViewWithTag("env_banner");
        if (oldBanner != null) {
            decorView.removeView(oldBanner);
        }
        
        // Show new banner
        EnvBannerUtil.INSTANCE.showBanner(this, environments[currentEnvIndex]);
    }
}
```

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build staging APK (if configured)
./gradlew assembleStaging

# Build release APK
./gradlew assembleRelease

# Build specific flavor + build type
./gradlew assembleDevDebug
./gradlew assembleStagingRelease
./gradlew assembleProductionRelease

# Install debug APK to device
./gradlew installDebug

# Clean and build
./gradlew clean assembleDebug
```

## Common Issues

### Issue: "Unresolved reference: envbanner"

**Solution:** Make sure you've added the module in `settings.gradle`:
```groovy
include ':app'
include ':envbanner'
```

### Issue: "Cannot resolve symbol 'EnvBannerUtil'"

**Solution:** Add the dependency in `app/build.gradle`:
```groovy
dependencies {
    implementation project(':envbanner')
}
```

Then sync your project: **File > Sync Project with Gradle Files**

### Issue: Banner not showing

**Solution:** Make sure you call `showBanner` AFTER `setContentView`:
```java
setContentView(R.layout.activity_main);  // First
EnvBannerUtil.INSTANCE.showBanner(this, Environment.DEV);  // Then
```

## Complete Example (Groovy + Java)

**app/build.gradle:**
```groovy
plugins {
    id 'com.android.application'
}

android {
    namespace 'com.example.myapp'
    compileSdk 34
    
    defaultConfig {
        applicationId "com.example.myapp"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
    
    buildTypes {
        debug {
            buildConfigField 'String', 'ENV_NAME', '"DEV"'
            buildConfigField 'String', 'ENV_COLOR', '"#CC2196F3"'
        }
        release {
            buildConfigField 'String', 'ENV_NAME', '"PROD"'
            buildConfigField 'String', 'ENV_COLOR', '"#CCF44336"'
            minifyEnabled false
        }
    }
}

dependencies {
    implementation project(':envbanner')
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
}
```

**MainActivity.java:**
```java
package com.example.myapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.fy.envbanner.EnvBannerUtil;
import com.fy.envbanner.Environment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Show environment banner
        Environment env = Environment.fromText(
            BuildConfig.ENV_NAME, 
            BuildConfig.ENV_COLOR
        );
        EnvBannerUtil.INSTANCE.showBanner(this, env);
    }
}
```

---

**For more examples, see the main [README.md](README.md) file.**

