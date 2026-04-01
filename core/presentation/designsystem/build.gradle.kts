plugins {
    alias(libs.plugins.runtracker.android.library.compose) // using a library compose plugin as we dont need to include any other dependencies for it
}

android {
    namespace = "dev.saketanand.core.presentation.designsystem"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material3)
}