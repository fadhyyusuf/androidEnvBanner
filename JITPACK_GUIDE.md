# Publishing to JitPack - Complete Guide

## Prerequisites

1. GitHub account
2. Git repository for this project
3. GitHub repository must be public (or JitPack subscription for private repos)

## Step 1: Prepare Your Repository

### Initialize Git (if not already done)

```bash
cd /Users/fadhyyusuf/AndroidStudioProjects/envbanner
git init
git add .
git commit -m "Initial commit: Environment Banner Library v1.0.0"
```

### Create GitHub Repository

1. Go to https://github.com/new
2. Create a new repository named `envbanner` (or your preferred name)
3. **Important:** Make it **Public**
4. Don't initialize with README (you already have one)

### Push to GitHub

```bash
# Replace with your GitHub username
git remote add origin https://github.com/YOUR_USERNAME/envbanner.git
git branch -M main
git push -u origin main
```

## Step 2: Create a Release/Tag

JitPack builds your library based on Git tags or releases.

### Option A: Using GitHub Web Interface (Recommended)

1. Go to your repository on GitHub
2. Click on **Releases** → **Create a new release**
3. Click **Choose a tag**
4. Type: `1.0.0` (or any version you want)
5. Set release title: `v1.0.0`
6. Add release notes (optional)
7. Click **Publish release**

### Option B: Using Git Command Line

```bash
git tag -a 1.0.0 -m "Release version 1.0.0"
git push origin 1.0.0
```

## Step 3: Build on JitPack

1. Go to https://jitpack.io
2. Enter your repository URL: `https://github.com/YOUR_USERNAME/envbanner`
3. Click **Look up**
4. Find your version tag (1.0.0)
5. Click **Get it** button
6. JitPack will start building your library (this may take a few minutes)
7. Wait for green checkmark ✅ indicating successful build

## Step 4: Use the Library

Once JitPack build is successful, you can use it in any Android project:

### For Kotlin DSL (build.gradle.kts)

**Project level - `settings.gradle.kts`:**
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

**OR in root `build.gradle.kts`:**
```kotlin
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**App level - `app/build.gradle.kts`:**
```kotlin
dependencies {
    implementation("com.github.YOUR_USERNAME:envbanner:1.0.0")
}
```

### For Groovy DSL (build.gradle)

**Project level - `settings.gradle`:**
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

**OR in root `build.gradle`:**
```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**App level - `app/build.gradle`:**
```groovy
dependencies {
    implementation 'com.github.YOUR_USERNAME:envbanner:1.0.0'
}
```

## Configuration Files Created

The following files have been configured for JitPack:

### 1. `envbanner/build.gradle.kts`
- Added `maven-publish` plugin
- Configured publishing with Maven coordinates
- Set groupId: `com.github.YOUR_USERNAME`
- Set artifactId: `envbanner`
- Set version: `1.0.0`

### 2. `jitpack.yml`
- Specifies JDK version (OpenJDK 17)
- Build instructions for JitPack

### 3. `LICENSE`
- MIT License file (required by JitPack and good practice)

## Publishing Updates

### To publish a new version:

1. Make your changes
2. Commit changes:
   ```bash
   git add .
   git commit -m "Update: describe your changes"
   git push
   ```

3. Create new tag:
   ```bash
   git tag -a 1.0.1 -m "Release version 1.0.1"
   git push origin 1.0.1
   ```

4. Go to JitPack and look up your repo again
5. The new version will appear automatically

## Version Naming

You can use different versioning schemes:

- **Release tags**: `1.0.0`, `1.0.1`, `2.0.0`
- **Commit hash**: Use the commit SHA (e.g., `a1b2c3d`)
- **Branch name**: `main-SNAPSHOT` for latest from main branch

### Using Latest from Branch

```kotlin
// Always get latest from main branch
implementation("com.github.YOUR_USERNAME:envbanner:main-SNAPSHOT")
```

## Troubleshooting

### Build Failed on JitPack

1. Check JitPack build log (click on the version tag)
2. Common issues:
   - Wrong Java/JDK version → Check `jitpack.yml`
   - Missing dependencies → Check `build.gradle.kts`
   - Compilation errors → Test locally first

### Test Build Locally

Before publishing, test the build:

```bash
./gradlew clean :envbanner:build
./gradlew :envbanner:publishToMavenLocal
```

### Check Local Maven

After `publishToMavenLocal`, check:
```bash
ls ~/.m2/repository/com/github/YOUR_USERNAME/envbanner/
```

## Update README.md

Don't forget to update your README.md with JitPack installation instructions:

```markdown
## Installation

Add JitPack repository:

**Kotlin DSL:**
```kotlin
maven { url = uri("https://jitpack.io") }
```

**Groovy DSL:**
```groovy
maven { url 'https://jitpack.io' }
```

Add dependency:

**Kotlin DSL:**
```kotlin
implementation("com.github.YOUR_USERNAME:envbanner:1.0.0")
```

**Groovy DSL:**
```groovy
implementation 'com.github.YOUR_USERNAME:envbanner:1.0.0'
```
```

## Badge for README

Add JitPack badge to show latest version:

```markdown
[![](https://jitpack.io/v/YOUR_USERNAME/envbanner.svg)](https://jitpack.io/#YOUR_USERNAME/envbanner)
```

## Important Notes

1. **Update GitHub Username**: Replace `YOUR_USERNAME` and email in:
   - `envbanner/build.gradle.kts` (groupId and pom.developers)
   - All installation instructions

2. **Version Numbers**: Update version in `envbanner/build.gradle.kts` when creating new releases

3. **Public Repository**: JitPack requires public GitHub repos (free tier)

4. **First Build**: First build on JitPack may take 5-10 minutes

5. **Cache**: JitPack caches builds. To rebuild, use the "Delete & Rebuild" option on JitPack

## Complete Example

After setup, users can install your library:

```kotlin
// In settings.gradle.kts or root build.gradle.kts
repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

// In app/build.gradle.kts
dependencies {
    implementation("com.github.fadhyyusuf:envbanner:1.0.0")
}
```

Then use it:
```kotlin
import com.fy.envbanner.EnvBannerUtil
import com.fy.envbanner.Environment

EnvBannerUtil.showBanner(this, Environment.DEV)
```

## Resources

- JitPack Website: https://jitpack.io
- JitPack Docs: https://jitpack.io/docs/
- Your Library on JitPack: https://jitpack.io/#YOUR_USERNAME/envbanner

---

**Ready to publish!** Follow the steps above to make your library available via JitPack. 🚀

