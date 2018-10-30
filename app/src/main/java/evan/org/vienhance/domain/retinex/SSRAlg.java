package evan.org.vienhance.domain.retinex;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.util.AppExecutors;
import org.opencv.core.Mat;

/**
 * Create By yejiaquan in 2018/10/16 16:38
 */
public class SSRAlg implements enhanceAlg {

    private static volatile SSRAlg INSTANCE;

    private AppExecutors mAppExecutors;

    private Mat src;

//    private double fGamma;

    private SSRAlg(@NonNull AppExecutors appExecutors){
        mAppExecutors = appExecutors;
    }

    public static SSRAlg getInstance(@NonNull AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (SSRAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new SSRAlg(appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public enhanceAlg src(Mat src) {
        return this;
    }

    @Override
    public enhanceAlg args(float[] args) {
        return this;
    }

    @Override
    public void result(AlgCallback callback) {

    }
}
