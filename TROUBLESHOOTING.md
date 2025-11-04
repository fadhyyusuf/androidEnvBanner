# 🔧 Troubleshooting - Banner Tidak Muncul

## ✅ MASALAH SUDAH DIPERBAIKI!

Jika banner tidak muncul setelah implementasi WindowManager overlay, berikut solusinya:

---

## 🐛 Root Cause

**Masalah**: Resource identifier mencari layout di package aplikasi (`context.packageName`) padahal resource ada di package library (`com.fy.envbanner`).

**Solusi**: Library sekarang mencoba kedua package name (app package dulu, lalu library package sebagai fallback).

---

## ✅ Perbaikan yang Sudah Dilakukan

### 1. Fix Resource Identifier di `showOverlayBanner()`

```kotlin
// BEFORE (Tidak bekerja):
val layoutId = context.resources.getIdentifier("banner_environment", "layout", context.packageName)

// AFTER (Bekerja):
var layoutId = context.resources.getIdentifier("banner_environment", "layout", context.packageName)
if (layoutId == 0) {
    layoutId = context.resources.getIdentifier("banner_environment", "layout", "com.fy.envbanner")
}
```

### 2. Fix Resource Identifier di `updateOverlayBanner()`

```kotlin
// BEFORE:
val textViewId = context.resources.getIdentifier("envBannerText", "id", context.packageName)

// AFTER:
var textViewId = context.resources.getIdentifier("envBannerText", "id", context.packageName)
if (textViewId == 0) {
    textViewId = context.resources.getIdentifier("envBannerText", "id", "com.fy.envbanner")
}
```

---

## 📋 Checklist Jika Banner Masih Tidak Muncul

### 1. ✅ Verifikasi Init Dipanggil

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Pastikan ini dipanggil
        Log.d("EnvBanner", "Initializing banner...")
        EnvBannerUtil.init(this, Environment.DEV)
        Log.d("EnvBanner", "Banner initialized!")
    }
}
```

### 2. ✅ Application Class Terdaftar

```xml
<!-- AndroidManifest.xml -->
<application
    android:name=".MyApplication"  ← Pastikan ini ada!
    ...>
```

### 3. ✅ Rebuild Clean

```bash
cd /path/to/your/project

# Clean build directories
./gradlew clean

# Rebuild
./gradlew assembleDebug
```

### 4. ✅ Cek Permission (Android 6+)

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                Log.d("EnvBanner", "Overlay permission: GRANTED")
                EnvBannerUtil.init(this, Environment.DEV)
            } else {
                Log.e("EnvBanner", "Overlay permission: DENIED")
                // Fallback to per-activity method
            }
        } else {
            EnvBannerUtil.init(this, Environment.DEV)
        }
    }
}
```

### 5. ✅ Verifikasi Library Version

Pastikan menggunakan library terbaru dengan fix resource identifier:

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.github.fadhyyusuf:envbanner:1.2.1") // Latest version
}
```

---

## 🧪 Debug Mode

Tambahkan logging untuk debug:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        try {
            Log.d("EnvBanner", "=== ENV BANNER DEBUG ===")
            Log.d("EnvBanner", "Application context: ${this.packageName}")
            Log.d("EnvBanner", "Android version: ${Build.VERSION.SDK_INT}")
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("EnvBanner", "Can draw overlays: ${Settings.canDrawOverlays(this)}")
            }
            
            EnvBannerUtil.init(this, Environment.DEV)
            Log.d("EnvBanner", "Init completed successfully!")
            
        } catch (e: Exception) {
            Log.e("EnvBanner", "Error initializing banner", e)
            e.printStackTrace()
        }
    }
}
```

---

## 🔍 Common Issues & Solutions

### Issue 1: Permission Denied (Android 6+)

**Symptom**: Banner tidak muncul di Android 6.0+

**Solution**: 
```kotlin
// Check permission
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    if (!Settings.canDrawOverlays(this)) {
        // Request permission or use fallback
        Toast.makeText(this, "Overlay permission needed for dev banner", Toast.LENGTH_SHORT).show()
    }
}
```

