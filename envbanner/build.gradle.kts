plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.fy.envbanner"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.8"
        languageVersion = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.fadhyyusuf"
                artifactId = "envbanner"
                version = "1.0.2"

                pom {
                    name.set("Environment Banner")
                    description.set("Android library to display environment banners for easier testing")
                    url.set("https://github.com/fadhyyusuf/envbanner")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("fadhyyusuf")
                            name.set("Fadhy Yusuf")
                            email.set("your.email@example.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/fadhyyusuf/envbanner.git")
                        developerConnection.set("scm:git:ssh://github.com/fadhyyusuf/envbanner.git")
                        url.set("https://github.com/fadhyyusuf/envbanner")
                    }
                }
            }
        }
    }
}
