package evan.org.vienhance.main;

import android.support.annotation.NonNull;
import evan.org.vienhance.UseCase;
import evan.org.vienhance.UseCaseHandler;
import evan.org.vienhance.domain.enhanceUseCase;
import evan.org.vienhance.domain.model.AlgContext;
import evan.org.vienhance.util.AppExecutors;
import evan.org.vienhance.util.Objects;
import org.opencv.core.Mat;


import android.os.Handler;

/**
 * Create By yejiaquan in 2018/10/25 14:19
 */
public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "Main::Presenter";

    private final MainContract.View mView;
    private final UseCaseHandler mUseCaseHandler;

    private Mat src;
    private float[] args;

    private static AlgContext context;


    public MainPresenter(@NonNull MainContract.View mView,
                         @NonNull UseCaseHandler mUseCaseHandler) {
        this.mUseCaseHandler = Objects.checkNotNull(mUseCaseHandler);
        this.mView = Objects.checkNotNull(mView);

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        context = AlgContext.getInstance(new AppExecutors());
    }

    @Override
    public MainPresenter setRGBA(Mat src) {
        this.src = src;
        return this;
    }

    @Override
    public MainPresenter setArgs(float[] args) {
        this.args = args;
        return this;
    }

    @Override
    public void getEnhance(int type, final Handler handler) {
        mUseCaseHandler.execute(new enhanceUseCase(), new enhanceUseCase.RequestValues(src, args, type, context),
                new UseCase.UseCaseCallback<enhanceUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(enhanceUseCase.ResponseValue response) {
                        mView.show(response.getDst(), handler, response.getType());
                        context = response.getContext();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