**Note**: Permission biasanya auto-granted untuk apps dari Play Store.

---

### Issue 2: Resources Not Found

**Symptom**: `layoutId == 0` atau `textViewId == 0`

**Solution**: Sudah diperbaiki di library v1.2.1+. Update library:

```bash
./gradlew clean
./gradlew assembleDebug
```

---

### Issue 3: Application Class Not Registered

**Symptom**: `init()` tidak dipanggil

**Solution**: Periksa AndroidManifest.xml:

```xml
<application
    android:name=".MyApplication"  ← Harus ada!
    ...>
```

---

### Issue 4: Wrong Package Name

**Symptom**: Banner tidak muncul karena package name salah

**Solution**: Library sekarang otomatis fallback ke library package. Tidak perlu action.

---

### Issue 5: Build Cache Issue

**Symptom**: Banner tidak muncul setelah update library

**Solution**: Clean build:

```bash
./gradlew clean
rm -rf build app/build
./gradlew assembleDebug
```

---

## ✅ Verification Steps

Setelah perbaikan, verify dengan langkah ini:

### Step 1: Tambahkan Log

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("EnvBanner", "START")
        EnvBannerUtil.init(this, Environment.DEV)
        Log.d("EnvBanner", "END")
    }
}
```

### Step 2: Run App

```bash
./gradlew installDebug
adb logcat | grep EnvBanner
```

### Step 3: Check Logs

Expected output:
```
D/EnvBanner: START
D/EnvBanner: END
```

### Step 4: Visual Check

Banner harus muncul di pojok kanan atas dengan:
- ✅ Text "DEV" (atau environment Anda)
- ✅ Warna biru (atau warna environment Anda)
- ✅ Transparan (80% opacity)
- ✅ Tidak flickering saat pindah screen

---

## 🎯 Alternative: Fallback to DecorView

Jika overlay masih bermasalah, gunakan fallback method:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Fallback: per-activity method (may flicker slightly)
        EnvBannerUtil.showBanner(this, Environment.DEV)
    }
}
```

**Note**: Method ini mungkin ada sedikit flickering tapi pasti bekerja.

---

## 📊 Test Matrix

| Android Version | Overlay Permission | Expected Result |
|----------------|-------------------|-----------------|
| 5.0 - 5.1 (API 21-22) | Not required | ✅ Works |
| 6.0 - 7.1 (API 23-25) | Auto-granted | ✅ Works |
| 8.0+ (API 26+) | Not required (TYPE_APPLICATION_OVERLAY) | ✅ Works |

---

## 🚀 Quick Fix Command

Jika banner tidak muncul, jalankan ini:

```bash
cd /path/to/your/project

# 1. Update library to latest
# (edit build.gradle.kts to use latest version)

# 2. Clean build
./gradlew clean

# 3. Rebuild
./gradlew assembleDebug

# 4. Install
./gradlew installDebug

# 5. Check logcat
adb logcat | grep -i "envbanner\|overlay"
```

---

## 📚 Related Documentation

| File | Description |
|------|-------------|
| `WINDOW_OVERLAY_ID.md` | Panduan WindowManager overlay |
| `WINDOW_OVERLAY_GUIDE.md` | Complete overlay guide |
| `TROUBLESHOOTING.md` | This file |

---

## 💡 Still Not Working?

### Last Resort Checklist:

1. [ ] `MyApplication` class created?
2. [ ] `android:name=".MyApplication"` in manifest?
3. [ ] `EnvBannerUtil.init()` called in `Application.onCreate()`?
4. [ ] Clean build done? (`./gradlew clean`)
5. [ ] Latest library version?
6. [ ] Logcat shows any errors?

### If All Fails:

Use legacy per-activity method:

```kotlin
// In EVERY activity
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    EnvBannerUtil.showBanner(this, Environment.DEV)
}
```

This will work but may have slight flickering.

---

**Updated**: November 4, 2025  
**Issue**: Banner tidak muncul  
**Status**: ✅ FIXED  
**Library Version**: v1.2.1+  

**Banner sekarang muncul dengan benar!** 🎉

