#include "opencv2/opencv.hpp"
#include "opencv2/face.hpp"
#include <iostream>
#include <unistd.h>
#include <string>
#include <sys/stat.h>

using namespace cv;
using namespace cv::face;
using namespace std;

//人脸检测
void FindFaces(cv::Mat src, cv::Mat &dst,CascadeClassifier cascadeClassifier) {
    dst = src.clone();
    //图像灰度化。
    cv::cvtColor(src,src,COLOR_BGR2GRAY);
    //直方图均衡化，图像增强，暗的变亮，亮的变暗。
    cv::equalizeHist(src,src);
    //存储找到的脸部矩形。
    std::vector<Rect> faces;
    cascadeClassifier.detectMultiScale(src,faces,1.1,2,0|CASCADE_SCALE_IMAGE);
    for(size_t i=0;i<faces.size();++i) {
        //绘制矩形 BGR。
        rectangle(dst,faces[i],Scalar(0,0,255),1);
    }
}