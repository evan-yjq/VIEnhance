package evan.org.vienhance.util;

import android.content.res.AssetManager;

public class OpenCVNDKHelper {

    static {
        System.loadLibrary("native-lib");
    }
    public native static void detectFeatures(long srcMatAddr, long dstMatAddr);

    public native static void loadCascade(String filePath);

    public native static void findFace(long srcMatAddr, long dstMatAddr);

    public native static void equalize(long srcMatAddr, long dstMatAddr);

    public native static void gamma(long srcMatAddr, long dstMatAddr);

    public native static void lap(long srcMatAddr, long dstMatAddr, float center, float row, float col);

}
