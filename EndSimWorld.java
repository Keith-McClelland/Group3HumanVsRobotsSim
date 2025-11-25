import greenfoot.*;
/**
 * EndSimWorld is a Greenfoot World that displays the end results of the simulation. 
 * It shows the winning side, the total simulation time, and summary statistics 
 * for both Humans and Robots 
 * <p> 
 * It reads static data from Units, Robots, and Human classes to display final results 
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 * @version November 2025
 */
public class EndSimWorld extends World {

    public static int totalTimeElapsed = 0;
    private String winner ;
    GreenfootImage humanWin = new GreenfootImage("humanWinBackground.png");
    GreenfootImage robotWin = new GreenfootImage("robotWinBackground.png");
    
    
    GreenfootSound humanWinSound = new GreenfootSound("humanWin.mp3");
    GreenfootSound robotWinSound = new GreenfootSound("robotWin.mp3");
    
    
    /**
     * This simple constructor creates a new EndSimWorld instance and displays the winner and stats. 
     * @param winner The winner of the simulation. Should be "Human" or "Robot". 
     *               This determines which background image is shown. 
     */
    public EndSimWorld(String winner) {
        super(1240, 700, 1);
        this.winner = winner;
        
        humanWinSound.setVolume(70);
        robotWinSound.setVolume(70);
        
        if (winner.equals("Human")) {
            setBackground(humanWin);
            humanWinSound.playLoop();
        } else {
            setBackground(robotWin);
            robotWinSound.playLoop();
        }

        // Convert frames to seconds
        double seconds = totalTimeElapsed / 60.0;
        String roundedSeconds = String.format("%.2f", seconds);

        // Get stats from Units class
        int humanCash = Units.getHumanCash();
        int robotCash = Units.getRobotCash();
        int humansSpawned = Human.totalHumansSpawned;
        int robotsSpawned = Robot.totalRobotsSpawned;
        int robotsKilled = Units.robotsDead;
        int humansKilled = Units.humansDead;

        // Define boxes
        int leftBoxXStart = 120;
        int leftBoxXEnd = 490;
        int rightBoxXStart = 800;
        int rightBoxXEnd = 1120;

        int boxYStart = 150;  // moved up a bit
        int boxHeight = 330;

        // Center X positions
        int leftBoxCenterX = (leftBoxXStart + leftBoxXEnd) / 2 - 10;
        int rightBoxCenterX = (rightBoxXStart + rightBoxXEnd) / 2 -10;

        // Left box: Simulation time
        showText("Simulation Time: " + roundedSeconds + " seconds", leftBoxCenterX, boxYStart + boxHeight / 2);

        // Right box: Human & Robot stats
        int humanLineSpacing = 35; // smaller spacing between lines
        int robotLineSpacing = 35;

        int startY = boxYStart + 30; // starting Y for right box text

        // Human stats
        showText("Human Stats", rightBoxCenterX, startY);
        showText("Total Humans Spawned: " + humansSpawned, rightBoxCenterX, startY + humanLineSpacing);
        showText("Number of Robots Killed: " + robotsKilled, rightBoxCenterX, startY + humanLineSpacing * 2);
        showText("Human Cash Earned: $" + humanCash, rightBoxCenterX, startY + humanLineSpacing * 3);
        
        // Leave a space between Human and Robot stats
        int robotStartY = startY + humanLineSpacing * 3 + 80; // 50 px extra space

        showText("Robot Stats", rightBoxCenterX, robotStartY);
        showText("Total Robots Spawned: " + robotsSpawned, rightBoxCenterX, robotStartY + robotLineSpacing);
        showText("Number of Humans killed: " + humansKilled, rightBoxCenterX, robotStartY + robotLineSpacing*2);
        showText("Robot Cash Earned: $" + robotCash, rightBoxCenterX, robotStartY + robotLineSpacing * 3);
    }
    
    /**
     * Plays background music
     */
    public void playMusic()
    {
        if (winner.equals("Human")) {
            if (!humanWinSound.isPlaying()) 
            {
                humanWinSound.setVolume(30);
                humanWinSound.playLoop();
            }
        } else {
            if (!robotWinSound.isPlaying()) 
            {
                robotWinSound.setVolume(30);
                robotWinSound.playLoop();
            }
        }
                
    }
    
    /**
     * Starts playing the music
     */
    @Override
    public void started() {
        playMusic(); // resumes loop if not already playing
    }

    /**
     * Stops the music
     */
    @Override
    public void stopped()
    {
        humanWinSound.stop();
        robotWinSound.stop();
    }

}


