import greenfoot.*;
import greenfoot.Color;  
import java.util.List;

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int topEdge = 275;

    private int spawnInterval = 200;
    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;

    // Keep original positions for statboards if needed
    private StatBoard humanStatboard;
    private StatBoard robotStatboard;

    public MyWorld() {    
        super(1240, 700, 1);

        setPaintOrder(
            StatsHUD.class,      // ensure HUD is on top
            SuperStatBar.class, 
            Builders.class,
            Fences.class,
            Robot.class
        );
        
        setBackground(background);

        // Add turrets/canons
        Turret turret = new Turret();
        addObject(turret, 100, getHeight()/2 + 50);
        
        Canon canon = new Canon();
        addObject(canon, getWidth() - 100, getHeight()/2 + 50);
        
        // Add statboards
        humanStatboard = new StatBoard();
        robotStatboard = new StatBoard();
        addObject(humanStatboard, 105, 75);
        addObject(robotStatboard, getWidth() - 105, 75);

        // Add HUD actors on top of everything
        StatsHUD leftHUD = new StatsHUD(true);   // Robots stats
        addObject(leftHUD, 126, 96);

        StatsHUD rightHUD = new StatsHUD(false); // Humans stats
        addObject(rightHUD, 1157, 96);

        Units.setHumanCash(0);
        Units.setRobotCash(0);
    }

    public void act() {
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

        // Optionally: spawn builder if you want
        // if (Units.getHumanCash() >= 100 && !fenceExists() && !builderExists()) {
        //     spawnBuilder();
        //     Units.setHumanCash(Units.getHumanCash() - 100);
        // }
    }

    private boolean fenceExists() {
        return !getObjects(Fences.class).isEmpty();
    }

    private boolean builderExists() {
        return !getObjects(Builders.class).isEmpty();
    }

    private void spawnBuilder() {
        int y = 175;
        Builders builder = new Builders();
        addObject(builder, getWidth() - 100, y);
    }

    private void spawnHumans() {
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

        int choice = Greenfoot.getRandomNumber(2);
        if (choice == 0) {
            MeleeHuman human = new MeleeHuman(100, 2, 40, 20, 40, 10);
            addObject(human, getWidth() - 50, y);
        } else {
            RangedHuman human = new RangedHuman(80, 2, 400, 40, 40, 10);
            addObject(human, getWidth() - 50, y);
        }
    }

    private void spawnRobots() {
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

        int choice = Greenfoot.getRandomNumber(2);
        if (choice == 0) {
            MeleeRobot robot = new MeleeRobot(250, 2, 40, 10, 40, 10);
            addObject(robot, 50, y);
        } else {
            RangedRobot robot = new RangedRobot(100, 2, 400, 15, 40, 10);
            addObject(robot, 50, y);
        }
    }
}


