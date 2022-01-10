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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoZUXZKFaaR9MSK0DFHQepU@bVQLajJ6Ja@uaY35HoxVMQ0VFn22pHnG62tYSWkBFBF;e3BdIN5op@4i;fG;wzMj3VsKjYMxmtbonBSrZdLqR1UeLeqGL1IoP5JaS642YY1emS5pJC471uUSXWKKix8BDI2rB9UT0VFpTiALNv5Maue5Mm4OB97@Gr6zzm6yH@ezIihxlHMPMFORswuPJn2PsexshyNMGktFchpwUYe5wlug2;TaiHbNqhYB@RVgH3l2zAHzfGn9sIEay00iz9LDUOTLXytboF8lcqgyEEdvxQanhTdCMg9JNKtuCJoZllRJbtQxeG1XIx;Y61yekC21SnOeOX1UQ3a3WuW8ht3O5MNhd0v9Xvo74;;zNtNUDI2AsaSuQYpT6pVGAoZOUG20PaxMVWnffG05ZUOhC2SiPcqTvRYfOS1hWn5dqmgNB1gd;RYr1R7uvV51qXk3aKRW7WNHIC8AdAV06yt;VjQVgaBBbyq64HvaM@RU1;pdgpj1B8WspY9EnYev61foMVZnl2oIAUfFLJcu7u4H@E0cSZ2sPTdtw@l9QQThBJs3fuqSVXg71Ey2JUkNMRjBJXiNDZUoZMQqxIlXWlUpg9FDlqtjWOVjgm18XuMQTAyMoQuggbNSg9YY8eF@u;qzloptJ4Z9aFozJY54epvVvrkECAwEAAQ==");
  jstring value = env->NewStringUTF(
      "Z7vV7tM;uYbIBXPkkvTgd;JZKyNfKaIU@kTlKsshSpKk@d7T9xCRpXWwiD;RExpru7B6O59Gjtxt5YvJ7tXnIc@4uqswGDLNdDh3kDivxhiafcYUiioDhPM8yhObzREAcATtzJsrvHANfLyQ1KfBQU6pHay6zpRQj2h8AeR@i9bD9;DgIamBga4PSl4qAXXlZbdpDDkNFdK0p627UjJOXX3MuHQpnfe@OByY0xbq@ZL39O@a1q@JTjVyuvgWivJ1dVF0MOPcXPQgW7dvvMgt;Z;yafnE1MUKe1G;7jzLbKB06YRh5TuyYJWzpCiHJ;C0A;Z2CX3vwKYn4J2L;lu6DmPVDRqpRYmN7yi3Nmlcs2x;3o6zuONQvKlsbouVFk6zJpito7lMC;AhnNiwiTzYl6eTf3vmsdocvYet7klo90kAtYexMKFyHHUFVEehO82u;KGgHoeiY4bGpyTe5ADc3HaCHC8YhW6TmLxqMzziU7aRcW3zZJa@ZYMI@kff;HyvpGfZGmxarRJrVeGaJBLuzTH3CjLPM85QJLwXFpppDf1@oXG88r;k@@rxBSaG67wQuCKzrTvf3;twYJ9M0fXZLxLeGyNC@dSGA2wFaz1IlxV6ZhRrrA9dVyAHpy9@6kUI6G1AJRHUaIiyLoem;iN2E76XVu2@2d83TkY7manBAPo=");
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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAu;MbqQ4o6yq6rRsDnd9J3gjYa43iJnfciW1cSydtFjfER1y@;0S6fL2lhJlc60RL5Lpyss7tJVhQNFUmhBFnqTBeQgVRUi3RsRAJCI0SJF;1FH@pwHaKrc7zKUbOk9GQigExBhtL9YwLlzw77rx53WTa3NvAA5lSbNO94Shbd5W;8eFPir;NSnrNyJr5p5LPCv4n;cythGHVBhbKHpiCnGGMN5ss49@K@dqPoXpb2kkMEqURXKh9t0UeS4OXDXHhU4lqXraYTHmMDaGW0DCX@xaArKWrc7nZ3qCE;pJdMioYYxfkFWWB9LdBN2zFXbr0ivNKByAdIG8NIYxuGej1u0okOqnsP5kgn3Aye4Jq;tfniMm0VjyBycMOvuSUNA9eR4uCwNt47Pb2@GRVv3T3JzGfF0QvPFD54g;5k2mSigSYOy0yEZN2HVkwy7kX7dA7CjIhUq7yeYWVGwajvpiN0HXKA2dT56i7yOxy0eD@uesdulcMeVe23xl69QhvAhw9RzIUdB92tIhnGRnQIrcrzQlrue9BAAp8QpVZXnT;J@QBMvcSEGzxtMKZHt6r;eqGY44oE4YLOWiAgwuklTjo3El4vOXAd3f4KeHCIDYpjHlkCiIHIJL@IO265JJ1KqcBkKVHdBYj8PKMFJPqe5xTxmgYyahfWrjtD07L7YrAIiUCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "kTFewOUJF6xY;BozR34IsEkivaqfb;XXJ7Raato5Ke@St@dBSHxth2l5RXVWzWdiNt8F0aK4n4RI;qJP9Z5EW4jazC1chxW7erlRwfxmSJEBBvyDL3dwQIsJrsbYxTMgj7Sd5MAWbWVHYbRe9z3JEaQXKm4B0T8r74qk5JSCQ7@@Z3FpIq4zFHf8I3SLYowaSEqZbwy6CHcnhBEQYdgAQ9t5lCiE2n8QGEmBPEFBQ768gJZ580khj4WAgnkcdVZIWmrUb3zYXSwZ0QcFCCrO6KoIU60;7vqnAvKPDG32G@M8Oz9;S4;bQ0xIyai27CadiGsr857cKB8C628TOjuv40tlSJFGZlQVBfEcAKpfsXdeT;IQaCcEx21uUdprLV3fkWxT9o9qPL9lB3;dlhg9CAjEksYrylFWMv7uP1FclmgXklAaAgoMOuRIB7i1kjR3QsUpuVQ2ZrnbYm15zzJ7mpoypqxxguy8Jr@SEF1Z24V;I;zz@KArMI1VDUIj17Zn1OWGPlnHAbmFAKH1y2bmfF1lOE;J6v8xIJM;fVqw4;VoZA2wX9F9nCwbPLbqi7sUVaaXPl9DpDPYyfRanVgQtaLv0ic1RosUC;5610l7BnqM784RJzcSF1BF;1j1FYCz8sUxsurhxYhe85WBGqLrGqeZtlpdbZtH9TE505gHpQw=");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}