package evan.org.vienhance.domain.gamma;

import android.support.annotation.NonNull;
import android.util.Log;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import org.opencv.core.Mat;

import java.util.Date;

import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.core.Core.*;
import static org.opencv.core.CvType.CV_32FC3;
import static evan.org.vienhance.domain.enhanceFilter.GAMMA;

/**
 * Create By yejiaquan in 2018/10/25 13:49
 */
public class GammaAlg implements enhanceAlg {

    private static volatile GammaAlg INSTANCE;

    private static double[]lut = new double[256];

    private AlgContext context;

    private Mat src;

    private double fGamma;

    private GammaAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static GammaAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (GammaAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new GammaAlg(context);
                    for (int i = 0; i < 256; i++) lut[i] = Math.pow(i, 3);
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
        this.fGamma = args!=null?args[0]:0.45;
        return this;
    }

    @Override
    public void result(final AlgCallback callback) {
        context.getAppExecutors().algIO().execute(new Runnable() {
            @Override
            public void run() {
                long step0 = new Date().getTime();
                Mat dst = new Mat(src.size(), CV_32FC3);
                long step1 = new Date().getTime();
                for (int i = src.rows()-1; i >= 0 ; i--) {
                    for (int j = src.cols() - 1; j >= 0; j--) {
                        double[]v = src.get(i, j);
                        double[]k = new double[]{lut[(int)(v[0])], lut[(int)(v[1])], lut[(int)(v[2])]};
                        dst.put(i, j, k);
                    }
                }

                long step2 = new Date().getTime();
                normalize(dst, dst, 0, 255, NORM_MINMAX);
                long step3 = new Date().getTime();
                convertScaleAbs(dst, dst);
                long step4 = new Date().getTime();
                Log.e("step1", ""+(step1-step0));
                Log.e("step2", ""+(step2-step1));
                Log.e("step3", ""+(step3-step2));
                Log.e("step4", ""+(step4-step3));
                callback.result(dst);
            }
        });
    }
}
