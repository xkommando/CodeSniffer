import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionMode;
import java.io.File;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Test {

    public static boolean profile = false;

    public static boolean notree = false;

    public static boolean gui = false;

    public static boolean printTree = false;

    public static boolean SLL = false;

    public static boolean diag = false;

    public static boolean bail = false;

    public static boolean x2 = false;

    public static boolean threaded = false;

    public static boolean quiet = false;

    public static Worker[] workers = new Worker[3];

    static int windex = 0;

    public static CyclicBarrier barrier;

    public static boolean firstPassDone = false;

    public static class Worker implements Runnable {

        public long parserStart;

        public long parserStop;

        List<String> files;

        public Worker(List<String> files) {
            this.files = files;
        }

        @Override
        public void run() {
            parserStart = System.currentTimeMillis();
            for (String f : files) {
                parseFile(f);
            }
            parserStop = System.currentTimeMillis();
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                return;
            } catch (BrokenBarrierException ex) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        doAll(args);
    }

    public static void doAll(String[] args) {
        List<String> inputFiles = new ArrayList<String>();
        long start = System.currentTimeMillis();
        try {
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-notree"))
                        notree = true;
                    else if (args[i].equals("-gui"))
                        gui = true;
                    else if (args[i].equals("-ptree"))
                        printTree = true;
                    else if (args[i].equals("-SLL"))
                        SLL = true;
                    else if (args[i].equals("-bail"))
                        bail = true;
                    else if (args[i].equals("-diag"))
                        diag = true;
                    else if (args[i].equals("-2x"))
                        x2 = true;
                    else if (args[i].equals("-threaded"))
                        threaded = true;
                    else if (args[i].equals("-quiet"))
                        quiet = true;
                    if (args[i].charAt(0) != '-') {
                        inputFiles.add(args[i]);
                    }
                }
                List<String> javaFiles = new ArrayList<String>();
                for (String fileName : inputFiles) {
                    List<String> files = getFilenames(new File(fileName));
                    javaFiles.addAll(files);
                }
                doFiles(javaFiles);
                if (x2) {
                    System.gc();
                    System.out.println("waiting for 1st pass");
                    if (threaded) {
                        System.out.println("threaded  146");
                        while (!firstPassDone) {
                        }
                    }
                    System.out.println("2nd pass");
                    doFiles(javaFiles);
                }
            } else {
                System.err.println("Usage: java Main <directory or file name>");
            }
        } catch (Exception e) {
            System.err.println("exception: " + e);
            e.printStackTrace(System.err);
        }
        long stop = System.currentTimeMillis();
        System.gc();
    }

    public static void doFiles(List<String> files) throws Exception {
        long parserStart = System.currentTimeMillis();
        if (threaded) {
            barrier = new CyclicBarrier(3, new Runnable() {

                public void run() {
                    report();
                    firstPassDone = true;
                }
            });
            int chunkSize = files.size() / 3;
            int p1 = chunkSize;
            int p2 = 2 * chunkSize;
            workers[0] = new Worker(files.subList(0, p1 + 1));
            workers[1] = new Worker(files.subList(p1 + 1, p2 + 1));
            workers[2] = new Worker(files.subList(p2 + 1, files.size()));
            new Thread(workers[0], "worker-" + windex++).start();
            new Thread(workers[1], "worker-" + windex++).start();
            new Thread(workers[2], "worker-" + windex++).start();
            System.out.println("threaded  167");
        } else {
            for (String f : files) {
                parseFile(f);
            }
            long parserStop = System.currentTimeMillis();
            System.out.println("Total lexer+parser time " + (parserStop - parserStart) + "ms.");
        }
    }

    private static void report() {
        long time = 0;
        if (workers != null) {
            for (Worker w : workers) {
                long wtime = w.parserStop - w.parserStart;
                time = Math.max(time, wtime);
                System.out.println("worker time " + wtime + "ms.");
            }
        }
        System.out.println("Total lexer+parser time " + time + "ms.");
        System.out.println("finished parsing OK");
        System.out.println(LexerATNSimulator.match_calls + " lexer match calls");
    }

    public static List<String> getFilenames(File f) throws Exception {
        List<String> files = new ArrayList<String>();
        getFilenames_(f, files);
        return files;
    }

    public static void getFilenames_(File f, List<String> files) throws Exception {
        if (f.isDirectory()) {
            String flist[] = f.list();
            for (int i = 0; i < flist.length; i++) {
                getFilenames_(new File(f, flist[i]), files);
            }
        } else if (((f.getName().length() > 5) && f.getName().substring(f.getName().length() - 5).equals(".java"))) {
            files.add(f.getAbsolutePath());
        }
    }

    public static void parseFile(String f) {
        try {
            if (!quiet)
                System.err.println(f);
            Lexer lexer = new Java8Lexer(new ANTLRFileStream(f));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Java8Parser parser = new Java8Parser(tokens);
            if (diag) {
                System.out.println("diag  271");
                parser.addErrorListener(new DiagnosticErrorListener());
            }
            if (bail) {
                System.out.println("bail  272");
                parser.setErrorHandler(new BailErrorStrategy());
            }
            if (SLL)
                parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
            ParserRuleContext t = parser.compilationUnit();
            if (notree) {
                System.out.println("notree  277");
                parser.setBuildParseTree(false);
            }
            if (gui)
                t.inspect(parser);
            if (printTree) {
                System.out.println("printTree  279");
                System.out.println(t.toStringTree(parser));
            }
        } catch (Exception e) {
            System.err.println("parser exception: " + e);
            e.printStackTrace();
        }
    }
}
