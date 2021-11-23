#include <jni.h>
#include <string>
#include <random>
#include "base64.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_reduce_tools_Secure_00024Base64_encode(JNIEnv *env, jclass /* this */, jstring cleartext) {
    auto msg = (env)->GetStringUTFChars(cleartext, JNI_FALSE);
    const std::string s = reinterpret_cast<const char *>(msg);
    std::string encoded = base64_encode(reinterpret_cast<const unsigned char *>(s.c_str()), s.length());
    return env->NewStringUTF(encoded.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_androidx_reduce_tools_Secure_00024Base64_decode(JNIEnv *env, jclass /* this */, jstring ciphertext) {
    auto msg = (env)->GetStringUTFChars(ciphertext, JNI_FALSE);
    const std::string s = reinterpret_cast<const char *>(msg);
    std::string decoded = base64_decode(s);
    return env->NewStringUTF(decoded.c_str());
}