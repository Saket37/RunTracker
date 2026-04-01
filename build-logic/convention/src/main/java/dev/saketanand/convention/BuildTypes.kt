package dev.saketanand.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures.apply {
            buildConfig = true
        }
        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }

                        release {
                            configureReleaseBuildType(apiKey, commonExtension)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)

                        }

                        release {
                            configureReleaseBuildType(apiKey, commonExtension)
                        }
                    }
                }
            }
        }

    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"http://192.168.0.14:8080\"")

    manifestPlaceholders["networkSecurityConfig"] = "@xml/network_security_config"
}

private fun BuildType.configureReleaseBuildType(apiKey: String, commonExtension: CommonExtension) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"http://192.168.0.14:8080\"")

    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}