package classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BetLine {
    double total;
    double over;
    double under;
    double home;
    double away;
    double spread;

    BetLine(TeamType type, int moneyLine, String strTot) {
        if (type.equals(TeamType.HOME)) {
            home = getDecC(moneyLine);
            away = getAnotherC(home);
        } else {
            away = getDecC(moneyLine);
            home = getAnotherC(home);
        }
        Pattern p = Pattern.compile("([0-9]\\.?[0-9])([ou]?)([0-9]*)");
        Matcher m = p.matcher(strTot);
        if (!m.find()) {
            System.out.println(strTot);
            throw new Error();
        }
        total = Double.parseDouble(m.group(1));
        over = under = 1.9;
        if (m.group(2).length() > 0) {
            if (m.group(2).equals("o")) {
                over = getDecC(-(100 + Integer.parseInt(m.group(3))));
                under = getAnotherC(over);
            } else {
                under = getDecC(-(100 + Integer.parseInt(m.group(3))));
                over = getAnotherC(over);
            }
        }
    }

    double getDecC(int muricanC) {
        if (muricanC > 0) {
            return (muricanC + 100) / 100.0;
        }
        muricanC = Math.abs(muricanC);
        return (muricanC + 100.0) / muricanC;
    }

    double getAnotherC(double c) {
        return c / (c * 1.05 - 1);
    }
    
    double getProfit(double tot){
        if(tot >=  total){
            return over;
        }else{
            return -1;
        }
    }

}
