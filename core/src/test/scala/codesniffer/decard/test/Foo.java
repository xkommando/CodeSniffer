package codesniffer.decard.test;

/**
 * Created by Bowen Cai on 1/14/2016.
 */
public class Foo {
  public static void main(String[] args) {
    Foo f = new Foo();
    int a = 7;
    int b = 14;
    int x = (f.bar(21) + a) * b;
//			System.out.println(x);
  }

  public int bar(int n) { return n + 42; }


//  public int test() {
//    int x = 100;
//    while(true) {
//      if(x < 200)
//        x = 100;
//      else
//        x = 200;
//    }
//    return x;
//  }

}