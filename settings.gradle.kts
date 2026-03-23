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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Coinollar"

include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:designsystem")
include(":data")
include(":domain")
include(":feature:home")
include(":feature:detail")
include(":feature:splash")
