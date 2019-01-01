#include <jni.h>
#include <string>
#include <opencv/cv.h>
#include <opencv/highgui.h>
#include "MSRCR.cpp"

using namespace cv;
using namespace std;

extern "C"
{
    JNIEXPORT void JNICALL Java_evan_org_vienhance_util_OpenCVNDKHelper_detectFeatures
        (JNIEnv *, jclass, jlong srcMatAddr, jlong dstMatAddr) {
        Mat& srcMat = *(Mat*)srcMatAddr;
        Mat& descriptors = *(Mat*)dstMatAddr;
        vector<double> sigma;
        vector<double> weight;
        for(int i = 0; i < 3; i++)
            weight.push_back(1./3);
        sigma.push_back(30);
        sigma.push_back(150);
        sigma.push_back(300);

        Msrcr msrcr;
        msrcr.MultiScaleRetinexCR(srcMat, descriptors, weight, sigma);
    }
}