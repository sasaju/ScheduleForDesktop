import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0-alpha04"
    id("com.google.devtools.ksp") version("1.6.10-1.0.2")
//    id("com.squareup.sqldelight") version "1.5.3"
}
group = "me.lifly"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val exposedVersion: String by project
val decomposeVersion = "0.5.1"
val ktor_version = "2.0.0-beta-1"
val objectboxVersion = "3.1.1"

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.0")
    implementation("org.jetbrains.compose.material3:material3:1.1.0-alpha04")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.1.0-alpha04")
    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
    implementation("com.squareup.moshi:moshi:1.13.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation ("io.github.microutils:kotlin-logging:2.1.21")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:3.36.0.2")
//    implementation ("com.squareup.sqldelight:sqlite-driver:1.5.3")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            includeAllModules = true
            packageName = "ScheduleDesktop"
            packageVersion = "1.0.2"
            linux{
                iconFile.set(File("ic_launcher-playstore.png"))
            }
            windows{
                dirChooser = true
                shortcut = true
                perUserInstall = true
                iconFile.set(File("ic_win.ico"))
            }
        }
    }
}