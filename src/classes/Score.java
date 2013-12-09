package classes;

import java.util.Scanner;

class Score {
    final int ht;
    final int at;
    final int total;

    Score(Scanner in) {
        
        ht = in.nextInt();
        in.next();
        at = in.nextInt();
        total = at + ht;
    }

    Score(int hT, int aT) {
        ht = hT;
        at = aT;
        total = at + ht;
    }

    public int hashCode() {
        return 31 * ht + at;
    }

    public boolean equals(Object o) {
        Score sc = (Score) o;
        return sc.at == at && sc.ht == ht;
    }

}
