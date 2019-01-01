package evan.org.vienhance.util;

public class OpenCVNDKHelper {

    static {
        System.loadLibrary("native-lib");
    }
    public native static void detectFeatures(long srcMatAddr, long dstMatAddr);

}
