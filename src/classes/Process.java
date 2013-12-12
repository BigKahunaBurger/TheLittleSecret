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
            in = new Scanner(new File("nhl2010-11_odds"));
            out = new PrintWriter(new File("output.txt"));
        } catch (Exception e) {
            throw new Error(e);
        }

        try {
            solve1();
        } finally {
            out.close();
        }
    }

    double testPredicting(NHL nhl, double k1, double k2, double k3, double k4) {
        nhl.k1 = k1;
        nhl.k2 = k2;
        nhl.k3 = k3;
        nhl.k4 = k4;
        double profit = 0;
        for (int i = 0; i < nhl.getAmountMatches(); i++) {
            double current = nhl.tryGetProfit(nhl.getMatch(i));
            profit += current;
            // Match m = nhl.getMatch(i);
            // out.println("result : " + m.score.total +" --- " + "predict : " +
            // nhl.makeTotalPredict(m) + " --- line : " + m.betLine.total +
            // " --- profit : " + current);
        }
        // out.println(countBets + " " + "->>> profit : " + profit);
        return profit;

    }

    void solve1() {
        NHL nhl = new NHL(in);
        double profit = 0;
        int countBets = 0;
        for (int i = 0; i < nhl.getAmountMatches(); i++) {
            double current = nhl.tryGetProfit(nhl.getMatch(i));
            profit += current;
            Match m = nhl.getMatch(i);
            if (current != 0) {
                countBets++;
            }

            // / out.println("result : " + m.score.total + " --- " +
            // "predict : "
            // / + nhl.makeTotalPredict1(m) + " --- line : "
            // + m.betLine.total + " --- profit : " + current);
        }
        out.println(countBets + " " + "->>> profit : " + profit);

    }

    void solve() {
        NHL nhl = new NHL(in);
        double eps = 0.10;
        double maxProfit = -10000;
        double k[] = new double[4];
        for (double k1 = 1; k1 <= 2.3; k1 += eps) {
            for (double k2 = 1; k2 <= 2.3; k2 += eps) {
                for (double k3 = 1; k3 <= 2.3; k3 += eps) {
                    for (double k4 = 1; k4 <= 2.3; k4 += eps) {
                        double currentProfit = testPredicting(nhl, k1, k2, k3,
                                k4);
                        if (currentProfit > maxProfit) {
                            maxProfit = currentProfit;
                            k[0] = k1;
                            k[1] = k2;
                            k[2] = k3;
                            k[3] = k4;
                        }

                    }
                }
            }
        }
        out.println(maxProfit);
        out.println(k[0] + " " + k[1] + " " + k[2] + " " + k[3]);
    }
}
