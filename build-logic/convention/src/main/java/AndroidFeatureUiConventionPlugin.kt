import com.android.build.api.dsl.LibraryExtension
import dev.saketanand.convention.addUiLayerDependencies
import dev.saketanand.convention.configureAndroidCompose
import dev.saketanand.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply("runtracker.android.library.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            dependencies{
                addUiLayerDependencies(target)
            }
        }
    }
}