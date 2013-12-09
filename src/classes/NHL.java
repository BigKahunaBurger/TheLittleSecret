package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class NHL {
    ArrayList<Match> matches;
    HashMap<Team, Match> prevMatch;
    int deep = 10;
    double k1 = 1.5;
    double k2 = 2;
    double k3 = 1.5;
    double k4 = 1;

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

    void addMatch(Scanner in) {
        Match match = new Match(in, 1);
        matches.add(match);
        makeLink(match);
    }

    NHL(Scanner in) {
        matches = new ArrayList<Match>();
        prevMatch = new HashMap<Team, Match>();
        String currentDel = new String(in.delimiter().toString());
        while (in.hasNextLine()) {
            addMatch(in);
        }
        in.useDelimiter(currentDel);
    }

    double makeTotalPredict(Match match) {
        double predict = 0;
        Team homeTeam = match.homeTeam;
        Team awayTeam = match.awayTeam;
        double hGoalFor = getAvrGoalFor(match, homeTeam);
        double hGoalAgt = getAvrGoalAgt(match, homeTeam);
        double aGoalFor = getAvrGoalFor(match, awayTeam);
        double aGoalAgt = getAvrGoalAgt(match, awayTeam);
        if (hGoalFor < 0 || aGoalFor < 0) {
            return -1;
        }
        predict = ((hGoalFor + aGoalAgt) / 2 + (hGoalAgt + aGoalFor) / 2) / 2;
        return predict;
    }

    int testPredict(Match match) {
        if (makeTotalPredict(match) < 0) {
            return -1;
        }
        return match.score.total >= makeTotalPredict(match) ? 1 : 0;
    }

    Match getMatch(int i) {
        return matches.get(i);
    }

    int getAmountMatches() {
        return matches.size();
    }

    double getGoalForTeam(Match match, Team team) {
        if (match.homeTeam.equals(team)) {
            return k1 * match.score.ht;
        } else {
            return k2 * match.score.at;
        }

    }

    double getGoalAgtTeam(Match match, Team team) {
        if (match.homeTeam.equals(team)) {
            return k3 * match.score.at;
        } else {
            return k4 * match.score.ht;
        }

    }

    Match getPrevMatch(Team t, Match match) {
        if (match.homeTeam.equals(t))
            return match.prevHTM;
        return match.prevATM;
    }

    double getAvrGoalFor(Match match, Team team) {
        int countMatches = 0;
        double res = 0;
        Match currentMatch = getPrevMatch(team, match);
        for (int i = 0; i < deep && currentMatch != null; i++, currentMatch = getPrevMatch(
                team, currentMatch), countMatches++) {
            double k = getGoalForTeam(currentMatch, team);
            res += k;
        }

        res /= countMatches;
        if (countMatches < deep)
            return -1;
        return res;
    }

    double getAvrGoalAgt(Match match, Team team) {
        int countMatches = 0;
        double res = 0;
        Match currentMatch = getPrevMatch(team, match);
        for (int i = 0; i < deep && currentMatch != null; i++, currentMatch = getPrevMatch(
                team, currentMatch), countMatches++) {
            double k = getGoalAgtTeam(currentMatch, team);
            res += k;
        }
        res /= countMatches;
        if (countMatches < deep)
            return -1;
        return res;
    }

}
