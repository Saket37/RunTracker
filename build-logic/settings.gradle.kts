dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    // as we need to identify the version catalogs
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")