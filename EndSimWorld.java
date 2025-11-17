import greenfoot.*;

public class EndSimWorld extends World {

    // Track time of the simulation (stored in MyWorld for accuracy)
    public static int totalTimeElapsed = 0; // in frames; MyWorld should update this each act


    public EndSimWorld(String winner) {
        super(1000, 600, 1);

        // Convert frames → seconds
        double seconds = totalTimeElapsed / 60.0;

        // Get stats from Units class
        int humanCash = Units.getHumanCash();
        int robotCash = Units.getRobotCash();
        int totalUnits = Units.numUnits;  // total units spawned
         
        // To track humans / robots separately add counters in Human & Robot classes
        int humansSpawned = Human.totalHumansSpawned;
        int robotsSpawned = Robot.totalRobotsSpawned;

        // UI Output
        showText("⚔ " + winner + " WIN! ⚔", getWidth()/2, 200);

        showText("Simulation Statistics:", getWidth()/2, 270);
        showText("Time Elapsed: " + seconds + " seconds", getWidth()/2, 310);

        showText("Humans Spawned: " + humansSpawned, getWidth()/2, 350);
        showText("Robots Spawned: " + robotsSpawned, getWidth()/2, 380);

        showText("Human Cash Earned: $" + humanCash, getWidth()/2, 430);
        showText("Robot Cash Earned: $" + robotCash, getWidth()/2, 460);
    }

    public void act() {
        // End screen does nothing
    }
}
