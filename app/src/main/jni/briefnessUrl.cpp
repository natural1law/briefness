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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtO@lBxGe0wq1ONSdD@epFc@6VTM8TojOP1kFI49CjIV7TE3N7K7fSDSoWRqvzXf;TRQqJ8SmXC5HuBuIQARd2sqeViIddinCXingLANWGNzSWz;F9ESSX7xaYVw9m6VpSyzt30BrbmFtrwKOJj3MZOP5f1nsHfOhSzDCEEoBf2;m94mifGd9L@Z8QsyQjme;PGKdo4HGsLOyr3idp98@cgviw513RjsDv5eQSHAbEfKwC6CnWwHiZLppixEH46X1CcgI5l5wG6na1QzYKWDBzxJIfGBQ79yJ4ue77JrmxSBXSy5nokKhAy@AZOyxMVUeDM6JCNb;Rg4OJUx1iwh;Yx8E1JufBHjReRapM1mXyR4VrZYKkGHe;7yI49UpOLWkl51NuMqs1oWUgPyBlwX7v09jPfI5ijlLDsJFK95K1sjgrgSvn5QA7NUOne2cvHJHfYK;5Ji6@RXKDbPsKS;VBKZ72ZdFBxeJdKF6Be1dauUv60zDdF8QFQnwRy3MI8de;HrWh4Zn0GPlmIbOlOS;dBa5tKrP4KqvuEHgmCASn6qej9GeM1IapptTZ8HrBS8kMj5hrcAx4TeDhx8wPvxMLb@2vICa6JeiZREkEZMUo@STsww88UKgVDElDvsjIWrCQdEpcNLMkFNkyb7VKbT;uuKwREUYF7yWVYWL1@WizSkCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "UKzAzHHYsr0qIVebvn8@AFH1thIZw02qQ65HZB;YOA8Nn86@D9OoZIiIYuQUuQtZzm0sXoyHgPbXg2eLcPTyuYtjzaZ0Tir5fPDfJ8RZ@@hRexpiGZ0OxMG6KW6O;Uu2MpykfmsADQ@fS4r8XBg7KjVHKb83HzOA0@XjGJuHniHevcpcQXxbF6bYXxInAPgwnqZG;HBgt5o0FmhMVZcyBEE5XEs2XF4LNNoZaRniQnNm4pMknyXCVVov7@0EnwEvol7CCaLNfVAhgbDPIeTrNNun5K6jzMHM5jnNEvk@21gjij83owM93xiFaWNUPMc7IqbkUwcLYfi@H4bv8l@FPj8gDuuo3CtepWPW0T9FmPc49IrZc3PFaDzqfz@UhjSKMvdQiRHQVO325OLTtZGfp4EwGcO1ZTNS;BE2v3cMTmDiyivxMINkM24JLwuAa21sGBP7BY@SrQssal3NjyIirNUthpui6LuCXuXco75byaxqHFVuDhE2L9qQ@aNW60V@R1pX@R6EcOLRY7DefFto9qkk7hV5BqNvNZILsuefZxdiVgcj7ARzNyPpoVY@mJPB4Vnanat8j;5liISMwpbbqZ577SisttFtjZM;bAoMQ13SXzGrzG0oawPXFiU@IBO7bNRlW;UE2lPBXKZzRaPc6E4wg0QdsI6ZPQ@2Ea2a0hw=");
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
      "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA0WmCMJrmPKFGKQGCTx60C3eL3fLNxG1;rOmVkqMWAuf4ggHI09ZinHJQfVxWlTiIt4nGVIgoqkn8D8FB30rgxDovGd4w8q3Nbe0zf27NkGph7lZiVKaH2Irq1cBu7h0Lcd7nmIeV7Qibk22F0RLOnJ@JFNp7Wuihl3ftXlSPsNzBOiHbxUNjbwvKIK9ZQP0I4jjbDmIlu5KPbIkKKUwFSRWG31I59o81a50z;wsry6DN6eTmqioqkw6iFm2V04KUneQWDHCTxEIGS34LKPF5OwckJOnWtkZiBlfDDMmElFmG65xiw28l;Jdj;kbyA9u4OaFNF34K0lE;Rp0LdyStSuf1CdeCTcueweSAGjnNlJp2WLz8733wVOF8W0jddMvWj3J3C5AONoKRGwYi2HdKzmCu0czYGjT3xLZp3Ly7rDn7Jp58eaxUPzTXKKwJmVAOtcZ808HGTHY0xF2MmYUJwaFK5knYIeawIlul5;NHqXPef1zY9R7hBXRGXwNm@UQWlc4i4pY@PdRZR@lhKuUC2jGPOVEGTS6CplMs9rd0eWt@NyE0kTjRV7Bznb0@s7SQCySZ3bAH9kyI1cyW0JDI;OmjXvl@mhZ1LevnyK0t6Qzt1lawweJTyXkg70FOlVCf@IoPm3i5YWedpsMTCIf2GyuEvcrzQ1ix29KK1iJmkJsCAwEAAQ==");
  jstring value = env->NewStringUTF(
      "XIwOBM6CmzPOa2q1NB284q9GR3MuHS6Cge6oZ8RtPXbAmm01nQ6;txjf8vWtZXA3uTLHnvhJmX2mAnq5VOexVCMiMIbgF80Q5khHM9kdj84lB6rFXP;hkUdAh1jyK72a2kTqeDtUAJnCEx@dvPToCiTwKTa28B5JbEcGOJEhrYscHCmZ403kC181p7eYXYKS;@J0l87Q5SIPhK8me;nCRzvxtQLLguqzHcYH3RvDGMLWZmIM@VXB3KaqVVTlhd4HtTFESm2rARypP5RLPliLGa2KrVi2LA20;SL4GRCSGHM;5MtRAM1a5;BmemlQ5MmGfDZIuk@E0GJo9vBW9ttnFwb8lJEhndisYe3C9@Fgafe3azaw68z6St1fuZs6iHGLsR1D;bOCy7Fy@9TfeR6TTC@HLk8RjWAJsbiwNB5mVrEi0Sy7RPVf8yHdK2P93z1F1CMdHJEUgSW5wpm1Nc5pSbOCCO5dR7c25GvKW7x0q2ex9DTYgJtynLN8@MVPiBrnCnhnw2RvF4efRavpmFhIDE5nwvQ6I46zPpQwtBgJpP74RcCDhHeDcQsZzPx8oGorF3J7vzBPNmTkz6BFhahcXGNPHp5lai;JFLSOVhT1FslGgNyVjPm0IL7IJcwDiRWjsBrAwTcyQ7nAdW0oU5j8cbPdoC4t97FcA11AHJYZEHk=");
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