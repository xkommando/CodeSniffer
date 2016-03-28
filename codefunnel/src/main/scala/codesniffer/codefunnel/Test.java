package codesniffer.codefunnel;

import codesniffer.codefunnel.crawler.PojImport;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//import scala.collection.convert.wrapAsScala

/**
 * Created by bowen on 3/17/2016.
 */
public class Test {
    public static void main(String[]args) throws Throwable {
        Class kls = Class.forName("codesniffer.codefunnel.crawler.PojImport");
        System.out.println(kls.getDeclaredFields().length);
        System.out.println(kls.getFields().length);
        System.out.println(kls.getDeclaredMethods().length); // 5
        System.out.println(kls.getMethods().length); // 14
        for (Method m: kls.getDeclaredMethods()) {
            System.out.print(Modifier.isPublic(m.getModifiers()));
            System.out.println("   " + Modifier.isStatic(m.getModifiers()));
//            System.out.println(Modifier.isPublic(m.getModifiers()));
        }
    }
}
