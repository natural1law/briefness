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
    jstring key = env->NewStringUTF("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAo9GPN4HthD0I46Q0G9Tkb5qS9IZGUYNMcE5EeUsHLNko4V;lfmWRU;r08IUC0ihTnWtjaRVhGdczW4eLk1zKr51HAJGBnWaXIPozCoCuFkRNXVyGrwmblBfLgNp@Qb9fLQwd;SLq;l0HAmmbEkuF@AWrJ8eAJN4XUE7Wcgm@KCrcSq4r@FGxvkse20UR7fBBDmaBJQa1X7hotb9ah71ipGRkzsGYpS1FvdLLyJYzn0J2KFWksQOALQYMNCuAVzA53uOE3oCvThmpPQqJnGlbC0OcxZsvgH75oVWptgvUm0DgShEii3mbXOcpBN;zoJWKsm3Kgm4@1fBuTvjmuBah9DBhXowHOWHBQyAW5EZZPFo012BZXi7;2T;Ih1KSZNZspKZVATITg6@Oaovmnyr5dxb29zlhLkp2ShtUEB0QlKqK60HDzBZS2k8MOxz3MWaYdps7jqnpfT2KYCTM;3Cfy@4opOjK80TvXelcAmIzDS41yg3AymORjWlCSR0srRAEc1iV1QkThW@UDdnbpFa0UeKro;e;13Zq3VConzx81nP1KTmNbkzRGSyvgGaeZQNe7KAmtE@JUdBYg@xvtmGHhS2K2DeBSPfTNflgAN3Ypad2mxdp0hZ7W4FCTkOCnfuO2G6N17Thdv@@RAbRcsZOzdv4oYZuf1zEBCvJSDBBOR0CAwEAAQ==");
    jstring value = env->NewStringUTF("Ikq12QYs@5XF2LD2nm4iLehIOzR;oO7CuCtM4Wcx;gjjrf0i8mgSJgWluk7c5NofZxlOpFslr0Ts4AMD2aJp4N7Jpt3K0UkMlMtFQOd7JS5jHE1hPtEnxcTsJNGwWKpU7vbfs2bEnt9eFGRnctdgClWdgvOANdrq;p;3wPLdFsf1HlktWKc4d5PxwAJJ1mppRh9Q1ylOcdW7GANe7YKTAsY3qIc;RoNfPTU8Kcf4vcPPVowbxt@Ihrna3xoCVySXlQAfK0mxKczDMfjibpDoulaPxeZaeNzkyoAj;epzH9h5Ft0qJy0m0XB;qhf1kjZKOUcDPc1pw3YUU5WONYUngLu08Bmy67LcLExte7oE;Y62SJeI42iDgm13kBNz6g2CHSeLeFpWXNOu6jll;ZLXKNszudu5AmrvAhNUmJBfz;3WH9@2Q0LRjs7D@xR7mwbDX8hU5D;OKNdR@20nRqTQM6;EQDpE8YXSFPsQDVjXCKHUCUqxWhhdVRAjHOHRgLNMBByaNT7ZGEz5GTUTXpGPWTbqpSo0fz3Snte1sRy6PJuGzLBCi;qT0oQwF4dpiN43Sumerq5AZHIsFB1cjJVLPANHSpdHBtJfOVolVIEogSEwVOWFoFSBiBAph;pBKaC6gEnoAXwcMLAAeU4praHvhYXK794z0BNojkKteqE8wos=");
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
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_briefness_homepage_activity_NetworkRequestActivity_wsUrl(JNIEnv *env, jclass /* this */) {
    jclass cls = env->FindClass("com/androidx/reduce/tools/Secure$RSA");//调用java类
    //获取java方法ID
    jmethodID encrypt = env->GetStaticMethodID(cls, "decryptPublic","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    jstring key = env->NewStringUTF("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtBVI1wGkMDxpBpR7ElsLLSIlsrdOMvrFiKJu9ffqg4lnSdcuPJatlYr180NRaWltBMKQCtR@OfKgLKA4rBZaHSrcHU4w9Bd3waLVGvl8dQXp782u1oIGaIwl6;91xqp020yULVYbeV4AhpU8VsqusH8NEQwOLrGBgzGbtffXKK1HIzBMMZW06KAudMzzmuD9Apua1x7oNncPq64s2jEq6sZBHcbI0ctXbNhAlOK@pjfr8CvNiWUDupxFl5bzzyoMqCoLweSmFqc2QbhIHk8sdHGKjG9RjuQY0UBjThTCAwrt5QsHDiO;@L4@ikSL;QMwu@u6WO1kDctVpFl3KnGbueDiQN8Y6j3c;1OmleIKTnJZzAKZXvu1qwUUFceaXwLPYNtYwYGNAqyccTZpKePfIrAKtCflR5ih6Ue8TZh3n1065WCvlvyV2e8hDRqRDikcSWkTFN14;1hHd2q0@pzu0sBvu7e9og2aQwwQ86Y5997d9;xdBCIDNqxIz;1Kv4QSxRjemv8IRkpQAm7E@lOmuA1CqgyPRyTPcEyWDdysX43WUN3E4eyw@c4BlcSpnqCKtsYgn;mVrP2Ni@SFC4pOXQxPLIzL3qsuCrQyaUQH127qKmdUHBl2oY9d3SB8kE;S@sxpLj13QSzbnHJCpnfUIQoKSNG89orgAEkvPCyl7SUCAwEAAQ==");
    jstring value = env->NewStringUTF("ZpJ2RHHZzzvx7MiWg@wsWehlHj;CDVXHG33P1P@N6JxHL@fXnpxhFeHK6zhjVvn3v5XAbvqwepYbo6CBdHBYm;Db@wSJH1KCO2l5Vb7PeuyzOCJbV6ft5nZL8r4pwWqrosyVBFdaGXB15K6fCjbaLekcGsDM@3UVBoaHK09W0Q6u;kKwZUgc2Ck3vCZV8abMugdbbBM;ILkTOzerL3gAN8U9R69rorz6IAyH8YPBFPU7gyIG6HqEBpYXWbqvFdo58PdQIU4;Kes0aE6kd3eW3ISoTaBFYPzrkscmToDvHPnGD3gtraxhVJEzZazoKsu0ovOEJh@oxAD63d0ZtypBY8neYKnYxGXEJUI8cDQpXMJD5ZowYFJSYp3LKJSD;de03IQM9iOpSDFfgcP3tg1AhL6mbiNBlBwfQ4@lYgezafl5aCo34SanFw3AuXLTFyL4PjL6W6WrkIlwdEAfIQhrw6ZcvwIcFVJ;VAuvarDrVfQNUCTVPu5nH8EAh1nzD2MEDRePiMwHJcq2TIwO7JPoAiSSnnmESPThCVsU7JMiNI64sjtIwN3ulPUDMAlPESIHygLg;5q4UxiuoqY82TLZCSl71DBkcp;SBoUFdfpURhSeybIm0jm5jbyaMVJYTtsuWtSQP8EdXm9l1Igy;vnPMkDTYahstHPoDGdBb10GEmw=");
    //使用java方法并且返回string
    auto strResult = (jstring)env->CallStaticObjectMethod(cls, encrypt, key, value);
    const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
    std::string url = res;
    env->DeleteLocalRef(key);//释放string内存
    env->DeleteLocalRef(value);//释放string内存
    env->ReleaseStringUTFChars(strResult, res);//释放string内存
    return env->NewStringUTF(url.c_str());
}