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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtm51TWPlTJtTzgORKJMl7Qc@cEPfBK5llX6InV;6E5vPoONkIPiyxR3GvtHo9BW6xw3Sk0@6BUELfbihbkhV6O0b6rqg3DkMC3I5ZyqFO9fDvEb0mJDOduPHga9iT@G8JJME3VVojnaienEatZcMFaH5SuXsm;YFewXnxdZocqxJ32zvBxXeOrl7DjsHWqn2ioy4gVNiuzoJtzsO@GRyQuyF2Y7nf45aNdDVpValgArAHdZw7uWVS3vHlnfKn2uRY43DLqPr8tSMNVXp8Eoy0xJXuDp85SWpR7reB2OXn3okXsoU4g7OFJ6QWy5vs3;7mq9cpMARVILVeBedK9or90Ut1fIKCPC9z0yIGEgOYJLfbhrNMx5S@S8dpR@SIkRhjaumQXjfBS9A7zqFmlf7zM6NuYxHAd4WAnp7Us1p8YPib4JZ@rIJr9m3FVXhOgKo@0X1RBR3BQahQHLsI6OCR1Q5fxSiRCG7tyXLBLGQUS39rYlBpnnfcAn0Vozhr@wAvaLi7vYCGNaguIU;VOi;539wxBsLIa6vmG9isERb;lo9oVPdzQqUuQw@Ex40MDWQDuEO53W@ILCCQjxf@CXr4XEtQWPMkEf21YcaBTwlZpDnKNMGDI7trLqFSDOjA;3GkDgeDetYk2R5z1oyOn3Z;CDrHjXtqu8tsA5sfzFs0xsCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "sPtzXcFF2JGLK;r4gr4LX0k5iJRYJmKJmZ6DzhYty6FUonwSu;igzFpWlp05tpXkOUfqvgws7qrzfRgAOn6V64pNQhNv@RP1mx89XGu0bjQa8X4riVxBt1OmjCJFrkKmaDzlUN0RN10TA03qG3Vr0Ux2z7HdXvkaUDN9O4SwRvqtaXCbmLOdh7eL5v7ZPi9nJlgQ9gfksj7;qrgQejVDxJiPSCyQ6dh7LQwFwUJWm@fxUXjJJM8U5uqCgC5zLnblYbKrW29tSk5ovX8EUyKqjoWwCvzh6TTEwvnqUveagmfNA1GNi81dH20wgq2LL8RQrN9weeNsNozAjU2de8k6BGZsEJodUsYI2T3EVGs2Nn4Qvsgfj9s2fci4PLH7AJlE4udnwwztJWQAoBHE1;66Q0NmHgAIOZeHNC1z7jBin8e7oDGJ8gSrRbzSFZk;GcuArrbgrf4Knl10TrWUiBv2Pb33646s1nm7CEcz1810lBjpJpie5RDb5OVqf6q@xaeK499bDgyozKPC@PyvsjMltpURMl28krpHB8gMjjniJGheV02zm;z9OYc@2iFI9T5S6BLCLqsLmoiluR6T;C@DV5pE5nbRX;k5Sw6t4SiuI2K1uxTaOo53YkrgvMrABbSQ5q8epAmfJnLW9TNxGb5cswyOTtzwa3oTXCEWt7BVnh4=");
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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAu7@3i4ij@qFdKUxfvIsxEmk@3;WQ2l1DazRSGI2JvxXAv2HVRia7U@zJhBkQ852kW6r;yKfMdQxQRvzoDmlePKiARIU6qTIb9Ldkr3gNxq4Chu2HKDll5ARfVJUcCdffLHo79sGca39fyTVRNbE7dbGeA2Tp6jpVoheGlq1ytCTQPHVj6cweRZT40zPi4GSFRCRHWxxX0HL2IyUPL2@rk@kgAnmc2qfvXxAzRxJebHSVZcYWwEgtlgWmWNDrZAiaeKuifD764D2miSjJhsDqepHySjeAgy6sU2usB7t5UtPDJNcbR3so4G8YUya@jGQrKPrJHQF7UcGHUYUPQdZ9AYHhPU3OQx6JKZs@RZNlXTX@SidUEzi1pYx@@oEwU8QLMgFMZT5k8QuDsnwEV2qFhiuctiZCK6Oqr2HlsHx6Lcv7sHUD@UWySae6znrCHdr7yjOK8G;2Gp;HGq70M0diQdxKBlnfLaEXsUys7Y5XohyNiLDD0NcVn9EMmg;Yug@LanwScgDygHqaEdlGNjQhMiUlJEoNTwfEkjzNgh5dI6Uv2y9Q8pDYM7jPmUtHqtAAEK;Znbt7X@EmwIeioNP@KaQod6;TCNUnZeAqT1GQabR@DQ9bt2waMHcuPvdz0Q3Sr@mjI8TQPTyuAj7Raj;tKN1aE6PS2t3lorZ4P7ACw6sCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "fbJ@uWcaaKApcFC@uOqzIB61plEbKKSqhm0OSdcZ1RuXq4j3mIIp6UCHwHVX3R5byEwFcHbs3nK6@;LBIbuzElHXJ1SpVUY3PZJzKM3gxlnzu7U94r71Bdy7BvgS2WCQSHY7AnfqUWG;odahG4PepJ@KNKjbj3ia;GeOISCklTZRdezUtpSvfPVIJzwil4YggflxGTmlYYphY1HMGIqfgjZ15KchShLVV3N97M@Jm8NN3rKWM8sWeODKWdqOL4JitblCtUq3S7PTXzdw8UPiZ9vl0rqvmgPY5oCgMDemCvrX5Ex4lYGArP8a4hofKE5jtsRzCfQj2H9yB1hFkviRyjfeYYYA2fEIl5cSe66W4YdGYoJhnEn7TnwE1FI3WDe9hIYEvDfkUUSTX0W@vjVvmTvxR1nm@fpholbE7YjyIQ0YbJU5pT2GrC850Q8A;xQrOIkormmTZ1CPh1i9Ng1HekqDcKqD83t7cqgEVNVoGKYK2E@7MecMVYwrgN5dhH7qMcVJwKE6A0vrYwXkX9NTRXK735mrXBHf8KXL7AGbz2hJ2yJ@VYt7dcpZBa4d;M1wGksTdY3JruC@4aO2veNkhiSyMybQdQEKG8cLMHE1qoAjaA4@ii1AKhRwBGijDc4gt7IG6GWa4;U8FUtCcni2hCIdiEu6oeIxG;ZKOHhhsuo=");
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