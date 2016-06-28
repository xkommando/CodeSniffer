package codesniffer.java8;

import java.util.concurrent.*;

/**
 * Created by superbow on 5/21/2016.
 */
public class _Test {

    static volatile int iacc = 0;
    static volatile int iacc2 = 0;
    static volatile long lacc = 0;
    static volatile long lacc2 = 0;

    static final int NUM = 536870000;

    static void single() {
        int a = NUM;
        while (a-- > 0) {
            iacc++;
            iacc2 += 2;
            lacc++;
            lacc2 += 2;
        }
    }

//  2147483647
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(8);
        Future f1 = exe.submit(new Runnable() {
            @Override
            public void run() {
                int a = NUM;
                while (a-- > 0) {
                    iacc++;
                    iacc2 += 2;
                    lacc++;
                    lacc2 += 2;
                }
                System.out.println("Done " + Thread.currentThread().getId());
            }
        });
        Future f2 = exe.submit(new Runnable() {
            @Override
            public void run() {
                int a = NUM;
                while (a-- > 0) {
                    iacc++;
                    iacc2 += 2;
                    lacc++;
                    lacc2 += 2;
                }
                System.out.println("Done " + Thread.currentThread().getId());
            }
        });
        Future f3 = exe.submit(new Runnable() {
            @Override
            public void run() {
                int a = NUM;
                while (a-- > 0) {
                    iacc++;
                    iacc2 += 2;
                    lacc++;
                    lacc2 += 2;
                }
                System.out.println("Done " + Thread.currentThread().getId());
            }
        });

        long a = NUM;
        while (a-- > 0) {
            iacc++;
            iacc2 += 2;
            lacc++;
            lacc2 += 2;
        }
        System.out.println("Done " + Thread.currentThread().getId());
        f1.get();
        f2.get();
        f3.get();
        System.out.println(iacc + " " + iacc2 + "\r\n" + lacc + "  " + lacc2);
//        no lock
//        1715846946 -867447920
//        1716442355  3436009550

//        1720107822 -854341900
//        1720097974  3438528774

        iacc = 0;
        iacc2 = 0;
        lacc = 0;
        lacc2 = 0;
        single();
        System.out.println(iacc + " " + iacc2 + "\r\n" + lacc + "  " + lacc2);
//        536870000 1073740000
//        536870000  1073740000

    }
}
