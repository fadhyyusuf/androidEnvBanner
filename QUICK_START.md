# Quick Start - Publishing to JitPack

## ✅ Configuration Complete!

All JitPack configuration files have been created and tested successfully.

## 📋 Checklist Before Publishing

- [x] Maven publish plugin added
- [x] Publishing configuration created
- [x] `jitpack.yml` created
- [x] `LICENSE` file created
- [x] README updated with JitPack instructions
- [x] Local build tested ✅
- [ ] **TODO: Update GitHub username in build.gradle.kts**
- [ ] **TODO: Create GitHub repository**
- [ ] **TODO: Push code to GitHub**
- [ ] **TODO: Create release tag**
- [ ] **TODO: Build on JitPack**

## 🚀 Next Steps

### 1. Update GitHub Username

Edit `envbanner/build.gradle.kts` and replace `fadhyyusuf` with your actual GitHub username:

```kotlin
groupId = "com.github.YOUR_GITHUB_USERNAME"  // Line 53
```

Also update in:
- Developer information (line 65)
- SCM connection URLs (lines 71-73)

### 2. Create GitHub Repository

```bash
# Go to https://github.com/new
# Create repository named: envbanner
# Make it PUBLIC
# Don't initialize with README
```

### 3. Initialize Git and Push

```bash
cd /Users/fadhyyusuf/AndroidStudioProjects/envbanner

# Initialize git if not already done
git init

# Add all files
git add .

# Commit
git commit -m "Initial release: Environment Banner Library v1.0.0"

# Add remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/envbanner.git

# Push
git branch -M main
git push -u origin main
```

### 4. Create Release Tag

**Option A: Via GitHub Web (Recommended)**
1. Go to: `https://github.com/YOUR_USERNAME/envbanner/releases`
2. Click "Create a new release"
3. Click "Choose a tag"
4. Type: `1.0.0`
5. Title: `v1.0.0 - Initial Release`
6. Description: Describe features
7. Click "Publish release"

**Option B: Via Command Line**
```bash
git tag -a 1.0.0 -m "Release version 1.0.0"
git push origin 1.0.0
```

### 5. Build on JitPack

1. Go to: https://jitpack.io
2. Enter: `https://github.com/YOUR_USERNAME/envbanner`
3. Click "Look up"
4. Find version `1.0.0`
5. Click "Get it"
6. Wait for build (green checkmark ✅)

### 6. Use in Projects

Once built on JitPack, anyone can use your library:

**Add to `settings.gradle.kts`:**
```kotlin
maven { url = uri("https://jitpack.io") }
```

**Add to `app/build.gradle.kts`:**
```kotlin
implementation("com.github.YOUR_USERNAME:envbanner:1.0.0")
```

## 📝 Files Created

```
envbanner/
├── envbanner/build.gradle.kts    # Updated with maven-publish
├── jitpack.yml                    # JitPack build config
├── LICENSE                        # MIT License
├── JITPACK_GUIDE.md              # Detailed publishing guide
├── QUICK_START.md                # This file
└── README.md                      # Updated with JitPack badge
```

## 🔍 Verify Configuration

Test local build:
```bash
./gradlew :envbanner:build
./gradlew :envbanner:publishToMavenLocal
```

Check output:
```bash
ls ~/.m2/repository/com/github/fadhyyusuf/envbanner/1.0.0/
# Should see: envbanner-1.0.0.aar, .pom, .module
```

✅ **Local build successful!** Ready to publish to JitPack.

## 📚 Documentation

- **Detailed Guide**: See [JITPACK_GUIDE.md](JITPACK_GUIDE.md)
- **Main README**: See [README.md](README.md)
- **Groovy Setup**: See [GROOVY_SETUP_GUIDE.md](GROOVY_SETUP_GUIDE.md)

## 🎯 Quick Commands Reference

```bash
# Build library
./gradlew :envbanner:build

# Publish to local Maven
./gradlew :envbanner:publishToMavenLocal

# Create git tag
git tag -a 1.0.0 -m "Release v1.0.0"

# Push tag
git push origin 1.0.0

# List tags
git tag -l

# Delete tag (if needed)
git tag -d 1.0.0
git push origin :refs/tags/1.0.0
```

## ⚠️ Important Notes

1. **Repository must be PUBLIC** for free JitPack
2. **First build** may take 5-10 minutes
3. **Update version** in `build.gradle.kts` for each release
4. **GitHub username** must match in all config files
5. **Tag format**: Use semantic versioning (1.0.0, 1.0.1, etc.)

## 🆘 Troubleshooting

### Build fails on JitPack
- Check build log on JitPack
- Verify `jitpack.yml` syntax
- Test locally first

### Can't find library
- Check JitPack badge is green
- Verify correct groupId/version
- Check JitPack repository is added

### Wrong GitHub username
- Update in `envbanner/build.gradle.kts`
- Rebuild and push new tag

## ✨ Success Criteria

- ✅ Local build successful
- ✅ `publishToMavenLocal` works
- ✅ Files in `~/.m2/repository/`
- ⏳ Code pushed to GitHub
- ⏳ Release tag created
- ⏳ JitPack build successful
- ⏳ Library available for use

---

**Ready to go!** Follow the steps above to publish your library to JitPack. 🚀

