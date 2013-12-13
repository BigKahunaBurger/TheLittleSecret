package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class NHL {
    ArrayList<Match> matches;
    HashMap<Team, Match> prevMatch;
    int deep = 5;
    double cOfRich = 5.5;
    double eps1;
    double eps2;

    NHL() {
        matches = new ArrayList<Match>();
        prevMatch = new HashMap<Team, Match>();
    }

    void makeLink(Match match) {
        match.prevHTM = match.prevATM = null;
        if (prevMatch.containsKey(match.homeTeam)) {
            match.prevHTM = prevMatch.get(match.homeTeam);
        }
        if (prevMatch.containsKey(match.awayTeam)) {
            match.prevATM = prevMatch.get(match.awayTeam);
        }
        prevMatch.put(match.homeTeam, match);
        prevMatch.put(match.awayTeam, match);
    }

    void addMatch(Scanner in, String str) {
        Match match = new Match(in, str);
        matches.add(match);
        makeLink(match);
    }

    NHL(Scanner in) {
        matches = new ArrayList<Match>();
        prevMatch = new HashMap<Team, Match>();
        while (in.hasNextLine()) {
            String first = in.next();
            if (first.equals("new")) {
                prevMatch.clear();
            }
            addMatch(in, first);
        }
    }

    double makeTotalPredict(Match match) {
        double predict = 0;
        Team homeTeam = match.homeTeam;
        Team awayTeam = match.awayTeam;
        double homeAttack = getAttack(match, homeTeam);
        double homeDefence = getDefence(match, homeTeam);
        double awayAttack = getAttack(match, awayTeam);
        double awayDefence = getDefence(match, awayTeam);
        if (homeAttack < 0 || homeDefence < 0 || awayAttack < 0
                || awayDefence < 0) {
            return -1;
        }
        predict = homeAttack + awayDefence + homeDefence + awayAttack - cOfRich;
        return predict;
    }

    int getScoreForTeam(Match match, Team team) {
        if (match.homeTeam.equals(team)) {
            return match.score.ht;
        } else {
            return match.score.at;
        }

    }

    double getAttack(Match match, Team team) {
        int countMatches = 0;
        int[] goals = new int[deep];
        Match currentMatch = getPrevMatch(team, match);
        for (int i = 0; i < deep && currentMatch != null; i++, currentMatch = getPrevMatch(
                team, currentMatch), countMatches++) {
            goals[i] = getScoreForTeam(currentMatch, team);
            countMatches++;
        }
        if (countMatches < deep)
            return -1;
        double res = 0;
        for (int i = 0; i < goals.length; i++) {
            res += goals[i];
        }

        return res / (deep);
    }

    double getDefence(Match match, Team team) {
        int countMatches = 0;
        int[] goals = new int[deep];
        Match currentMatch = getPrevMatch(team, match);
        for (int i = 0; i < deep && currentMatch != null; i++, currentMatch = getPrevMatch(
                team, currentMatch), countMatches++) {
            goals[i] = currentMatch.score.total
                    - getScoreForTeam(currentMatch, team);
            countMatches++;
        }
        if (countMatches < deep)
            return -1;
        double res = 0;
        for (int i = 0; i < goals.length; i++) {
            res += goals[i];
        }

        return res / (deep);
    }

    double tryGetProfit(Match m) {
        double total = 0;
        if ((total = makeTotalPredict(m)) < 0) {
            return 0;
        }
        return m.betLine.getProfit(total, m.score.total, eps1, eps2);
    }

    Match getMatch(int i) {
        return matches.get(i);
    }

    int getAmountMatches() {
        return matches.size();
    }

    Match getPrevMatch(Team t, Match match) {
        if (match.homeTeam.equals(t))
            return match.prevHTM;
        return match.prevATM;
    }

}
