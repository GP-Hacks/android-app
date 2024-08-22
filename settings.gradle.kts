pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TatarstanResidentCard"
include(":app")
include(":feature")
include(":feature:chat_bot")
include(":core")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":feature:home")
include(":feature:services")
include(":feature:news")
include(":feature:stocks")
include(":feature:places")
include(":feature:charity")
include(":feature:portal_care")
