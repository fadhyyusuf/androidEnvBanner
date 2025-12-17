# Release Notes - Version 1.0.4

## 🐛 Bug Fixes

### Theme Compatibility Fix
**Fixed Critical Theme Compatibility Issue** - The library now works with ALL Android themes, not just Material Components themes.

#### Problem
Previously, the library would crash when used in apps that don't inherit from `Theme.MaterialComponents` with the error:
```
java.lang.IllegalArgumentException: The style on this component requires your app theme to be Theme.MaterialComponents (or a descendant).
```

#### Solution
- **Added internal Material theme wrapper** - Created `EnvBanner.MaterialTheme` in the library's resources
- **Wrapped inflation context** - Modified `EnvBannerUtil.kt` to use `ContextThemeWrapper` when inflating the banner layout
- **Added ProGuard rules** - Ensured theme resources are protected in consumer apps

#### Impact
✅ **Universal Compatibility** - Now works with:
- `Theme.AppCompat` and derivatives
- `Theme.Material3` and derivatives  
- `Theme.MaterialComponents` and derivatives
- Legacy Android themes
- Custom themes

#### Technical Details
The fix wraps the activity context with a lightweight Material Components theme specifically for banner inflation. This ensures the banner can be created regardless of the parent app's theme, without affecting the rest of the app.

#### Migration
No code changes required! Simply update to version 1.0.4:
```gradle
implementation 'com.github.fadhyyusuf:envbanner:1.0.4'
```

---

## 📝 Files Changed
- `EnvBannerUtil.kt` - Added `ContextThemeWrapper` for theme isolation
- `res/values/themes.xml` - Created library-internal Material theme
- `consumer-rules.pro` - Added ProGuard rules for resource protection
- `build.gradle.kts` - Version bump to 1.0.4

## 🙏 Thanks
Special thanks to the community for reporting this issue!

---

**Release Date**: December 17, 2025  
**Full Changelog**: [v1.0.3...v1.0.4](https://github.com/fadhyyusuf/envbanner/compare/v1.0.3...v1.0.4)

