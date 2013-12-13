package classes;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
            solve2();
        } finally {
            out.close();
        }
    }

    int count = 0;

    void solve2() {
        NHL nhl = new NHL(in);
        int[] totals = new int[30];
        for (Match m : nhl.matches) {
            totals[m.score.total]++;
        }
        for (int i = 0; i < totals.length; i++) {
            if (totals[i] != 0) {
                out.println(i + " -> " + totals[i]
                        / (nhl.getAmountMatches() + 0.0));
            }
        }
    }

    void solve3() {
        NHL nhl = new NHL(in);
        int count = 0;
        for (int i = 1230*2; i < 1230*3; i++) {
            Match m = nhl.getMatch(i);
            if (m.score.total == 7) {
                count++;
            }
        }
        out.println(count / 1230.0);
    }

    void solve1() {
        NHL nhl = new NHL(in);
        double win = 0;
        int count = 0;
        double predicts = 0;
        for (int i = 0; i < nhl.getAmountMatches(); i++) {
            double predict = nhl.makeTotalPredict(nhl.getMatch(i));
            // out.println(predict);
            if (predict <= 0) {
                continue;
            }
            if (predict < 7) {
                continue;
            }
            count++;
            if (predict - nhl.getMatch(i).score.total < 1) {
                win++;
            }
            predicts += nhl.getMatch(i).score.total;

        }
        out.println(count);
        out.println(win / count);
        out.println(predicts / count);
    }

    double testPredicting(NHL nhl) {
        double profit = 0;
        count = 0;
        for (int i = 0; i < nhl.getAmountMatches(); i++) {

            double current = nhl.tryGetProfit(nhl.getMatch(i));
            profit += current;
            if (current != 0) {
                count++;
                // out.println(i + " " + profit);
            }
            // Match m = nhl.getMatch(i);
            // out.println("result : " + m.score.total + " --- " + "predict : "
            // + nhl.makeTotalPredict(m) + " --- line : "
            // + m.betLine.total + " --- profit : " + current);
        }
        // out.println(countBets + " " + "->>> profit : " + profit);
        return profit;

    }

    void printResult(NHL nhl) {
        double c = 0;
        int amount = 0;
        for (int i = 0; i < 4; i++) {
            double profit = 0;
            for (int j = i * 1230; j < (i + 1) * 1230
                    && j < nhl.getAmountMatches(); j++) {
                double add = nhl.tryGetProfit(nhl.getMatch(j));
                profit += add;
                if (add > 0) {
                    c += add + 1;
                    amount++;
                }
                // out.println(nhl.tryGetProfit(nhl.getMatch(j)));
            }
            out.println((i + 1) + " - profit : " + profit);
        }
        out.println(testPredicting(nhl));
        out.println(c / amount);
    }

    void solve() {
        NHL nhl = new NHL(in);
        double e1 = 0, c = 0, e2 = 0;
        double minProfit = -0;
        int cBets = 0;
        double prPerBank = 0;
        nhl.deep = 6;
        for (double eps1 = 0.5; eps1 <= 0.5; eps1 += 0.25)
            for (double eps2 = 0.5; eps2 <= 0.5; eps2 += 0.25)
                for (double rich = 3; rich <= 6; rich += 0.5) {
                    nhl.eps1 = eps1;
                    nhl.eps2 = eps2;
                    nhl.cOfRich = rich;
                    double profit = testPredicting(nhl);
                    if (count >= 100 && profit < minProfit) {
                        minProfit = profit;
                        e1 = eps1;
                        c = rich;
                        e2 = eps2;
                        cBets = count;
                        prPerBank = profit / count;
                    }
                }
        out.println(minProfit);
        out.println(e1 + " e2: " + e2 + " substract: " + c + " cbets: " + cBets
                + " %: " + prPerBank);
        nhl.eps1 = 0.5;
        nhl.eps2 = 0.5;
        nhl.cOfRich = 5;
        printResult(nhl);
    }
}