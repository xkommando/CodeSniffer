package codesniffer.codefunnel;

public class bug {
//    static Point currentPos = new Point(1, 2);
    static Point currentPos;
    static {
        currentPos = new Point();
        currentPos.init(1,2);
    }
    static class Point {
        int x;
        int y;
        void init(int x, int y) {
            this.y = y;
            this.x = x;
//            System.out.println(this.x);
        }
//        Point(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
    }
    public static void main(String[] args) {
        new Thread() {
            void f(Point p) {
                synchronized (this) {}
//                System.out.println(p.x+" "+p.y); //p.x = 0
                int a = p.x + 1;
                int b = p.y;
                if (a != b) {
                    System.out.println(a + " " + b);
                    System.out.println(p.x + " " + p.y);
                    System.exit(1);
                }
            }

            @Override
            public void run() {
                while (currentPos == null) ;

                while (true)
                    f(currentPos);
            }
        }.start();
        while (true) {
//            currentPos = new Point(currentPos.x + 1, currentPos.y + 1);
            Point _p = new Point();//1
            _p.init(currentPos.x + 1, currentPos.y + 1);//2
            currentPos = _p;//3
            //      System.out.println("x="+currentPos.x+" "+"y="+currentPos.y);
        }

    }

}