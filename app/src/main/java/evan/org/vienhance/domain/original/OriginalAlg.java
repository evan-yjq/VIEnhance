package evan.org.vienhance.domain.original;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.laplase.LaplaceAlg;
import evan.org.vienhance.util.AppExecutors;
import org.opencv.core.Mat;

/**
 * Create By yejiaquan in 2018/10/29 13:18
 */
public class OriginalAlg implements enhanceAlg {

    private static volatile OriginalAlg INSTANCE;

    private AppExecutors mAppExecutors;

    private Mat src;

    private OriginalAlg(@NonNull AppExecutors appExecutors){
        mAppExecutors = appExecutors;
    }

    public static OriginalAlg getInstance(@NonNull AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (OriginalAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new OriginalAlg(appExecutors);
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
                callback.result(src);
            }
        });
    }
}
