package evan.org.vienhance.domain.retinex;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import evan.org.vienhance.util.OpenCVNDKHelper;
import org.opencv.core.Mat;

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
                OpenCVNDKHelper.detectFeatures(src.getNativeObjAddr(), dst.getNativeObjAddr());
                callback.result(dst);
            }
        });
    }
}
