import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;  

public class MyWorld extends World {
    //se
    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int topEdge = 275;
    public static double robotSpawnMultiplier = 1;
    public static double humanSpawnMultiplier = 1;

    // Text display images
    private GreenfootImage humanCashImage;
    private GreenfootImage robotCashImage;

    // Positions for text
    private int humanCashX, humanCashY;
    private int robotCashX, robotCashY;
    
    // Spawning variables
    private int frameCount = 0;        // counts act() calls
    private int spawnInterval = 200;   // every 150 frames (~2.5 sec at 60fps)
    
    // Separate timers for balanced spawns
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


        // Set text positions
        humanCashX = getWidth() - 250;
        humanCashY = getHeight() - 40;
        robotCashX = 30;
        robotCashY = getHeight() - 40;
        
       // spawn(); // spawn initial Builder & Turret
    }

    public void act() {
        updateCash();
        frameCount++;
        
        // Spawn humans on left
        humanSpawnTimer++;
        if (humanSpawnTimer >= 200) {
            humanSpawnTimer = 0;
            spawnHumans();
        }

        //Spawn robots on right
        robotSpawnTimer++;
        if (robotSpawnTimer >= spawnInterval) {
            robotSpawnTimer = 0;
            spawnRobots();
        }

        robotSpawnMultiplier = 1;
        humanSpawnMultiplier = 1;

        // Set text positions (bottom corners)
        humanCashX = getWidth() - 175;
        humanCashY = 45;
        robotCashX = 30;
        robotCashY = 45;
    }


    private void updateCash() {
        GreenfootImage frame = getBackground();
        frame.drawImage(background, 0, 0); // redraw background
        humanCashImage = new GreenfootImage("Human Cash: " + Units.getHumanCash(), 24, Color.WHITE, new Color(0, 0, 0, 150));
        robotCashImage = new GreenfootImage("Robot Cash: " + Units.getRobotCash(), 24, Color.WHITE, new Color(0, 0, 0, 150));

        frame.drawImage(robotCashImage, robotCashX, robotCashY);
        frame.drawImage(humanCashImage, humanCashX, humanCashY);
        
        robotSpawnMultiplier += Units.getRobotCash() * 0.000001;
        humanSpawnMultiplier += Units.getHumanCash() * 0.000001;
    }

    
    private void spawn() {
        // Spawn builder
        int startX = getWidth();
        Builders builder = new Builders();
        addObject(builder, startX, topEdge - 100);
        
        // Spawn turret
        Turret turret = new Turret();
        int turretX = 100;
        int turretY = getHeight()/2 + 50;
        addObject(turret, turretX, turretY);
    }

    private void spawnHumans() {
    int minY = 175;
    int maxY = getHeight() - 1;
    int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

    int health = 100;
    double speed = 2;
    int range = 40;   // melee attack range
    int damage = 20;
    int delay = 50;
    int value = 10;

    MeleeHuman human= new MeleeHuman(health, speed, range, damage, delay, value);
    addObject(human, getWidth() - 50, y); // right side

}

private void spawnRobots() {
    int minY = 175;
    int maxY = getHeight() - 1;
    int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

    int health = 80;
    double speed = 2;
    int range = 40;   // melee attack range
    int damage = 10;
    int delay = 50;
    int value = 10;

    MeleeRobot robot = new MeleeRobot(health, speed, range, damage, delay, value);
    addObject(robot, 50, y); // left side
}


}


