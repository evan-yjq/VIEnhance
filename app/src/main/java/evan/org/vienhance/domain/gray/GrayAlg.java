package evan.org.vienhance.domain.gray;

import android.support.annotation.NonNull;
import android.util.Log;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.util.AppExecutors;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Date;

import static org.opencv.imgproc.Imgproc.COLOR_RGBA2GRAY;

/**
 * Create By yejiaquan in 2018/10/29 13:54
 */
public class GrayAlg implements enhanceAlg {

    private static volatile GrayAlg INSTANCE;

    private AppExecutors mAppExecutors;

    private Mat src;

    private GrayAlg(@NonNull AppExecutors appExecutors){
        mAppExecutors = appExecutors;
    }

    public static GrayAlg getInstance(@NonNull AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (GrayAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new GrayAlg(appExecutors);
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
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long step0 = new Date().getTime();
                Mat dst = new Mat();
                Imgproc.cvtColor(src, dst, COLOR_RGBA2GRAY);
                long step1 = new Date().getTime();
                callback.result(dst);
                Log.e("GrayAlg:", ""+(step1-step0));
            }
        });
    }
}
