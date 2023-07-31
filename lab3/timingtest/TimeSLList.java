package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int ops = 10000;
        //Build an AList for the size of SLList NS
        AList<Integer> Ns = new AList<>();
        for (int N = 1000; N <= 128000; N = N * 2) {
            Ns.addLast(N);
        }
        int sizeOfTable = Ns.size();
        //Build an Alist for number of operations OPCOUNTS
        AList<Integer> opCounts = new AList<>();
        for (int i = 0; i < sizeOfTable; i++) {
            opCounts.addLast(ops);
        }
        //Build an Alist for elapsed time TIMES
        AList<Double> times = new AList<>();
        for (int i = 0; i < sizeOfTable; i++) {
            //Create an SLList, and add N items to the SLList
            SLList<Integer> test = new SLList<>();
            for (int n = 0; n < Ns.get(i); n++) {
                test.addLast(n);
            }
            //Start the timer, and perform M getLast() operations on the SLList
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < ops; j++) {
                test.getLast();
            }
            times.addLast(sw.elapsedTime());
        }
        printTimingTable(Ns, times, opCounts);
    }

}
