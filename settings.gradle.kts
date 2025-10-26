pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // You can also add gradlePluginPortal() here if needed for dependencies
    }
}

rootProject.name = "SmartPocketPrototype"
include(":app")