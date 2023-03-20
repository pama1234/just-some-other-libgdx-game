#include <iostream>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_jni_Tools_bar(JNIEnv *env, jobject thisObject)
{
  std::string res("bar");
  return env->NewStringUTF(res.c_str());
}