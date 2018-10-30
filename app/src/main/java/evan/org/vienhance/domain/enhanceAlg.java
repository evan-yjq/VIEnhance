package evan.org.vienhance.domain;

import org.opencv.core.Mat;

/**
 * Create By yejiaquan in 2018/10/25 13:51
 */
public interface enhanceAlg <T extends enhanceAlg>{

    T src(Mat src);

    T args(float[] args);

    void result(AlgCallback callback);

    interface AlgCallback{
        void result(Mat dst);
    }
}
