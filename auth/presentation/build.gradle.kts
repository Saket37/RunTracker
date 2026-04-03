plugins {
    alias(libs.plugins.runtracker.android.feature.ui)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.saketanand.auth.presentation"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(projects.core.domain)

    implementation(projects.auth.domain)
}