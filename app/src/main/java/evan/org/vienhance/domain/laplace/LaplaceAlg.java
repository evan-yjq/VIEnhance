package evan.org.vienhance.domain.laplace;

import android.support.annotation.NonNull;
import android.util.Log;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import evan.org.vienhance.util.OpenCVNDKHelper;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Date;
import java.util.HashMap;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8UC3;
import static evan.org.vienhance.domain.enhanceFilter.LAPLACE;

/**
 * Create By yejiaquan in 2018/10/29 11:49
 */
public class LaplaceAlg implements enhanceAlg{

    private static volatile LaplaceAlg INSTANCE;

    private static final float MASK_CENTER = 5;

    private static final float MASK_ROW = -1;

    private static final float MASK_COL = -1;

    private AlgContext context;

    private Mat src;

    private float center, row, col;

    private LaplaceAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static LaplaceAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (LaplaceAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new LaplaceAlg(context);
                }
            }
        }
        return INSTANCE;
    }

    public void result(@NonNull final enhanceAlg.AlgCallback callback) {
        context.getAppExecutors().algIO().execute(new Runnable() {
            @Override
            public void run() {
                Mat dst = new Mat();
                OpenCVNDKHelper.lap(src.getNativeObjAddr(), dst.getNativeObjAddr(), center, row, col);
                callback.result(dst);
//                Log.e("LaplaceAlg:", ""+(step1-step0));
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
