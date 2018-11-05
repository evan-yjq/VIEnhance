package evan.org.vienhance.domain.adjunction;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import org.opencv.core.Mat;

/**
 * Create By yejiaquan in 2018/11/5 14:20
 */
public class AdjunctionAlg implements enhanceAlg {

    private static volatile AdjunctionAlg INSTANCE;

    private AlgContext context;

    private Mat src;

    private AdjunctionAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static AdjunctionAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (AdjunctionAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new AdjunctionAlg(context);
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
    public void result(AlgCallback callback) {

    }
}
