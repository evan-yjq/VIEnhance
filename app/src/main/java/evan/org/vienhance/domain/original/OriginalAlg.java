package evan.org.vienhance.domain.original;

import evan.org.vienhance.domain.enhanceAlg;
import org.opencv.core.Mat;

import static evan.org.vienhance.domain.enhanceFilter.ORIGINAL;

/**
 * Create By yejiaquan in 2018/10/29 13:18
 */
public class OriginalAlg implements enhanceAlg {

    private static volatile OriginalAlg INSTANCE;

    private Mat src;

    public static OriginalAlg getInstance(){
        if (INSTANCE == null){
            synchronized (OriginalAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new OriginalAlg();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public enhanceAlg src(Mat src) {
        this.src = src;
        return this;
    }

    @Override
    public enhanceAlg args(float[] args) {
        return this;
    }

    @Override
    public void result(final AlgCallback callback) {
        callback.result(src, ORIGINAL);
    }
}
