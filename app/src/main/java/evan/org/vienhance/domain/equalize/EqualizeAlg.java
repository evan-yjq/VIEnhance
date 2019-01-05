package evan.org.vienhance.domain.equalize;

import android.support.annotation.NonNull;
import evan.org.vienhance.domain.adjunction.AdjunctionAlg;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import evan.org.vienhance.util.OpenCVNDKHelper;
import org.opencv.core.Mat;

public class EqualizeAlg implements enhanceAlg {

    private static volatile EqualizeAlg INSTANCE;

    private AlgContext context;

    private Mat src;

    private EqualizeAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static EqualizeAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (EqualizeAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new EqualizeAlg(context);
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
        context.getAppExecutors().algIO().execute(new Runnable() {
            @Override
            public void run() {
                Mat dst = new Mat();
                OpenCVNDKHelper.equalize(src.getNativeObjAddr(), dst.getNativeObjAddr());
                callback.result(dst);
            }
        });
    }
}
