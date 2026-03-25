// Problem statement
// Develop inheritance-based Cricket Match system using command line arguments.

// Base class
class Cricket {

    String team1, team2;

    // Constructor
    Cricket(String t1, String t2) {
        team1 = t1;
        team2 = t2;
    }

    void displayTeams() {
        System.out.println("Team 1: " + team1);
        System.out.println("Team 2: " + team2);
    }
}

// Derived class
class Match extends Cricket {

    int runs1, runs2;

    // Constructor
    Match(String t1, String t2, int r1, int r2) {
        super(t1, t2);
        runs1 = r1;
        runs2 = r2;
    }

    void result() {
        System.out.println("Score of " + team1 + ": " + runs1);
        System.out.println("Score of " + team2 + ": " + runs2);

        if (runs1 > runs2) {
            System.out.println(team1 + " wins!");
        } else if (runs2 > runs1) {
            System.out.println(team2 + " wins!");
        } else {
            System.out.println("Match Draw!");
        }
    }
}

// Main class
public class Program5 {

    public static void main(String[] args) {

        // Command line arguments:
        // args[0] = team1
        // args[1] = team2
        // args[2] = runs1
        // args[3] = runs2

        if (args.length < 4) {
            System.out.println("Please provide: team1 team2 runs1 runs2");
            return;
        }

        String team1 = args[0];
        String team2 = args[1];
        int runs1 = Integer.parseInt(args[2]);
        int runs2 = Integer.parseInt(args[3]);

        Match obj = new Match(team1, team2, runs1, runs2);

        obj.displayTeams();
        obj.result();
    }
}