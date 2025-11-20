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
    private int evolutionStage = 0; // 0 = caveman only, 1 = caveman+archer, 2 = archer+swordsman, 3 = gunner+cyborg


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
        Turret turret = new Turret(false);
        addObject(turret, 100, getHeight()/2 + 50);
            
        Canon canon = new Canon(true);
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
        addObject(territoryBar, getWidth()/2, 30); // safe position

        addObject(territoryBar, getWidth()/2, 30);
    
        // RESET ALL STATS HERE

        Units.setHumanCash(0);
        Units.setRobotCash(0);
    
        Human.setTotalHumansSpawned(0);
        Robot.setTotalRobotsSpawned(0);
    

        
        frameCount++;
        EndSimWorld.totalTimeElapsed = frameCount;

       

        humanSpawnTimer++;
        if (humanSpawnTimer >= spawnInterval-50) {
            humanSpawnTimer = 0;
            spawnHumans();
        }

        robotSpawnTimer++;
        if (robotSpawnTimer >= spawnInterval) {
            robotSpawnTimer = 0;
            spawnRobots();
        }

        // Optionally: spawn builder if you want
        if (Units.getHumanCash() >= 100 && !fenceExists() && !builderExists()) {
            spawnBuilder();
            Units.setHumanCash(Units.getHumanCash() - 100);
         }

        // Reset living/dead counters
        Units.setHumansAlive(0);
        Units.setHumansDead(0);
        Units.setRobotsAlive(0);
        Units.setRobotsDead(0);
    
        frameCount = 0;
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
        int maxY = getHeight() - 5;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);
    
        // --- Evolution stage based on human cash ---
        int humanCash = Units.getHumanCash();
        if (humanCash >= 250) {
            evolutionStage = 3; // cyborg + tank + gunner
        } else if (humanCash >= 100) {
            evolutionStage = 2; // caveman + archer + giant
        } else if (humanCash >= 50) {
            evolutionStage = 1; // caveman + archer
        } else {
            evolutionStage = 0; // caveman only
        }
    
        Human newMelee;
        Human newRanged;
    
        switch (evolutionStage) {
            case 0: // caveman only
                newMelee = new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman");
                addObject(newMelee, getWidth() - 50, y);
                break;
    
            case 1: // caveman + archer
                newMelee = new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman");
                addObject(newMelee, getWidth() - 50, y); 
                newRanged = new RangedHuman(50, 2, 400, 50, 40, 10, "archer");
                addObject(newRanged, getWidth() - 50, y);
                break;
    
            case 2: // caveman + archer + giant
                int roll = Greenfoot.getRandomNumber(5);
                if (roll <= 4) {
                    newMelee = new MeleeHuman(150, 2, 40, 60, 40, 10, "caveman");
                    addObject(newMelee, getWidth() - 50, y);
                    newRanged = new RangedHuman(50, 2, 400, 60, 40, 10, "archer");
                    addObject(newRanged, getWidth() - 50, y);
                } else {
                    newMelee = new GiantHuman(300, 1.5, 50, 60, 50, 25, "giant");
                    addObject(newMelee, getWidth() - 50, y);
                }
                break;
    
            case 3: // cyborg + tank + gunner
                int roll3 = Greenfoot.getRandomNumber(3);
                if (roll3 == 0) {
                    newMelee = new MeleeHuman(300, 2.5, 50, 75, 40, 20, "cyborg");
                    addObject(newMelee, getWidth() - 50, y);
                } else if (roll3 == 1) {
                    newMelee = new GiantHuman(800, 1.2, 60, 70, 60, 40, "tank");
                    addObject(newMelee, getWidth() - 50, y);
                } else {
                    newRanged = new RangedHuman(400, 2, 450, 75, 35, 15, "Gunner");
                    addObject(newRanged, getWidth() - 50, y);
                }
                break;
        }
    }

    private void spawnRobots() {
        int robotCash = Units.getRobotCash();
    
        // Determine evolution stage based on robot cash
        int robotEvolutionStage;
        if (robotCash >= 150) {
            robotEvolutionStage = 3; // Melee + Ranged + Exploding
        } else if (robotCash >= 100) {
            robotEvolutionStage = 2; // Melee + Ranged
        } else {
            robotEvolutionStage = 1; // Melee only
        }
        int minY = 175;
        int maxY = getHeight() - 1;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY + 1);

        double roll = Greenfoot.getRandomNumber(100) / 100.0;

        switch (robotEvolutionStage) {
            case 1: // Melee only
                MeleeRobot meleeRobot = new MeleeRobot(220, 2, 45, 15, 35, 12); // buffed
                addObject(meleeRobot, 50, y);
                break;

            case 2: // Melee + Ranged
                if (roll < 0.5) {
                    MeleeRobot meleeRobot2 = new MeleeRobot(220, 2, 45, 15, 35, 12);
                    addObject(meleeRobot2, 50, y);
                } else {
                    RangedRobot rangedRobot2 = new RangedRobot(140, 2, 375, 20, 35, 12);
                    addObject(rangedRobot2, 50, y);
                }
                break;

            case 3: // Melee + Ranged + Exploding
                if (roll < 0.33) {
                    MeleeRobot meleeRobot3 = new MeleeRobot(220, 2, 45, 15, 35, 12);
                    addObject(meleeRobot3, 50, y);
                } else if (roll < 0.66) {
                    RangedRobot rangedRobot3 = new RangedRobot(140, 2, 375, 20, 35, 12);
                    addObject(rangedRobot3, 50, y);
                } else {
                    ExplodingRobot explodingRobot = new ExplodingRobot(220, 2, 420, 30, 40, 18);
                    addObject(explodingRobot, 50, y);
                }
                break;
        }
    }

    private boolean fenceExists() {
        return !getObjects(Fences.class).isEmpty();
    }

    private boolean builderExists() {
        return !getObjects(Builders.class).isEmpty();
    }
}


