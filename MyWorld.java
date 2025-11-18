import greenfoot.*;
import greenfoot.Color;  
import java.util.List;

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int topEdge = 275;
    public static int frameCount = 0;

    private int spawnInterval = 200;
    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;
    

    // Keep original positions for statboards if needed
    private StatBoard humanStatboard;
    private StatBoard robotStatboard;

    public MyWorld() {    
        super(1240, 700, 1);
    
        setPaintOrder(
            StatsHUD.class,
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
    
        // HUD
        StatsHUD leftHUD = new StatsHUD(true);
        addObject(leftHUD, 126, 96);
    
        StatsHUD rightHUD = new StatsHUD(false);
        addObject(rightHUD, 1157, 96);
    
        TerritoryBar territoryBar = new TerritoryBar();
        addObject(territoryBar, getWidth()/2, 30);
    
        // RESET ALL STATS HERE
        Units.setHumanCash(0);
        Units.setRobotCash(0);
    
        Human.setTotalHumansSpawned(0);
        Robot.setTotalRobotsSpawned(0);
    
        // Reset living/dead counters
        Units.setHumansAlive(0);
        Units.setHumansDead(0);
        Units.setRobotsAlive(0);
        Units.setRobotsDead(0);
    
        frameCount = 0;
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

    public void act() {
        frameCount++;
        EndSimWorld.totalTimeElapsed = frameCount;
    
        // Spawn humans at fixed interval
        humanSpawnTimer++;
        if (humanSpawnTimer >= spawnInterval) {
            humanSpawnTimer = 0;
            spawnHumans();
        }
    
        // Spawn robots more frequently over time
        robotSpawnTimer++;
        int robotInterval = Math.max(60, 200 - frameCount / 10); // faster over time, min 60 frames
        if (robotSpawnTimer >= robotInterval) {
            robotSpawnTimer = 0;
            spawnRobots();
        }
    
        // Spawn builder if conditions met
        if (Units.getHumanCash() >= 100 && !fenceExists() && !builderExists()) {
            spawnBuilder();
            Units.setHumanCash(Units.getHumanCash() - 100);
        }
    }
    
    
    private void spawnHumans() {
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);
    
        int cash = Units.getHumanCash();
    
        // Probability curve for primitive units (caveman/archer) vs modern (swordsman/gunner)
        double primitiveProb = Math.max(0.1, 1.0 - cash * 0.02); // starts high, decreases as cash grows
        double roll = Greenfoot.getRandomNumber(100) / 100.0;     // 0.0–0.99
    
        if (roll < primitiveProb) {
            // Primitive unit
            if (Greenfoot.getRandomNumber(2) == 0) {
                MeleeHuman caveman = new MeleeHuman(100, 2, 40, 20, 40, 10, "caveman");
                addObject(caveman, getWidth() - 50, y);
            } else {
                RangedHuman archer = new RangedHuman(80, 2, 400, 40, 40, 10, "archer");
                addObject(archer, getWidth() - 50, y);
            }
        } else {
            // Modern unit
            if (Greenfoot.getRandomNumber(2) == 0) {
                MeleeHuman swordsman = new MeleeHuman(120, 2, 40, 25, 35, 15, "Swordsman");
                addObject(swordsman, getWidth() - 50, y);
            } else {
                RangedHuman gunner = new RangedHuman(90, 2, 450, 45, 35, 15, "Gunner");
                addObject(gunner, getWidth() - 50, y);
            }
        }
    }
    
    
    private void spawnRobots() {
        int numToSpawn = 1 + Greenfoot.getRandomNumber(2); // spawn 1–2 robots per tick
        for (int i = 0; i < numToSpawn; i++) {
            int minY = 175;
            int maxY = getHeight() - 1;
            int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);
    
            int robotCash = Units.getRobotCash();
    
            // Weighted probabilities for robot types
            double meleeProb = Math.max(0.2, 0.5 - robotCash * 0.002);
            double rangedProb = Math.min(0.4, 0.3 + robotCash * 0.0015);
            double explodingProb = 1.0 - meleeProb - rangedProb;
    
            double roll = Greenfoot.getRandomNumber(100) / 100.0;
    
            if (roll < meleeProb) {
                MeleeRobot robot = new MeleeRobot(220, 2, 45, 15, 35, 12); // buffed
                addObject(robot, 50, y);
            } else if (roll < meleeProb + rangedProb) {
                RangedRobot robot = new RangedRobot(140, 2, 375, 20, 35, 12); // buffed
                addObject(robot, 50, y);
            } else {
                ExplodingRobot robot = new ExplodingRobot(220, 2, 420, 30, 40, 18); // buffed
                addObject(robot, 50, y);
            }
        }
    }
}


