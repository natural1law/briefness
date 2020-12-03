#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_api_NetHttp_00024Builder_host(JNIEnv *env, jobject /* this */, jstring host, jstring port) {
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat","(Ljava/lang/String;)Ljava/lang/String;");

    jstring head = env->NewStringUTF("http://");
    jstring colon = env->NewStringUTF(":");
    jstring slash = env->NewStringUTF("/");
    jobject str1 = env->CallObjectMethod(head, concat_methodID, host);
    jobject str2 = env->CallObjectMethod(str1, concat_methodID, colon);
    jobject str3 = env->CallObjectMethod(str2, concat_methodID, port);
    jobject str4 = env->CallObjectMethod(str3, concat_methodID, slash);

    const char *chars = env->GetStringUTFChars((jstring) str4, nullptr);
    env->DeleteLocalRef(head);
    env->ReleaseStringUTFChars((jstring) str1, chars);
    return env->NewStringUTF(chars);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_api_NetHttp_00024Builder_hosts(JNIEnv *env, jobject /* this */, jstring host) {
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat","(Ljava/lang/String;)Ljava/lang/String;");
    jstring head = env->NewStringUTF("https://");
    jstring slash = env->NewStringUTF("/");
    jobject str1 = env->CallObjectMethod(head, concat_methodID, host);
    jobject str2 = env->CallObjectMethod(str1, concat_methodID, slash);
    const char *chars = env->GetStringUTFChars((jstring) str2, nullptr);
    env->DeleteLocalRef(head);
    env->ReleaseStringUTFChars((jstring) str1, chars);
    return env->NewStringUTF(chars);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_use_WebSocketRequest_hosts(JNIEnv *env, jclass /* this */, jstring host, jstring port) {
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat","(Ljava/lang/String;)Ljava/lang/String;");

    jstring head = env->NewStringUTF("https://");
    jstring colon = env->NewStringUTF(":");
    jstring slash = env->NewStringUTF("/");
    jobject str1 = env->CallObjectMethod(head, concat_methodID, host);
    jobject str2 = env->CallObjectMethod(str1, concat_methodID, colon);
    jobject str3 = env->CallObjectMethod(str2, concat_methodID, port);
    jobject str4 = env->CallObjectMethod(str3, concat_methodID, slash);

    const char *chars = env->GetStringUTFChars((jstring) str4, nullptr);
    env->DeleteLocalRef(head);
    env->ReleaseStringUTFChars((jstring) str1, chars);
    return env->NewStringUTF(chars);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_use_WebSocketRequest_host(JNIEnv *env, jclass  /* this */, jstring host, jstring port) {
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat","(Ljava/lang/String;)Ljava/lang/String;");

    jstring head = env->NewStringUTF("http://");
    jstring colon = env->NewStringUTF(":");
    jstring slash = env->NewStringUTF("/");
    jobject str1 = env->CallObjectMethod(head, concat_methodID, host);
    jobject str2 = env->CallObjectMethod(str1, concat_methodID, colon);
    jobject str3 = env->CallObjectMethod(str2, concat_methodID, port);
    jobject str4 = env->CallObjectMethod(str3, concat_methodID, slash);

    const char *chars = env->GetStringUTFChars((jstring) str4, nullptr);
    env->DeleteLocalRef(head);
    env->ReleaseStringUTFChars((jstring) str1, chars);
    return env->NewStringUTF(chars);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_use_WebSocketRequest_init(JNIEnv *env, jclass /* this */, jstring id, jstring user, jstring pass, jstring project) {
    jclass String_clazz = env->FindClass("java/lang/String");
    jmethodID concat_methodID = env->GetMethodID(String_clazz, "concat", "(Ljava/lang/String;)Ljava/lang/String;");

    jstring head = env->NewStringUTF("android/control/");
    jobject str = env->CallObjectMethod(project, concat_methodID, head);
    jstring severed1 = env->NewStringUTF("@");
    jstring severed2 = env->NewStringUTF("&");
    jstring severed3 = env->NewStringUTF("!.w");
    jobject str1 = env->CallObjectMethod(str, concat_methodID, id);
    jobject str2 = env->CallObjectMethod(str1, concat_methodID, severed1);
    jobject str3 = env->CallObjectMethod(str2, concat_methodID, user);
    jobject str4 = env->CallObjectMethod(str3, concat_methodID, severed2);
    jobject str5 = env->CallObjectMethod(str4, concat_methodID, pass);
    jobject str6 = env->CallObjectMethod(str5, concat_methodID, severed3);

    const char *chars = env->GetStringUTFChars((jstring) str6, nullptr);
    env->DeleteLocalRef(head);
    env->ReleaseStringUTFChars((jstring) str1, chars);
    return env->NewStringUTF(chars);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_http_use_WebSocketRequest_publicKey(JNIEnv *env, jclass /* this */) {
    std::string publicKey = "MIIEIjANBgkqhkiG9w0BAQEFAAOCBA8AMIIECgKCBAEAgg8vOTA1NXgXOK70UXiCfjSrH7UDo8gPENoino46we3aVISxq9eVa7c6HJJ0Hg+LngZrKdLCSqCEf8120zqE28$d0Y7aUmgm$a2BBZLIBRMQiO3UShl2Atz3T2lOBnDc0DAxonMDV8Er$7XgwIbsJ8oAFdebKmpSN8CC2yjZ9bEWnlazqno+os+V+n9tG6Oxan6WhlYtnG0A1HosiDZ4O+6XUz3EgcyS1fCA+e5zvXHySDmEibsw91TJ$BfuZmm40gwG8dsRZ6bA85J2WW4CSiLPlT$LKXyClenb0du1MnAbGj59D89aYRQsNi$rkYEiX1T4Vr4ZF0sgd0aV4nSxXi2gj$11QPxsqvlAohFyzw5TibcrhaN6CsGIYVLrdgvofhcA60X6eO+l9WxapO5l6+yzBhIFgfr5$qThCJ3iMeqjKFaVPKH1+EnTJhzKe+wLbrNYILqzxdDMTZ8JGJ5yKKs09CIt7zgZZxM3zzJE1wDBcaG4zH3EVfPbZh5Cxy7CDCjv4nXEXdJyqRBQVQSbONzJChK9r2laI3pGuOFZB44n6eJnQ$+seYollP+jkteFynnTQxFcVMacF9QwVrdrILkkkt$GuHFTVyPDrdjoJlzGYjzIrcqldhLX7ReRgve999AH+1F0i4virZBbU3lHILjaLZFfid+2szTmhjwmZd06wuIfxaHHxpYGG3oPGHGEQwp$+v8vphQxK4Euc19PIRPFzrJXosZjdp5nVMxSoAAXdwDU1G2QoKGVWcZvF9AJaFiztEHoICIB3WT$tC9nqCJcBcGHSwEM1m9OZ6Lj7DmxLdU6F+vn4Az0S9WL0PGqawgHSjmdgYuyKYgXAt$yiRFpCJ6SLDhFyUy7c9cN+zaeT2s6PpQp0Oxdeo3VLI6$zMfCmclof3$pp5cOe4oH5ETjs03QWvlXjwfNJQDgYZUaGQ0u6+Slrvnqyd5iBZsmELxAOrrRfGluXqAbGyGMgpeYJgLnMyxq0Cq4wzfZBBv+rbPfafrDIiiDPAUif+9tr9ah34RDcpzKpAddpqT9dTSi3m66KVFyc39shSrgGebu8$izcaLa9RZ0BYG3I8OlRNNSvotXGkLgiwDQoW0t5GKn4oUENywORzqfrXWXbz2lg5$oi8viPCPvlQOy4lFebHYRfvpR2ChxiXOipl9Yt+bgCO5rkuYLF3$tmQKhWYy7efydrtdkU3dpmkaav$4CB1Du3jbwEsnFz2b53Wq6CdwY7iZPqyXhyMMBimQnQ5GcwLS5hf9LolpNyPlNwXTu3AAk5fsPeRiIEE3AoDMhzQYWHqoeSw$qdleVVyYAOjpCdM$T29V5vXcSTVQ2Q3bD$szQ51+JY5ZCfpsqrc8uBwIDAQAB";
    return env->NewStringUTF(publicKey.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_error(JNIEnv */* this */, jclass /* this */) {
    return -2;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_fail(JNIEnv */* this */, jclass /* this */) {
    return -1;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_success(JNIEnv */* this */, jclass /* this */) {
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_msg(JNIEnv */* this */, jclass /* this */) {
    return 1;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_offline(JNIEnv */* this */, jclass /* this */) {
    return 2;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_androidx_http_use_WebSocketRequest_online(JNIEnv */* this */, jclass /* this */) {
    return 3;
}