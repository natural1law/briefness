#include <jni.h>
#include <string>

extern "C"
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCInconsistentNamingInspection"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_url(JNIEnv *env,jclass /* this */) {
    jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$AES");//调用java类
    //获取java方法ID
    jmethodID encrypt = env->GetStaticMethodID(cls, "decrypt","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    jstring key = env->NewStringUTF("5BD1D62642CCF36D21C7EBB4EF0A3511");
    jstring value = env->NewStringUTF("6519725463604244D83F0D86A97230050C8D3E9ABE66962F6830A57520662FCC817106B48C491EC5F58528D1D75D223D8C31FE25DB947F1949FDF81CFB32123C");
    //使用java方法并且返回string
    auto strResult = (jstring)env->CallStaticObjectMethod(cls, encrypt, key, value);
    const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
    std::string url = res;
    env->DeleteLocalRef(key);//释放string内存
    env->DeleteLocalRef(value);//释放string内存
    env->ReleaseStringUTFChars(strResult, res);//释放string内存
    return env->NewStringUTF(url.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_url1(JNIEnv *env,jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF(
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAt5EGnizVq6bDgq6t4xgOdaQcRNj1VZRkaIdUwJIRju@aFopWfjlO55;hnJrCE8iuKaNhnaC5t5fFkL3cba3yPrQVfIV3ab@yklK6LMlpwqr3wH0PxdEkRChOOPTldJ@a1T01dRyf3RQhY0y6;xalG40uVeDLnfxYXddXpN4QxD9bcPI;rnqW1bGmgMiYxhH99q2PhZO64NBGcqRRuKqs8H5ABR42e6WHVZw7OcI2nzenKe8eK5R2bLfrPrxVHiQj3g56gTfm@bIK8a4WSO8qIj@U38yEeLV5lpHlPfxqUzodZtFFid@FONb6ve6PBSWlZx0L44iYGK;Ons9TZF0TulUi2WdUCjdyc0Ik@eQBmttf5ZGPxPCIRylj8gPO1R713CDOjWPglfE;aTjdd;vjIrC9Q9KkRG49xDcUCTZjRhqlz4cqVbdZ8KHvNb3AY9pOJrtVR4y9muJRwHwgGhXU8sEZcM66t3;Ur29CNcCWWrGEWcZVj0Yc4koFWEBW3oWSSY0e0jFSa60gmDUsAbcDPCQT7ZztWMd6tdRDBwrr8d7friVPWKSnWKJzJQij1d6ULnH5RF4YNUgRDnjYbYD9Ii6tfkAybgIwLfeKNgwbGBCisFlXGg0ZUJKtyEq6RokRUHLxerkRWjuqclQ4N3wrAF9NUiJYOWc2VNJR6YNt9@ECAwEAAQ==");
  jstring value = env->NewStringUTF(
      "O9rEfSJ2J2b1oWtcJnqUJDwuovyBpWn3BFtgrlzqgEd9ATqHCKXq1N1L0h8s;2;FC@@dss0nHUKCHWoJxuqo8rwSqeZ6AdZ0gpfbD14h;UJeKcClRpJhBISQTgGraR8WIQ8Yw7u46zf5O;74Sn9FFa0tNI1dpqO1XxPp@wp6xvbnEXMH;OaZ4Sb2upf9nhjsgxfyhzMlmqUW6wuFEx5TQq76AIMLBRa8ru48VqeBgImJlhcaOKpAQICPjXjtKr7IMboR1QEok3bkOq1;1kEKmhutcI79Pv5t@TfcTiZ0G0yfRDyNQJdxQd8aBHTLQ5XFNVcm9JEPQmCuNQnQ17T0tu72P1DDG8wmatPyeeIvxc7voD4wgJZixrKr1AS161Ir11644eWq4QThH2Yk7fv;NUDxB4EExzVHV7yrkMp1M8;jN8VuEZj8qF24jACsaZrh4J57U5BkAKc3J@8h@iyOEGHPeAG130vMFLCxS9wn@rYmwoyHZpKU4N73r1WZQq7EfqZaLr7QgHr3joAl5daZjzT0CnIZl4ZuehNSJEkqDRIDSw2xPVTeRU8lIZ8tRmkZ3GcgRU92OvzOM9O0HBbAhrjMwwzcTX2acK@ZLUJaTyHTJ95gt3a@sFiAOMRz38BVlX26dfEMFAevp7vVzHjnKHGovlie4d0J8in;y7W2qpg=");
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
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_ios(JNIEnv *env, jclass /* this */) {
    jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
    //获取java方法ID
    jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    jstring key = env->NewStringUTF("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA0aUhs2m3Zmqy7w3ixJXbz1QGEjd+kPoxhmSn3SLJEhDT1Zfaf56aNt$wKiam$SO1asA+0K1rUNfz+yQr3rmbRNA5BQmNq2ynsSmO59DJQMstwDyX79UGmxJ+Ox+LgDfyuobqcm1GbrDq1xNSRrweE+yj59pHWGaUxF96GV+lU6v2dSA63aIbX1hL3R+sQwm+sZCgViNEetRhbc9UWouwRsOAQnXW$R9LgHciMi5qolio6KDyOL2sNW1R18mMZxwTVcy6aT3Mm9fm1B8uc9i2gX5SWtWj9j5KWAMvVNK7tu+B2gg6MpWrB9GtIwMfyhg1Lr0ze2cfNxbuevZE53JCTe5KZ1NJKn+8YckEYbrbBPVZ9ckusd3xLrk3IhlVj0OfEgZWpZME1rTmPy3ctq$CC3ip7OGcYe+yyBeozGsMun9P$RdWIC5OeM7d3$3lpREyTuJeAU+A8i0aP5kMzydF0cgg5X2x6cA7Kqz2vZOgBfxU3JTU6Q0YHmQ1GPiBwwFIMyzP5EwHGYB3PsUIaiVr8o+tBSPIbQNxcCQYDpAN+UDoBVmiURZ8W4iCjwaZ8taMKEg1gAvtlmH1XbR185C79Aqh0TBKTZEVR$a$EWDhLk89R4ju9mbVPhpVji7REE8kzYJXxEdtghPDkgZNWb2$Mxon3l3uc5s7s+MsVMcPmXsCAwEAAQ==");
    jstring value = env->NewStringUTF("iENDayzBa2WpP9TvDCNC2BqRVA$ybixOCq64gRWsHZr4FvF215qL8bJqOpR0CvYlMf9Iap1Co1B6oajXQzqUx9yWBwA5tsoC5SO6qTBXr9zRz10o9ncWyBdGHS5Rla2jmhlegsuzN$s1GnntXrnfzxVI+OLhSgOmRgT169a81E1MbxFmarpzxR2Mr+vT8CiXQzjotPVTdFkoo5eU5Z9DL$jv6LLMxboPUY4YxXuB+mvUpOV3UIDSk2OnETUYWia8Ldfe+RtDDAdWg2lwSw4uvw8alU4Bk8HKgXYt7NXZjX7EIkJ029RXgmc$ZzOWST6RqLG2qJQfFbEITc5dBwOURAoBcqvWFFTCNx+OF$aMe$GSWydC0JZ8Erqua1QbgwlPHa8Uv4CzacEvrZqRLrsYqJEY+umpw1gruGmGI55zNsFtrWOB4Cdf2jKvu0d9yzzNAr0hbUZGOe4FGdNaljOZuP0wW+n5XZMJuSmViftcBM3XKKY3qEz3aSxYV6AI3rgBVVGKvOGWDxmEecQxFLBoC5npgzA+FSe4lZ8Ituk8yUQyjSYuTRn42zxuuA1$MX4noi1nAezjPij6EAkbnbCNS9egxsMgc3kzFpl68TN49n5ywJGuNwrA9hUQBFROaFq5tUJY0R7avDMZXOuys1lwbWb$Uz9BcWA6caifQoviWs4=");
    //使用java方法并且返回string
    auto strResult = (jstring)env->CallStaticObjectMethod(cls, encrypt, key, value);
    const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
    std::string url = res;
    env->DeleteLocalRef(key);//释放string内存
    env->DeleteLocalRef(value);//释放string内存
    env->ReleaseStringUTFChars(strResult, res);//释放string内存
    return env->NewStringUTF(url.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_publicKey(JNIEnv *env, jclass /* this */) {
    return env->NewStringUTF("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAupWuETogvbzczyKe9L9KAREMZ6nRUq2y8xAfkAvM21OXrpAvEGWEi9gDAJdFErKTuJDsc8EKuTjPUX28PdqJws$j7qE4Q4RftxtjGwVUCKTsrhoB3lJXZI1$jdhoCErvKPLRVlClPlJTJhCcvZm+HModzbDPHTtuNiiz3W4iFcYxB9ndXWrgmTBZ8XwjjMYU9O3ADSXnSIwabtcCUFrkRNvcWUAu5bORT$Iu1LfRzlgJOzqk$o7lOul2ZplCeKjnEc53LqF7jBsp$ymUt587QI+BuFTa+Gjm8PYw0o1D5J1ruyILYJ+8$bYc9a+KxUKFkTWOIhdgpMSgacv$GAtRWztWfF0amloCjY7GqJoUbrye+U0MDokJHagwLJLfi6fNeAKoYEPt0LqDHWVcr1LtFMS0Ih3BkqqtgQepXD52sAgOGMOnyjGlWWmP234Dsn45GeuUYFU8IdUqLibLoqj9MRE7D2OQK7R2IH+EMVSIwV7uG9wGwQYJSOB5hbxXNqvyNetZLEGjQ$SWedSTQz926MUfw8UMRb1kc1nKKYEHNIuQl6tuuKoK+r$+qdOATbhz2mBUMZZ$2JnqwE9CBr$MU54MpxUL3ecYXbCkipH$XFhNwT2QvA1CClKfNd+Ym4kB$8WJdFn+kmZrwenbdpE+JMqorTwZGVitI4Ft3DkVNm8CAwEAAQ==");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_wsUrl(JNIEnv *env, jclass /* this */) {
  jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
  //获取java方法ID
  jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic",
                                             "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
  jstring key = env->NewStringUTF(
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjocl3yIgy2FipkO@DFlqC9i90ZiUf6KApxqUJ9AAG0LOc7OwJTFxKMYg1JLLRA@rMU3xFKvDuessQH79N@vaWZU8WJeE@GpPMMLDPi7xOoj@wm4GdGcR;m2OA9oo8m59eE7cwxttP02qPL29sDhr9MwXosfDVEHsTtv4yzw;bcuEjaD;hSrClE1rJXnRiiKYkGsGLrVLBl6uXznC3OPSqeDg7GWSFkZK7TM1rbgbBNvrrXw09kEgEgI43qLooZh83gZG0y@rY;lNsef9TXj2j;FoAf6PpDaCaBEN89wS0C6GWXZM1SNzOANtnN4odWjclUA25Pyf9etQS1d43sle@juqqg4g4cP3UqIr;IPnrCeNV@qaQxYxgRElNMByAK;0wbcYUP3oMavmvhlou9XbG3I2peMB;ICj6vG6W@aDBEHH95GG0GJEBk0Q17IKSPdIDWg3Dkjt3u2p1d1hdvFOLp5l;N9Mp1NnrrPVcr2KUOAR6Mi5XNYVn9wBWF;@;KG0UDedWGTNbCHDd3Fp9ztxc0DoA3nxQSbrjsSVmiLLyqr4H;NYfbv8A7VmN6I4mK@K8gSHtFg1MFs9oS3Gv;;9I7s8HsTEv;zaxsTsy@;8zby4ClLcKX6dtmiVGiQdbAemFBijJtTRmK@OozQLHfa7nkTT2EbJKUy9HMRy3y3D1cMCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "hQOwlyTUxIij@iOT4uUSVlUvOTrLjsdSF6QZY4XEEZ7wrBmg5GY2wScwNfaZ5repmCXjsWj0dUQ2iyHvBjygigC1qJK5BrTqok8h;Ftz6;2YImai@360b2M8cfnwC1yctRHUOUVtL7tOvphNfT75Tt5;gkT0rdD2uwuPAoigKVJxUfG46hnHg@C0UpKvR0I9J9Oep6d7B;p35FGD@13Opa4UqTRoitM9UyFDlyWwRmzZS8gD5rJKh3nynAe1DA@UKPR3pfRvookEFnE55Piv19SGWMScru9Z9WQ3oQCiHrO36z7HRyPSd6ICC51eLHnJRliC8q1lnPPgr2wTUrN9GynX@FinvZvNEHs6OREIGlIHX7hiAP0Pbwkn9vVh@nTs9kis4d4GfY3bifm@KXO4c2I0SV23g;eFmH1SKm@Hu0L0vyHo00Ma36Nw1;YmQjP9En0vzXfb@nhK1H;2lB8ilUvxwnKDogk2H3rJFtYbRI0LasEgpzEyx3pCvy62imtTDDke1Ounhwg88H7JGrzL01MTTWjUZiiV1ggnYEGdhnhi49T9xGqbR9iCFZ0;F54Rt@QSuVa9DFl3YrH2NmOAfHRxjg4mUqs;wqxlEhcdI0ARH@@jr;6kNbAZGmWVxsgxzJJFzarxMISVmGywR0i711MKx1Qd@MONuxuiTqJpV9Q=");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}