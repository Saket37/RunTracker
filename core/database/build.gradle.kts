plugins {
    alias(libs.plugins.runtracker.android.library)
}

android {
    namespace = "dev.saketanand.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)

}