# ref: https://github.com/android/architecture-samples/blob/views-hilt/app/proguardTest-rules.pro
# Proguard rules that are applied to your test apk/code.
-ignorewarnings
-dontoptimize

-keepattributes *Annotation*

-keep class androidx.test.espresso.**

-dontnote junit.framework.**
-dontnote junit.runner.**

-dontwarn androidx.test.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**