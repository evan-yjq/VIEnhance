package evan.org.vienhance.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import evan.org.vienhance.UseCase;
import evan.org.vienhance.domain.gamma.GammaAlg;
import evan.org.vienhance.domain.gray.GrayAlg;
import evan.org.vienhance.domain.laplace.LaplaceAlg;
import evan.org.vienhance.domain.original.OriginalAlg;
import evan.org.vienhance.util.AppExecutors;
import org.opencv.core.Mat;

import static evan.org.vienhance.util.Objects.checkNotNull;
import static evan.org.vienhance.domain.enhanceFilter.*;

/**
 * Create By yejiaquan in 2018/10/25 13:48
 */
public class enhanceUseCase extends UseCase<enhanceUseCase.RequestValues, enhanceUseCase.ResponseValue> {

//    public static int count = 0;
//    public static Mat latest;

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Mat src = requestValues.getSrc();
        float[]args = requestValues.getArgs();
        final int type = requestValues.getType();
        enhanceAlg alg;
        switch (type) {
            case LAPLACE:
                alg = LaplaceAlg.getInstance(new AppExecutors()).src(src).args(args);
                break;
            case GAMMA:
                alg = GammaAlg.getInstance(new AppExecutors()).src(src);
                break;
            case GRAY:
                alg = GrayAlg.getInstance(new AppExecutors()).src(src);
                break;
            default:
                alg = OriginalAlg.getInstance(new AppExecutors()).src(src);
                break;
        }
        alg.result(new enhanceAlg.AlgCallback() {
            @Override
            public void result(Mat dst) {
                getUseCaseCallback().onSuccess(new ResponseValue(dst, type));
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Mat src;

        private final float[] args;

        private final int type;

        public RequestValues(@NonNull Mat src, @Nullable float[] args, @NonNull int type) {
            this.src = checkNotNull(src,"src cannot be null!");
            this.type = checkNotNull(type,"type cannot be null!");
            this.args = args;
        }

        public Mat getSrc() {
            return src;
        }

        public float[] getArgs() {
            return args;
        }

        public int getType() {
            return type;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Mat dst;

        private final int type;

        public ResponseValue(@NonNull Mat dst, int type) {
            this.dst = checkNotNull(dst, "dst cannot be null!");
            this.type = checkNotNull(type, "type cannot be null!");
        }

        public Mat getDst() {
            return dst;
        }

        public int getType() {
            return type;
        }
    }
}
