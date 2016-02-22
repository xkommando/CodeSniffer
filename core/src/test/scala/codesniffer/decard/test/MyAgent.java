package codesniffer.decard.test;

import java.lang.instrument.*;

/**
 * Created by Bowen Cai on 1/20/2016.
 */
public class MyAgent {

  public static void premain(String args, Instrumentation inst) {
    Object[] array = new Object[10];
    Object obj = new Object();
    long size = inst.getObjectSize(obj);
    System.out.println("Bytes used by object: " + inst.getObjectSize(array));
    System.out.println("Bytes used by object: " + size);
  }

//  public static void main(String[] args) {
//    System.out.println("haha inst");
//  }
}