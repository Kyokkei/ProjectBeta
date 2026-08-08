# Keep project-specific ProGuard rules here when release shrinking is enabled.

# Keep Room entity classes (needed for DB reflection)
-keep class com.roulete.chastity.**Database* { *; }
-keep class com.roulete.chastity.**Dao* { *; }
-keep @androidx.room.Entity class * { *; }

# Keep Kotlin serialization / data classes used in AppStore JSON
-keepclassmembers class com.roulete.chastity.* {
    <fields>;
}

# Compose internals
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
