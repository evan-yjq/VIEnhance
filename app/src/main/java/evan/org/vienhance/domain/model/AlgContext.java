package evan.org.vienhance.domain.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import evan.org.vienhance.util.AppExecutors;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By yejiaquan in 2018/11/5 08:12
 */
public class AlgContext {

    private static volatile AlgContext INSTANCE;

    private final AppExecutors APP_EXECUTORS;

    private Context context;

    // 所选添加编号
    private int adjunction_num;

    // 所选添加类型
    private int adjunction_type;

    // 所选添加图集
    private Bitmap[] adjunctions;

    // 所选图集长度
    private int adjunction_count;

    // 所选图集进行帧
    private int adjunction_now;

    public static final int PNG = 1;

    public static final int GIF = 2;

    // 增强方式
    private Map<Integer, Double> enhance_type;

    @SuppressLint("UseSparseArrays")
    private AlgContext(AppExecutors appExecutors, Context context){
        APP_EXECUTORS = appExecutors;
        enhance_type = new HashMap<>();
        this.context = context;
    }

    public static AlgContext getInstance(AppExecutors appExecutors, Context context){
        if (INSTANCE == null){
            synchronized (AlgContext.class){
                if (INSTANCE == null){
                    INSTANCE = new AlgContext(appExecutors, context);
                }
            }
        }
        return INSTANCE;
    }

    //
    public void addEnhance(int type, double scale){
        enhance_type.put(type, scale);
    }

    @SuppressLint("UseSparseArrays")
    public AlgContext clearEnhance(){
        enhance_type = new HashMap<>();
        return this;
    }

    public boolean isEnhance(int type){
        return enhance_type.containsKey(type);
    }

    //
    public AppExecutors getAppExecutors(){
        return APP_EXECUTORS;
    }

    public Context getContext(){
        return context;
    }

    // 获取当前帧添加的动画
    public Bitmap getAdjunction(){
        if (adjunction_now == adjunction_count) adjunction_now = 0;
        return adjunctions[adjunction_now];
    }

    // 设置输入图编号
    public void setAdjunctionNum(int num){
        adjunction_num = num;
        getAdjunctionInfo();
    }

    // 获取输入图信息
    private void getAdjunctionInfo(){
        // todo 根据adjunction_num获得adjunction_type
        setAdjunctions();
    }

    // 设置输入图
    public void setAdjunctions() {
        if (adjunction_type == PNG){
            adjunction_count = 1;
            // todo
        }else if (adjunction_type == GIF){
            dismemberGif();
        }
    }

    // 分解gif图
    public void dismemberGif(){
        // todo
    }
}
