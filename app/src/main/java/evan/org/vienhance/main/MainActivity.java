package evan.org.vienhance.main;

/**
 * Create By yejiaquan in 2018/10/17 11:06
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import evan.org.vienhance.Injection;
import evan.org.vienhance.R;
import evan.org.vienhance.util.ActivityUtils;

import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main::Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.main_act);

        MainFragment mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mainFragment == null){
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mainFragment,R.id.contentFrame);
        }

        MainPresenter presenter = new MainPresenter(mainFragment,
                Injection.provideUseCaseHandler());
    }
}
