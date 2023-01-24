import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
    id("com.google.devtools.ksp") version("1.6.10-1.0.2")
//    id("org.openjfx.javafxplugin") version "0.0.12"
}
group = "me.lifly"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val exposedVersion: String by project
val decomposeVersion = "0.6.0"
val ktor_version = "2.0.1"
val objectboxVersion = "3.1.1"
dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.1")
    implementation("org.jetbrains.compose.material3:material3:1.1.1")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
//    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
//    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

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
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("cafe.adriel.voyager:voyager-navigator-desktop:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-kodein-desktop:1.0.0-rc02")
    implementation("net.java.dev.jna:jna:5.12.1")
    implementation("net.java.dev.jna:jna-platform:5.12.1")
//    implementation ("com.squareup.sqldelight:sqlite-driver:1.5.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
buildscript {
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.2.1")
    }
}
//val obfuscate by tasks.registering(proguard.gradle.ProGuardTask::class)
//
//fun mapObfuscatedJarFile(file: File) =
//    File("${project.buildDir}/tmp/obfuscated/${file.nameWithoutExtension}.min.jar")

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "ScheduleDesktop"
            packageVersion = "1.0.6"
            modules("java.sql")
            linux{
                iconFile.set(File("ic_launcher-playstore.png"))
            }
            windows{
                dirChooser = true
                shortcut = true
                perUserInstall = true
                menu = true
                upgradeUuid = "9E846EA8-869E-4384-9485-5A71876AF67F"
//                upgradeUuid = "9E846EA8-869E-4384-9485-5A71876AF67T"
                iconFile.set(File("ic_win2.ico"))
            }
        }

        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
//        disableDefaultConfiguration()
//        fromFiles(obfuscate.get().outputs.files.asFileTree)
//        mainJar.set(tasks.jar.map { RegularFile { mapObfuscatedJarFile(it.archiveFile.get().asFile) } })
    }
}

tasks.register<proguard.gradle.ProGuardTask>("obfuscate") {
    val packageUberJarForCurrentOS by tasks.getting
    dependsOn(packageUberJarForCurrentOS)
    val files = packageUberJarForCurrentOS.outputs.files
    injars(files)
    outjars(files.map { file -> File(file.parentFile, "${file.nameWithoutExtension}.min.jar") })

    val library = if (System.getProperty("java.version").startsWith("1.")) "lib/rt.jar" else "jmods"
    libraryjars("${System.getProperty("java.home")}/$library")

    configuration("proguard-rules.pro")
}