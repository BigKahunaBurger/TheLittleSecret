package classes;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Process {
    public static void main(String[] args) {
        new Process().run();

    }

    Scanner in;
    PrintWriter out;

    void run() {
        try {
            in = new Scanner(new File("nhl2010-11"));
            out = new PrintWriter(new File("output.txt"));
        } catch (Exception e) {
            throw new Error(e);
        }

        try {
            solve();
        } finally {
            out.close();
        }
    }

    void solve() {
        in = in.useDelimiter(",|\n");
        NHL nhl = new NHL(in);
        int countPredict = 0;
        int countGood = 0;
        int down = 0;
        int localMax = 0;
        int cur = 0;
        double min =  6;
        double commonTotal = 0;
        for (int i = 0; i < nhl.getAmountMatches(); i++) {
            int predict = nhl.testPredict(nhl.getMatch(i));
            double total = nhl.makeTotalPredict(nhl.getMatch(i));
            if (predict > 0) {
               // out.println("Good job: \n  " + nhl.getMatch(i)
               //         + " ---- predict - " + total);
                countGood++;
                cur++;
                commonTotal += total;
            } else if (predict == 0) {
              //  out.println("Bad guy");
                cur--;
            }
            if (predict != -1) {
                countPredict++;
            }
            if (predict == -1) {
               // out.println("GIVE ME STATISTIC");
            }
            if (cur >= localMax) {
                localMax = cur;
            }
            if (localMax - cur > down) {
                down = localMax - cur;
            }
            if(total < min && total >= 0){
                min = total;
            }
            //out.println(cur);
        }
        out.println((double) countGood / countPredict);
        out.println(countPredict + " vs " + countGood);
        out.println(nhl.getAmountMatches());
        out.println(down);
        out.println(commonTotal/countGood);
        out.println(min);
    }
}
