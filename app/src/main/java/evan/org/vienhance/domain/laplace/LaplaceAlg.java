package evan.org.vienhance.domain.laplace;

import android.support.annotation.NonNull;
import android.util.Log;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.util.AppExecutors;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Date;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8UC3;

/**
 * Create By yejiaquan in 2018/10/29 11:49
 */
public class LaplaceAlg implements enhanceAlg{

    private static volatile LaplaceAlg INSTANCE;

    private static final float MASK_CENTER = 5;

    private static final float MASK_ROW = -1;

    private static final float MASK_COL = -1;

    private AppExecutors mAppExecutors;

    private Mat src;

    private float center, row, col;

    private LaplaceAlg(@NonNull AppExecutors appExecutors){
        mAppExecutors = appExecutors;
    }

    public static LaplaceAlg getInstance(@NonNull AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (LaplaceAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new LaplaceAlg(appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    public void result(@NonNull final enhanceAlg.AlgCallback callback) {
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long step0 = new Date().getTime();
                Mat dst = new Mat();
                Mat kernel = new Mat(3, 3, CV_32FC1);
                float a[] = {0};
                float b[] = {col};
                float c[] = {0};

                float d[] = {row};
                float e[] = {center};
                float f[] = {row};

                float g[] = {0};
                float h[] = {col};
                float i[] = {0};
                kernel.put(0, 0, a);
                kernel.put(0, 1, b);
                kernel.put(0, 2, c);

                kernel.put(1, 0, d);
                kernel.put(1, 1, e);
                kernel.put(1, 2, f);

                kernel.put(2, 0, g);
                kernel.put(2, 1, h);
                kernel.put(2, 2, i);
                Imgproc.filter2D(src, dst, CV_8UC3, kernel);
                long step1 = new Date().getTime();
                callback.result(dst);
                Log.e("LaplaceAlg:", ""+(step1-step0));
            }
        });
    }

    @Override
    public LaplaceAlg src(Mat src) {
        this.src = src;
        return this;
    }

    @Override
    public LaplaceAlg args(float[] args) {
        this.center = args != null ? args[0] : MASK_CENTER;
        this.row = args != null ? args[1] : MASK_ROW;
        this.col = args != null ? args[2] : MASK_COL;
        return this;
    }
}
