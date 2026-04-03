plugins {
    alias(libs.plugins.runtracker.android.feature.ui)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.saketanand.run.presentation"

}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.run.domian)
}