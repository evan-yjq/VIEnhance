package evan.org.vienhance.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import evan.org.vienhance.R;
import org.jetbrains.annotations.NotNull;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By yejiaquan in 2018/10/25 14:13
 */
public class MainFragment extends Fragment implements MainContract.View {

    private static final String TAG = "Main::Fragment";

    private MainContract.Presenter presenter = null;
    private CameraBridgeViewBase mOpenCvCameraView;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(@NotNull MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_frag, container, false);
        mOpenCvCameraView = root.findViewById(R.id.surface_view);
        FloatingActionButton mChangeButton = root.findViewById(R.id.change);
        FloatingActionButton mGamaButton = root.findViewById(R.id.gama);
        FloatingActionButton mFilterButton = root.findViewById(R.id.filter);
        SeekBar arg1SeekBar = root.findViewById(R.id.arg1);
        SeekBar arg2SeekBar = root.findViewById(R.id.arg2);
        SeekBar arg3SeekBar = root.findViewById(R.id.arg3);
        TextView txt1 = root.findViewById(R.id.txt1);
        TextView txt2 = root.findViewById(R.id.txt2);
        TextView txt3 = root.findViewById(R.id.txt3);

        arg1SeekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(txt1, "center", 2, 5));
        arg2SeekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(txt2, "row", -2, 2));
        arg3SeekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener(txt3, "col", -2, 2));

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(new MyCvCameraViewListener2(1));

        mChangeButton.setOnTouchListener(new MyOnTouchListener(2));
        mFilterButton.setOnTouchListener(new MyOnTouchListener(3));
        mGamaButton.setOnTouchListener(new MyOnTouchListener(4));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }else{
            start();
        }
        presenter.start();
    }

    private void start(){
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, getContext(), mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start();
            }else {
                // Permission Denied
                Toast.makeText(getContext(), getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getContext()) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.i(TAG, "CameraView loaded successfully");
                mOpenCvCameraView.enableView();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void show(Mat dst, Handler handler) {
        Message m = new Message();
        m.arg1 = 1;
        m.obj = dst;
        handler.sendMessage(m);
    }

    HashMap<String, Float> argsMap = new HashMap();

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        private String n;
        private int max;
        private int min;
        private TextView tv;

        public MyOnSeekBarChangeListener(TextView tv, String n, int min, int max){
            this.n = n;
            this.min = min;
            this.max = max;
            this.tv = tv;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            float v = (float) i / 100 * (max - min);
            argsMap.put(n, min + v);
            tv.setText(""+(min+v));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnTouchListener implements View.OnTouchListener{

        private int t;

        private MyOnTouchListener(int t){
            this.t = t;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                //松开事件发生后执行代码的区域
                mOpenCvCameraView.setCvCameraViewListener(new MyCvCameraViewListener2(1));
            }else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                //按住事件发生后执行代码的区域
                mOpenCvCameraView.setCvCameraViewListener(new MyCvCameraViewListener2(t));
            }
            return true;
        }
    }

    class MyCvCameraViewListener2 implements CameraBridgeViewBase.CvCameraViewListener2 {
        private int t;

        private MyCvCameraViewListener2(int t){
            this.t = t;
        }

        @Override
        public void onCameraViewStarted(int width, int height) {

        }

        @Override
        public void onCameraViewStopped() {

        }

        @Override
        public void onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame, Handler handler) {
            Mat mRgba = inputFrame.rgba();
            presenter.setRGBA(mRgba);
            if (t == 1){
                presenter.getEnhance("", handler);
            }else if(t == 2){
                presenter.getEnhance("gray",handler);
            }else if(t == 3){
                if (argsMap.get("center") != null && argsMap.get("row") != null && argsMap.get("col") != null)
                    presenter.setArgs(new float[]{argsMap.get("center"), argsMap.get("row"), argsMap.get("col")});
                presenter.getEnhance("laplace",handler);
            }else if(t == 4){
                presenter.getEnhance("gama",handler);
            }else{
                presenter.getEnhance("",handler);
            }
        }
    }
}
