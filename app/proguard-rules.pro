# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

# 사용하지 않는 메소드 유지
-dontshrink
-dontoptimize

# 나중에 문제 발생 시 확인하기 위한 Mapping 파일
-printmapping map.txt
-printseeds seed.txt
-printusage usage.txt
-printconfiguration config.txt

# Exception 처리
-keepattributes Exceptions

# 소스 파일 변수 명 바꾸는 옵션
-renamesourcefileattribute SourceFile
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

-keepparameternames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,MethodParameters

# Annotation
-dontwarn **.annotation.**

-dontwarn com.ozcoin.cookiepang.**