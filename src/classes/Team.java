package classes;

import java.util.Scanner;

public class Team {
    final String name;
    
    Team(Scanner in){
        name = in.next();
    }
    public int hashCode(){
        return name.hashCode();
    }
    public boolean equals(Object o){
        Team t = (Team)o;
        return t.name.equals(name);
    }
    Team(String name){
        this.name = name;
    }
}
