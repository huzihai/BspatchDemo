//
// Created by jing-hu on 7/20/20.
//

#include <jni.h>
#include <string>

#include <android/log.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_bspatchdemo_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" {
extern int bspatchmain(int argc, char *argv[]);
}
extern "C" {
extern int bsdiffmain(int argc, char *argv[]);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_bspatchdemo_MainActivity_patch(JNIEnv *env, jobject instance, jstring oldApk_,
                                                           jstring output_, jstring patch_) {
    //将java字符串转换成char指针
    const char *oldApk = env->GetStringUTFChars(oldApk_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    const char *output = env->GetStringUTFChars(output_, 0);
    //bspatch ,oldfile ,newfile ,patchfile
    char *argv[] = {"", const_cast<char *>(oldApk), const_cast<char *>(output),
                    const_cast<char *>(patch)};
    __android_log_print(ANDROID_LOG_DEBUG,"hu","%s",argv[1]);
    __android_log_print(ANDROID_LOG_DEBUG,"hu","%s",argv[2]);
    __android_log_print(ANDROID_LOG_DEBUG,"hu","%s",argv[3]);
    bspatchmain(4, argv);
    // 释放相应的指针gc
    env->ReleaseStringUTFChars(oldApk_, oldApk);
    env->ReleaseStringUTFChars(patch_, patch);
    env->ReleaseStringUTFChars(output_, output);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_bspatchdemo_MainActivity_diff(JNIEnv *env, jobject type, jstring oldPath_,
                                    jstring newPath_, jstring patchPath_) {

    const char *oldPath = env->GetStringUTFChars(oldPath_, 0);
    const char *newPath = env->GetStringUTFChars(newPath_, 0);
    const char *patchPath = env->GetStringUTFChars(patchPath_, 0);

    int argc = 4;
    char * argv[argc];
    argv[0] = (char *)"bsdiff";
    argv[1] = (char *)oldPath;
    argv[2] = (char *)newPath;
    argv[3] = (char *)patchPath;

    int ret = bsdiffmain(argc,argv);

    env->ReleaseStringUTFChars(oldPath_, oldPath);
    env->ReleaseStringUTFChars(newPath_, newPath);
    env->ReleaseStringUTFChars(patchPath_, patchPath);

    return ret;
}