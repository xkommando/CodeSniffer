package codesniffer.clang;//package
import java.util.*;
import java.util.concurrent.*;

//package codesniffer.clang;
//
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.*;
//
///**
// * Created by Bowen Cai on 1/1/2016.
// */
public class T {
  public int partition(int[] arr, int start, int stop) {
    return 0;
  }

  ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

  public void multiThreadSort(
      int[] arr, int left, int right) {
    multiThreadSort(
      Runtime.getRuntime().availableProcessors(),
      arr, left, right);
  }
  public void multiThreadSort(
      final int threads,
      final int[] arr,
      final int left, final int right)  {
    if (threads > 1) {
      final int mid = partition(arr, left, right);
      Future f1 = pool.submit(new Runnable() {
        public void run() {
          multiThreadSort(threads - 1,
              arr, left, mid);
        }
      });
      Future f2 = pool.submit(new Runnable() {
        public void run() {
          multiThreadSort(threads - 1,
              arr, mid, right);
        }
      });
//      f1.get();
//      f2.get();
    } else
      Arrays.sort(arr, left, right);
  }


  public void SeqSort(
      int[] arr, int left, int right) {
    Arrays.sort(arr, left, right);
  }

}
//  private static final int N_THREADS = Runtime.getRuntime().availableProcessors();
//  private static final int FALLBACK = 2;
//  private static Executor pool = Executors.newFixedThreadPool(N_THREADS);
//
//  public <T> void quicksort(T[] input) {
//    AtomicInteger count = new AtomicInteger(1);
//    pool.execute(
//        new QuicksortRunnable<T>(
//            input,0, input.length - 1, count));
//    try {
//      synchronized (count) {
//        count.wait();
//      }
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }
//
//  class QuicksortRunnable<T>
//      implements Runnable {
//    private final T[] arr;
//    private final int left;
//    private final int right;
//    private final AtomicInteger count;
//
//    public QuicksortRunnable(T[] values,
//                             int left, int right,
//                             AtomicInteger count) {
//      this.arr = values;
//      this.left = left;
//      this.right = right;
//      this.count = count;
//    }
//
//    public void run() {
//      quicksort(left, right);
//      synchronized (count) {
//        if (count.getAndDecrement() == 1)
//          count.notify();
//      }
//    }
//
//    void quicksort(int pLeft, int pRight) {
//      if (pLeft < pRight) {
//        int idx = partition(pLeft, pRight);
//        if (count.get() >= FALLBACK * N_THREADS) {
//          quicksort(pLeft, idx - 1);
//          quicksort(idx + 1, pRight);
//        } else {
//          count.getAndAdd(2);
//          pool.execute(
//              new QuicksortRunnable<T>(
//                  arr,pLeft, idx - 1, count));
//          pool.execute(
//              new QuicksortRunnable<T>(
//                  arr,idx + 1, pRight, count));
//        }
//      }
//    }
//  }
//}
