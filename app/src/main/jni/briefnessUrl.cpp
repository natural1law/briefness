#include <jni.h>
#include <string>

extern "C"
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCInconsistentNamingInspection"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_url(JNIEnv *env,
                                                                         jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$AES");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decrypt",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF("5BD1D62642CCF36D21C7EBB4EF0A3511");
  jstring value = env->NewStringUTF(
      "6519725463604244D83F0D86A97230050C8D3E9ABE66962F6830A57520662FCC817106B48C491EC5F58528D1D75D223D8C31FE25DB947F1949FDF81CFB32123C");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_url1(JNIEnv *env,
                                                                          jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF(
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA0hgbaogCUo5MC8zHaTV938Q2cCbvp76WxnfmO8mfeMqLwWg9fbh5A@OIDmeV1Zixyn06NAZEXquX6uoQBlJ3uugUMyuaXNPEhRkQIQsruTJOlKaUN2h0ffsye8D6n4ItKxp6vrgstBoSlku3IlCHaTd1W3H8qiloxvKoSehvPEEEMoW9TmRui0@8Dg6MyOEZgLPqRtsXw@IMBp@vRVKZY2JOOLbROizBCXW8itfgdfCfJu3x8uK;WWd2DaY9JbRYQbuD4IzgutbJfWWmAkNdUG8zMBkrGmUa7vhiRvqSjRZrNnkd9;zmqu;otJcZvagaOBhtgDSyFKm9xzS9fznK35iSu9yJjRcbCoHKVVDQUIFoGXOFBzSguMCgTJuCYYhIiIunwDsw6CLCrcF2mpORq@WUewj@J@lS7ezNoQ;bBb;W07CRjqOl0ymc@mS1fYRNgJUvgnmc1igg9HHmlC69jkNW4s5P@SYFIxpuDn5cX2C39bOPkN3WYGqUvrKz8V6N9C2lDCjFwhQZ1@KbTkZf@1T;8gUVpZzkgsvrDPPuYJm42lYGJKxXBdPKDBjiatWDAh@4JroMoVBgx3@Enp8TKCvGSh;9s36of2ZuEcMDGRE9Q17z99WyXRlRms@QgRTjYO@rLGdjaBRVIHamQ5nZjCONePZUrCn@81xTrwiLndkCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "YGdZ5HY4QWaN0xKcNLkqlqtPEY4sQ7lzDCrfNLUJLovBzDspVfTjQ;4FsmAOu2yuAWEo50esqdum1XPtj3oTNhRL1YY@Wx70j;AxTbx;XCnMCrMi;9GRCCgL;9Qmn6xO7kdER654gMr8kblDZPmWtM8asjlkVVRlt3nB08sBdW2s4zKiepDNYwA6LtrU8RQ4sSg1xoDkeOdqKTmoX1jXYojw92sNeXwiUfmlS25jX9A5abGCsaPmdNThEfdUtfBzVMUuKkZ8INV9TA2WkCjxvfdfC@M4POIjKcgj3ObXy9s8iRDXE4isCr0Ga;DPSdN2jf7342MTsFO6YGeAbsDTdli7zPUmYk7E7WENy;1iaVWWuQddqIwHOy6MS1gETst1RQuCTCyUkhaNtt1tDQB;Sg60XXSNtk;T97s;ssNm8Sz;dolBV4LWT7MDbp6gc7Grcjbqxp1lT5TCMh5q5P@nxUMeBmfqHOYnWOIBz@DWXfcywI4cPf9ZRdwTLVMii7TvbaYhp7wD3BWATrq@Uu2YyDq1Iu5E;XWhbHxcZL2ih5iJayTA1N6qoNtcM0cz3w86UrDaArHJqGVBnlomGyougfiy51@riCYCuPmV7T1X6;BUp79R5PnCqmrPW85DP2I9o7LYVCGFO71bLknO;AUYWGY40xdH94ZeOY0C889ET00=");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_wsUrl(JNIEnv *env,
                                                                           jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF(
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtgPeK2AFPrxoD4N6Qmnx7lhbDPHwVrRgGRV3MyJtqYHLXTgU8Au5e4tV@LFFCes1Kb4wkOCdXvWXlx7fCSG6ntndwQBy7xXkLnenXqNb1RVScG6ZsglJzx8XZpw3PjPA9tzCM5nyNiLFMhvrPusRwOciVervL5jjuPOKb4TW1YADuHlciUcFIWndCsUbOVaCAxlaR4Xq9UTdtAq87yTrdu;M;MQDczwjnQgkIV5Wff1C6Kv2g6fF1gOhr1Saescc2DROp17JMzKm3qxspurfeoeERKaSe6kZQehI0r3xrBjBdqUXsJwnrF5ESoIf5cQxuHeTUM3UgPXTjJ21iQzAqHQZa17cf8x6J32GAqE81kmLA6ukXKir1Sw;iiDzhOGkEm5eBNZ1wIhhjy4Gn6ymqNfyNF3H1uSedPNCEg0CH65gDk6kBhomstHWhu59RAWxyJXcm6dY;zh7Ft8i@2cbgOP0OgmxVLE30xKXXhR7rL6hJkwq5;v;NrVTottFBk547VyVt06x9uxzvVtUMcMD8kfdBYly55Rf76@BWvyorMUIhYJSZfKce7TZ4z1OtPap1Pwa5Rf3K1zBC;2WX9LDmwfdNzqJztyVtkk@GvzB4Prgx@ceseu357G6mKioefyPCtpM6qOr@yDyBATijZ198ZjXLg709lY4RKTkoRUZM2sCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "K;rkVXHDx2HEPzs6ovBHxQKkN57Qb36S3i;Bv7aOGddPOs;FuFjXOW4XAs9O8Lc51nZkZ40doH9evs0xxBTJM3mq9oeY0pfPxLVRhGK8tM@YPZdIJrl9PaXPeRhy223;XqP7KYlNjyelzaosF;jOJwHg2hORZ3o74U2fPlYwUMRUWKKXFkah6t5Gd92T2bLduYGfwdKjVIz4qvJ8hnAKeZu4xKGvc9dAUKAJi5Nf81@VyEAo2STzGchczqR3@HmV9URzswl1vplvSxokLRI12JH04yGefnOpmCox@8JMl@edjmsFTyg4droPvGA4VwBq7vBJ391LJc0A9Sa6RhrcRPeR5ady6sOqYe5yNDZ4UmdbqYz2B7Kr6sFA1zt1Rs5nlAvmOX9dXNaJgW961tJe@V;yJl8aEp21Ye2nvVYZpr9xCkdMzLaEjgnbOJWry1vAQNvupOgVQ7e9e1s5gUMpBXunWAGdguXc8;rbVB46Z6d7zrILC8v8RAuZyKmZd0zuFvqm5IgyGsl2H2m1xXO3RreRb6GcBVuTDTl43qwKlQLOpi1MKSf8rXJf@zhRjW6xAuQTmQP1adpFvQREh5sgZpMwX9XR77iNMSZEiue9blq0Drq@aUf0I8xUEEUIshgvYTi06sjZit5pCt9jBqraE;2ojUOuzjksKsIGmp8sj9U=");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_rsa(JNIEnv *env,
                                                                         jclass /* this */,
                                                                         jstring key,
                                                                         jstring value) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "encryptPublic",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_RefreshActivity_url(JNIEnv *env, jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$AES");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decrypt",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF("5BD1D62642CCF36D21C7EBB4EF0A3511");
  jstring value = env->NewStringUTF(
      "6519725463604244D83F0D86A97230050C8D3E9ABE66962F6830A57520662FCC817106B48C491EC5F58528D1D75D223D8C31FE25DB947F1949FDF81CFB32123C");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}