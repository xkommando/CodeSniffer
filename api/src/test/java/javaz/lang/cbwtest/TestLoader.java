package javaz.lang.cbwtest;

import java.io.File;
import java.io.IOException;

/**
 *  java.lang.SecurityException: Prohibited package name: java.lang.cbwtest
 * Created by superbow on 3/28/2016.
 */
public class TestLoader {
    static {
        ClassLoader cl = TestLoader.class.getClassLoader();
        while (cl != null) {
            System.out.println(cl);
            cl = cl.getParent();
        }
    }
    public static void main(String[] args) throws IOException {
        File pc = new File("C:\\ProgramData\\Package Cache\\{1ECA24CC-5551-31C9-A10F-1DDC0D21C855}v14.0.24720");
        if (pc.isDirectory()) {
            System.out.println(pc.getAbsolutePath());
            System.out.println(pc.getCanonicalPath());
            System.out.println(pc.length());
        }
//        System.out.println("Hello");
//        ClassLoader cl = TestLoader.class.getClassLoader();
//        while (cl != null) {
//            System.out.println(cl);
//            cl = cl.getParent();
//        }
    }
}
