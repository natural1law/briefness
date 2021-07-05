#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

#预校验
-dontpreverify

#混淆时是否记录日志
-verbose

# 忽略警告
-ignorewarnings

# 保留support下的所有类及其内部类
-keep class android.support.* {*;}

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留在AppCompatActivity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-optimizations !code/simplification/cast,!field/*,!class/merging/*

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable *;
  public static final ** CREATOR;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#Natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}


#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
#    public static *** e(...);
}

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
# 保留类不被混淆
-keep public class com.androidx.reduce.use.*{*;}
-keep public class com.androidx.reduce.tools.Convert.**{*;}
-keep public class com.androidx.reduce.tools.Regular.**{*;}
-keep public class com.androidx.reduce.tools.Storage.**{*;}
-keep public class com.androidx.reduce.tools.This.**{*;}