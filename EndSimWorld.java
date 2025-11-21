import greenfoot.*;

public class EndSimWorld extends World {

    public static int totalTimeElapsed = 0;
    GreenfootImage humanWin = new GreenfootImage("humanWinBackground.png");
    GreenfootImage robotWin = new GreenfootImage("robotWinBackground.png");

    public EndSimWorld(String winner) {
        super(1240, 700, 1);

        if (winner.equals("Human")) {
            setBackground(humanWin);
        } else {
            setBackground(robotWin);
        }

        // Convert frames to seconds
        double seconds = totalTimeElapsed / 60.0;
        String roundedSeconds = String.format("%.2f", seconds);

        // Get stats from Units class
        int humanCash = Units.getHumanCash();
        int robotCash = Units.getRobotCash();
        int humansSpawned = Human.totalHumansSpawned;
        int robotsSpawned = Robot.totalRobotsSpawned;

        // Define boxes
        int leftBoxXStart = 120;
        int leftBoxXEnd = 490;
        int rightBoxXStart = 800;
        int rightBoxXEnd = 1120;

        int boxYStart = 150;  // moved up a bit
        int boxHeight = 330;

        // Center X positions
        int leftBoxCenterX = (leftBoxXStart + leftBoxXEnd) / 2;
        int rightBoxCenterX = (rightBoxXStart + rightBoxXEnd) / 2;

        // Left box: Simulation time
        showText("Simulation Time: " + roundedSeconds + " seconds", leftBoxCenterX, boxYStart + boxHeight / 2);

        // Right box: Human & Robot stats
        int humanLineSpacing = 40; // smaller spacing between lines
        int robotLineSpacing = 40;

        int startY = boxYStart + 30; // starting Y for right box text

        // Human stats
        showText("Human Stats", rightBoxCenterX, startY);
        showText("Humans Spawned: " + humansSpawned, rightBoxCenterX, startY + humanLineSpacing);
        showText("Human Cash Earned: $" + humanCash, rightBoxCenterX, startY + humanLineSpacing * 2);

        // Leave a space between Human and Robot stats
        int robotStartY = startY + humanLineSpacing * 3 + 20; // 20 px extra space

        showText("Robot Stats", rightBoxCenterX, robotStartY);
        showText("Robots Spawned: " + robotsSpawned, rightBoxCenterX, robotStartY + robotLineSpacing);
        showText("Robot Cash Earned: $" + robotCash, rightBoxCenterX, robotStartY + robotLineSpacing * 2);
    }
}


