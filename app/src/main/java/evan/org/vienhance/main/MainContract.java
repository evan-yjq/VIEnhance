package evan.org.vienhance.main;

import evan.org.vienhance.BasePresenter;
import evan.org.vienhance.BaseView;
import org.opencv.core.Mat;

import android.os.Handler;

/**
 * Create By yejiaquan in 2018/10/25 14:09
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {

        void show(Mat dst, Handler handler, int type);

        void reverse();
    }


    interface Presenter<T extends Presenter> extends BasePresenter{

        T setRGBA(Mat src);

        T setArgs(float[] args);

        void getEnhance(int type, Handler handler);
    }
}
