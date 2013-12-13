package classes;

import java.util.Calendar;
import java.util.Scanner;

class Match {
    Calendar date;
    final Team homeTeam;
    final Team awayTeam;
    final Score score;
    final BetLine betLine;
    Match prevHTM;
    Match prevATM;

    Match(Calendar d, Team hT, Team aT, Score sc, BetLine bt) {
        homeTeam = hT;
        awayTeam = aT;
        score = sc;
        date = d;
        betLine = bt;

    }

    boolean isMoneyLine(String str) {
        return str.charAt(0) == '-';
    }

    Match(Scanner in, String label) {
        int moneyLine = 0;
        TeamType team = TeamType.AWAY;
        String total = null;
        int d = 0;
        if (label.equals("new")) {
            d = in.nextInt();
        } else {
            d = Integer.parseInt(label);
        }
        date = Calendar.getInstance();
        date.set(2000, d / 100, d % 100);
        in.nextInt();
        in.next();
        awayTeam = new Team(in);
        in.next();
        in.next();
        int awayScore = in.nextInt();
        in.next();
        String data = in.next();
        if (isMoneyLine(data)) {
            team = TeamType.AWAY;
            moneyLine = Integer.parseInt(data);
        } else {
            total = data + "";
        }
        in.next();
        in.next();
        in.next();
        homeTeam = new Team(in);
        in.next();
        in.next();
        int homeScore = in.nextInt();
        in.next();
        data = in.next();
        if (isMoneyLine(data)) {
            team = TeamType.HOME;
            moneyLine = Integer.parseInt(data);
        } else {
            total = data + "";
        }
        score = new Score(homeScore, awayScore);

        betLine = new BetLine(team, moneyLine, total);

    }

    public int hashCode() {
        return 31 * 31 * 31 * score.hashCode() + 31 * 31 * date.hashCode() + 31
                * homeTeam.hashCode() + awayTeam.hashCode();
    }

    public boolean equals(Object o) {
        Match m = (Match) o;
        return m.homeTeam.equals(homeTeam) && m.awayTeam.equals(awayTeam)
                && m.score.equals(score);
    }

    public String toString() {
        return homeTeam.name + " <> " + awayTeam.name + " :score: " + score.ht
                + ":" + score.at + " ---- betLine :" + betLine;
    }

}
