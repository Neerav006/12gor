# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\USER\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn okio.**

-keep class com.itextpdf.text.** {*;}

-keep class javax.annotation.** { *; }
-keep class java.io.IOException
-dontwarn javax.annotation.**

-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
-keep class com.itextpdf.text.** { *; }
-keepclassmembers  class *  {
    @com.itextpdf.text *;
}
-keepattributes EnclosingMethod
-keepattributes InnerClasses
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**
-dontwarn com.google.gson.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.**
-dontwarn com.squareup.okhttp.internal.huc.**
-dontwarn com.google.android.gms.internal.zzhu
-dontwarn  com.itextpdf.text.pdf.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.google.android.gms.**


# proguard new rule

