plugins {
    alias(libs.plugins.runtracker.android.library)
}

android {
    namespace = "dev.saketanand.core.data"

}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.core.database)

}