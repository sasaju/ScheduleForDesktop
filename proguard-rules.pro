# proguard-rules.pro
-dontoptimize
-dontobfuscate

-dontwarn kotlinx.**

-keepclasseswithmembers public class MainKt {
    public static void main(java.lang.String[]);
}
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
#-keep class kotlin.coroutines.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class kotlin.jvm.** { *; }
-keep class kotlin.** { *; }
-keep class okio.** { *; }
-keep class com.lifly.schedule.desktop.logic.model.**

#-keep class com.squareup.moshi.** { *; }
#-keep interface com.squareup.moshi.** { *; }
-dontwarn okio.**
-dontwarn com.squareup.moshi.**
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.* { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn java.d