package evan.org.vienhance.domain.face;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import evan.org.vienhance.R;
import evan.org.vienhance.domain.enhanceAlg;
import evan.org.vienhance.domain.model.AlgContext;
import evan.org.vienhance.domain.retinex.MSRCRAlg;
import evan.org.vienhance.util.OpenCVNDKHelper;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FindFaceAlg implements enhanceAlg {
    private static volatile FindFaceAlg INSTANCE;

    private AlgContext context;

    private Mat src;

    private File mCascadeFile;

    private FindFaceAlg(@NonNull AlgContext context){
        this.context = context;
    }

    public static FindFaceAlg getInstance(@NonNull AlgContext context){
        if (INSTANCE == null){
            synchronized (FindFaceAlg.class){
                if (INSTANCE == null){
                    INSTANCE = new FindFaceAlg(context);
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
    public void result(final enhanceAlg.AlgCallback callback) {
        context.getAppExecutors().algIO().execute(new Runnable() {
            @Override
            public void run() {
                Mat dst = new Mat();
                try {
                    // load cascade file from application resources
                    InputStream is = context.getContext().getResources().openRawResource(R.raw.lbpcascade_frontalface);
                    File cascadeDir = context.getContext().getDir("cascade", Context.MODE_PRIVATE);
                    mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                    if (mCascadeFile.exists())return;
                    FileOutputStream os = new FileOutputStream(mCascadeFile);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    is.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OpenCVNDKHelper.loadCascade(mCascadeFile.getAbsolutePath());
                OpenCVNDKHelper.findFace(src.getNativeObjAddr(), dst.getNativeObjAddr());
                callback.result(dst);
            }
        });
    }
}
