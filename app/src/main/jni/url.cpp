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
    jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    jstring key = env->NewStringUTF("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArVhBnZR1r5YJkVKUAU2XL9DVZh22iUT5y5Em1OXxdpVwea$QHHz+xAdR1unNyszF3dPUQAoCJn3+SDZS+lxr9+XBa4XIfLComgh4fVnMBbxTGK63sRoiUkMdn8OSbo3lqAHq7fzwt0V6+YRRyZOe9WRA30GhCPkXWwDfCf6gLucMDq+dwK1xhZiun+Ir5VcoByRz4yiKgmumwvbzWBDXkY1Ila7GiVDPi6Qx3zUJsrzaZ917$undp9hlYlQQ1pb9BrI+2TZnadQGheMGWI+RP+jpOMFMnksUQ5jo+$SEw4eV5l387mijhB8yPJ1KFk7SqCagzhsKqmtvnCPF8N7oYPnMShBIGGSIIrEu6EnZVC50kozSLK4FmcIBZzMaXC+qPV0KqrR8PAi6hqnDmBRET9Dh7UaVSgK0WGJFYFx2NzNHtkg4ujZbUM0NkbFihCzwitf+ii1MGttCtDCzjzyTOtu+I922ONmRrM5tH99rmDrVzbyr4ajGHUipNNXo6FkRUXQ7fUcwnzo3HibECb9pTldjqpS4aj7mP2KI2QkOwQZEPSGNHryaVpD+IrySLkyq5Cn$PiIQ17Av6Hqxvw1I+WL+zzD+6FzH18nf4O7zckh1noNVV+WIyubmyPp6Oczd9Y7Wflb$ngEdcKc9HFkL9JgOyOrokL9$gqZr092GxEkCAwEAAQ==");
    jstring value = env->NewStringUTF("HkqHhQFCkrIKc5lH8762GBbEmBGN7jiSL9OsLwcxUZPkOgo+$IUP8pjS19DaVtpT4IokIlwBGOOzQ7a4HA8w4g$SkPRW+Maa7uafRodSpoEFbuoJoXUMBYM7feIbaSGU8wjr1vQlGpA1l6D2pNCpzeSpDyhDCTRhrrD$x+zweJdS$UfXrFSk7AgUi0WZ0n2suWmcoKhEW6R5RVGrX0uXbjxGI2OCVrevZ6y9K+$fGt6iAMRxT6hrPhY1bh3$8j6pJyArjvlfxGyQzwxohc35u+loPtKikctULWDh23ekCMSMbTCNh+eSRvwbH6G4D7DylCHH6wXD9TmRsu849YtjlBcIgGPcxGSJdBCRINnJqyVcLGgJ8P1DaeEe9CDYCQvwAoiT1NrECniBQIKz$X4BarN2hoS$6yNm4Hh7TYIRT5VbFIVqwlLoyU$Uvv8W0pNnl7+NQrhKE6s9vLOEoZkS7ix61NKJkRwJNukn2Z4TMz488+OJxfX+vBbtElZmSPhZRklqZYXbioGBAlum4Xhsn4wzJO8VP1vRYzYjF7ymKTBgjyhioqyW5cjHemEHyQu4hckW7Bu7YHYd2VZ2+KPY8NC7hjctnooahssqXDpg5hV0nwbzWLUe+X3GMUOgxqO5RJ1rovOjRDhxvhNHPuLDAzzEbJbh1V1jj2KNmkjiFkE=");
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