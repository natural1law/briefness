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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAy6;1mLrFLNGy;tTLZfZLHbYXFTBZogi62D76@RWaDZXOFZiIEVt1P;Ela46TtMj57Bm6VVJBy6uD;Ay26rj@luFrbM8doTGrsFXF5NK8SQVmopCz2;91FchddBhaSr@M1DjqdXCzGUA6CbmGbWd;@9KG98kVBCsnZyeMB21MtCvwjZpINdDFru6@RaUHw7CeFAXo2GO0iA04x6a@DJgyaFiMQaLO27nBCAMNHf5CA@z6foo2rLx6kydMqMihg8kpdP6AR13qlWTbu3qjV3y9AAt@1SftHTPVNaU195By4IWjsW8m4sc10R;y5cEYr7kVYvaFJx9GST8rk407DFQ2pFNCJ;JE6b2obN@F7X5;vhn1CI7TPM6X63DEciOwadx@czCMvlwnPmC4FCDoche3mV0SoG6AGyxDt5jOLhHmIe3ZMAZiL9oDCZwA8MeAV;u4p6tVK9wCug26KAy39qXCJMjlqSePeCCkCi9L;5T;rMCsfB6oQR61yB4ToDHBI4oQDKV6YhgeioNPJoxcz4kgD@Q0lTaaDnfgmRD6EaWZbLUfVTNzfoIu1EbhymAcsfGwZvkbLbw@fdmj5chZdQVdd990RmvSDZ9SPhA903myDIBhzMXoN6MOHRQst3MXMopN2ARGm8IhxrOl9huOvE5XzDACfP9tpsVM;RP66ToNj7MCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "tmIAzfjkgRl1BwSbP7eY3a1RVxKOw7waR@C4i30ktHJOuUe8LXwWz4HR3pn@VDzx0CaSUTFHgTDcysuqvNi2v;zIcxvH9EtC2@QVKfR8ZeQgAlAQ@1SVhueGVeLAxkCsQOdn7znk4fif4qjGjPXLfHsvZ6tux93u4FMZ3k0cyO@C0yxw4qyvVVY0D9jKZqSJ@0YbzxLgKnGU9GvJvmbxMv;cSeWSy2cAFd5XymPyDSPYdZ5cK7vC;kvtmczQYNu9Lbd2ABAnDgmFNetuT4dDv7P8J6vR1iIrM6r;KYhW6XWYJzxkgUYFKEC9q6@RLgiFeWLfNsnsOHRcZgnR24wcwYGpQwcbXSNSJ@C3eOQxgdvyofBSFx;ezIct3X3TRG5ZuX7p0BTFsiXe1adYTg3s5fgJIW48lT3yIlq0l1j6nz0Gzd;dhVgpbNey0dyBDiUY@VhVT0AHJPnoehfhB6fm4CMWd6xSpPMnSojbyjECExMIx5m@@66IRrawIB81vkKZUhdnL9ahmoci3hqvZC2RhcK6T4r0mMrjTChqlTJFDtUA2VsbTZXk91c1I35wbcvfxIqIsQh4jfJd5TW0P3btILVlJtqweolsYMNAk;QyUOZv8j0OUWLaNFCLCJgPfDY5gL7rXYFJw3LNpqcuoLO9SaRw4WFV80Mm3tXOvaRIOdY=");
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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAkkL5JNYcnuDhD6uD9XxnsWlDVeZVYkPnyWNZkQenxk@ayFE;S@0eyiwXGjfnQH2bd6aBpLloGWuEYMYncfIU3j7KX4RJvfE@4WcDzN7B85mvj;i4oB4xQXcDvTCfcQp9fDMj2aUgKrVXCAZ@09SQpVdDMyWt4n3tK85WopdsCnbrNgqqWe1SjfECTk34RyAKcz@QHBjvGpFJ8tPpg20zbxpdWNXI7JNon1NHP0VqyrywpTG9td7c83Hd00w7OrgO@Cg5Zy@gmUiQi870ROZ4pz1kB4@QDavq@p5auJ8J3Gmoy2dDtm4o0JI2v;VfxFh;9JOVE8sjkPhFVK64HZK;7VROxoyZEdL07T@EiuED0f@JL4uKVny7Gh4rtDo89zVjpscZ;Ar4pPvSmott0XJM0ahnHY@WeWhpAvk4e0WWnMinUUrP@7l1MoYnikyTU32Ucw7K02GG0;7pXlqc@Ar2OuX6p50aWx2DW0mC6jaqT2TFMJMStLyZAYMTkO1L2oVMbFb8LARl;bq4Dn5mm;3bFpW;d0CMasvlFG10pWtPwhGyEyc;4dn@Aem90l5FS7RkWrrp1u6BN5W4Qzfl6H0LijyQW7LKqsZZMzDQUGDV0Ba@CR2QpOw5;8N;b5@wOrciLXluc1xnruEXB2YQsdvu55q92AitCooaw06ABvzh2JECAwEAAQ==");
  jstring value = env->NewStringUTF(
      "HcALfN2LKl5b;mZQj5TGgWdsAqMnMu;R@xYKumwOB63xx1rLV49dwcV9rEwgzG0IHH6uWc7d2MUB0Sg8;8R;YMBA3jNBV5QUI;8SFr5E1aCAA6JYkod0IVGReJzj4TmqAvI1S9aP9WTfj7Iho9CR6UrGT;E2k5Q3m6FnAWZaUwGElEBKYk0Ddyw1EvdrjfZAeQZEPxAGDVfFiPc1Bsn5uaXZ83HsejFL3GlJ8HqSwlCnJbvNa6uKH@peJds5H902xudataJEh@ik48U@M3acUN4D5RpkjzXgx7TKzi@KU06SNirN9IZcJFXJJLo4abdtU6cqDbDYfhAdSc9Ef4tMNeI7r;5mP7GLrmhYRTOYh5eOhNGqL4qN0eAQGKwbPQ9oAWl;4eenf3DlzEBeF8yKObPQSKaakNpzuybWgxMFEDfmXLoAiaB4PLIZBqCBeDtxbSrCbFsGLjjV7BH@y9QIPmxYYPCdrghvz6ro6KAkCgIuZXwXe4ray9WvUwuWPdgbaBQzK2KvHYGIICB8hZ;JC@k5v3UDwJAmC9@zgK7nemp8M;gOtsFIa2z6krMIbT7E4EUSkAsosUxyh311KcLSXfLzvw4D9x;mj86mIXJjhIJKzY656GXjTVzrHEJ3DVqxfrGd@9iwdoR1JKVELfUx5H4pNxHsonfKa7YUEBBndKU=");
  //使用java方法并且返回string
  auto strResult = (jstring) env->CallStaticObjectMethod(cls, encrypt, key, value);
  const char *res = env->GetStringUTFChars(strResult, JNI_FALSE);//转换java类使用的string类型
  std::string url = res;
  env->DeleteLocalRef(key);//释放string内存
  env->DeleteLocalRef(value);//释放string内存
  env->ReleaseStringUTFChars(strResult, res);//释放string内存
  return env->NewStringUTF(url.c_str());
}