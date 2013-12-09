package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

class Match {
    Date date;
    final Team homeTeam;
    final Team awayTeam;
    final Score score;
    final BetLine betLine;
    Match prevHTM;
    Match prevATM;

    Match(Date d, Team hT, Team aT, Score sc, BetLine bt) {
        homeTeam = hT;
        awayTeam = aT;
        score = sc;
        date = d;
        betLine = bt;
        
    }

    Match(Scanner in,int lol) {
        betLine = null;
        String dStr = in.next();
        String tokens[] = dStr.split(" ");
        Calendar cal = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(tokens[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        cal.set(Integer.parseInt(tokens[3]), month, Integer.parseInt(tokens[2]));
        date.setTime(cal.getTimeInMillis());
        homeTeam = new Team(in);
        int ht = in.nextInt();
        awayTeam = new Team(in);
        int at = in.nextInt();
        score = new Score(ht, at);

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
                + ":" + score.at;
    }

}
