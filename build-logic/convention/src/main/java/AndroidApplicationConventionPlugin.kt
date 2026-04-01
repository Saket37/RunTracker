import com.android.build.api.dsl.ApplicationExtension
import dev.saketanand.convention.ExtensionType
import dev.saketanand.convention.configureBuildTypes
import dev.saketanand.convention.configureKotlinAndroid
import dev.saketanand.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
//                if (!hasPlugin("org.jetbrains.kotlin.android")) {
               // apply("org.jetbrains.kotlin.android")
//                }
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }
                configureKotlinAndroid(this@configure)
//                configureBuildTypes(
//                    commonExtension = this,
//                    extensionType = ExtensionType.APPLICATION
//                )
            }
        }
    }


}