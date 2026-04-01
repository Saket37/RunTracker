import com.android.build.api.dsl.LibraryExtension
import dev.saketanand.convention.ExtensionType
import dev.saketanand.convention.configureBuildTypes
import dev.saketanand.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
//                if (!pluginManager.hasPlugin("org.jetbrains.kotlin.android")) {
//                    apply("org.jetbrains.kotlin.android")
//                }
            }

            extensions.configure<LibraryExtension>(){
                configureKotlinAndroid(this)
                configureBuildTypes(this, extensionType =  ExtensionType.LIBRARY)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }
}