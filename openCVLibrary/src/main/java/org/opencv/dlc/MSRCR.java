package org.opencv.dlc;

import org.opencv.core.Mat;

public class MSRCR {

    public static void MultiScaleRetinexCR(Mat src, Mat dst, double[] weight, double[] sigma, int gain, int offset) {

        MultiScaleRetinex_0(src.nativeObj, dst.nativeObj, weight, sigma, gain, offset);

        return;
    }

    // C++:  void MultiScaleRetinex(Mat src, Mat &dst, vector<double> weights, vector<double> sigmas,int gain, int offset)
    private static native void MultiScaleRetinex_0(long src1_nativeObj, long dst_nativeObj, double[] weight, double[] sigma, int gain, int offset);
}
