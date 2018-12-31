package evan.org.vienhance.domain.retinex;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import org.opencv.core.Mat;
import org.opencv.dlc.MSRCR;

/**
 * Create By yejiaquan in 2018/10/16 16:39
 */
public class MSRCRAlg implements enhanceAlg{
    private static volatile MSRCRAlg INSTANCE;

    private AlgContext context;

    private Mat src;

    private MSRCRAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static MSRCRAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (MSRCRAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new MSRCRAlg(context);
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
    public void result(final enhanceAlg.AlgCallback callback) {
        context.getAppExecutors().algIO().execute(new Runnable() {
            @Override
            public void run() {
                Mat dst = new Mat();
                double[] weight = new double[]{1/3, 1/3, 1/3};
                double[] sigma = new double[]{30, 150, 300};

                MSRCR.MultiScaleRetinexCR(src, dst, weight, sigma, 128, 128);
                callback.result(dst);
            }
        });
    }
}
