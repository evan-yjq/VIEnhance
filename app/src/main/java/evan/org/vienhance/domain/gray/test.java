package evan.org.vienhance.domain.gray;

/**
 * Create By yejiaquan in 2018/12/7 17:26
 */
public class test {
    public native String invokeCmethod();

    static {
        System.loadLibrary("test");//导入生成的链接库文件
    }
}
