pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

buildscript {
    repositories{
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    dependencies{

    }
}
rootProject.name = "ScheduleForDesktop"

