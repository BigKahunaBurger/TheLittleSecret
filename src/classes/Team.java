package classes;

import java.util.Scanner;

public class Team {
    String name;

    Team(Scanner in) {
        String str = in.next();
        name = "";
        while (Character.isLetter(str.charAt(0))) {
            name += str;
            str = in.next();
        }

    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        Team t = (Team) o;
        return t.name.equals(name);
    }

    Team(String name) {
        this.name = name;
    }
}
