import greenfoot.*;
import greenfoot.Color;  

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int topEdge = 275;
    public static double robotSpawnMultiplier = 1;
    public static double humanSpawnMultiplier = 1;

    // Text display images
    private GreenfootImage humanCashImage;
    private GreenfootImage robotCashImage;

    // TOP positions for text (KEPT)
    private int humanCashX = 1065;
    private int humanCashY = 45;
    private int robotCashX = 30;
    private int robotCashY = 45;

    // Spawning variables
    private int frameCount = 0;
    private int spawnInterval = 200;
    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;

    public MyWorld() {    
        super(1240, 700, 1);

        setPaintOrder(
            SuperStatBar.class, 
            Builders.class,
            Fences.class,
            Robot.class
        );
        
        setBackground(background);

        Units.setHumanCash(0);
        Units.setRobotCash(0);
    }

    public void act() {
        updateCash(); // KEEP THIS (top display)

        humanSpawnTimer++;
        if (humanSpawnTimer >= spawnInterval) {
            humanSpawnTimer = 0;
            spawnHumans();
        }

        robotSpawnTimer++;
        if (robotSpawnTimer >= spawnInterval) {
            robotSpawnTimer = 0;
            spawnRobots();
        }

        robotSpawnMultiplier = 1;
        humanSpawnMultiplier = 1;
    }

    private void updateCash() {
        GreenfootImage frame = getBackground();
        frame.drawImage(background, 0, 0); // redraw background

        humanCashImage = new GreenfootImage(
            "Human Cash: " + Units.getHumanCash(), 
            24, Color.WHITE, new Color(0, 0, 0, 150)
        );

        robotCashImage = new GreenfootImage(
            "Robot Cash: " + Units.getRobotCash(),
            24, Color.WHITE, new Color(0, 0, 0, 150)
        );

        // Draw TOP LEFT and TOP RIGHT
        frame.drawImage(robotCashImage, robotCashX, robotCashY);
        frame.drawImage(humanCashImage, humanCashX, humanCashY);
    }

    private void spawnHumans() {
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

        MeleeHuman human = new MeleeHuman(100, 2, 40, 20, 50, 10);
        addObject(human, getWidth() - 50, y);
    }

    private void spawnRobots() {
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

        MeleeRobot robot = new MeleeRobot(80, 2, 40, 10, 50, 10);
        addObject(robot, 50, y);
    }
}
