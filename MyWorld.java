import greenfoot.*;
import java.util.List;

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int frameCount = 0;

    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;
    private int spawnInterval = 200;

    private int evolutionStage = 1; // starts with basic melee
    private int nextStageCash = 50; // initial threshold

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

        // Statboards
        humanStatboard = new StatBoard();
        robotStatboard = new StatBoard();
        addObject(humanStatboard, 105, 75);
        addObject(robotStatboard, getWidth() - 105, 75);

        // HUD
        addObject(new StatsHUD(true), 126, 96);
        addObject(new StatsHUD(false), 1157, 96);
        addObject(new TerritoryBar(), getWidth()/2, 30);

        // RESET STATS
        Units.setHumanCash(0);
        Units.setRobotCash(0);
        Human.setTotalHumansSpawned(0);
        Robot.setTotalRobotsSpawned(0);

        frameCount = 0;
    }

    public void act() {
        frameCount++;
        EndSimWorld.totalTimeElapsed = frameCount;

        humanSpawnTimer++;
        if (humanSpawnTimer >= spawnInterval) {
            humanSpawnTimer = 0;
            spawnHumans();
        }

        robotSpawnTimer++;
        int robotInterval = Math.max(60, spawnInterval - frameCount / 10);
        if (robotSpawnTimer >= robotInterval) {
            robotSpawnTimer = 0;
            spawnRobots();
        }

        // Check evolution stage based on human cash
        updateEvolutionStage();

        // Builder spawn logic
        if (Units.getHumanCash() >= 100 && !fenceExists() && !builderExists()) {
            spawnBuilder();
            Units.setHumanCash(Units.getHumanCash() - 100);
        }
    }

    private void updateEvolutionStage() {
        int humanCash = Units.getHumanCash();

        if (humanCash >= nextStageCash) {
            evolutionStage++;
            
            if (evolutionStage == 2) nextStageCash = 200;
            else if (evolutionStage == 3) nextStageCash = 500;
            else if (evolutionStage == 4) nextStageCash = 1000;
            else if (evolutionStage == 5) nextStageCash = 2000;
        }
    }

    private void spawnHumans() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);

        if (evolutionStage == 1) {
            addObject(new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman"), getWidth() - 50, y);
        }

        if (evolutionStage >= 2) {
            addObject(new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman"), getWidth() - 50, y);
            addObject(new RangedHuman(50, 2, 400, 50, 40, 10, "archer"), getWidth() - 50, y);
        }

        if (evolutionStage >= 3) {
            addObject(new MeleeHuman(200, 2.5, 50, 60, 50, 15, "modern"), getWidth() - 50, y);
            addObject(new RangedHuman(80, 2, 450, 60, 45, 12, "modern"), getWidth() - 50, y);
        }

        if (evolutionStage >= 4) {
            addObject(new GiantHuman(500, 1.5, 60, 75, 60, 25, "giant"), getWidth() - 50, y);
            addObject(new ExplodingRobot(300, 2, 100, 40, 20, 20), getWidth() - 50, y); // example
        }

        if (evolutionStage >= 5) {
            addObject(new Turret(false), 100, getHeight()/2 + 50);
            addObject(new Canon(true), getWidth() - 100, getHeight()/2 + 50);
        }
    }

    private void spawnRobots() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);

        if (evolutionStage == 1) {
            addObject(new MeleeRobot(220, 2, 45, 15, 35, 12), 50, y);
        }

        if (evolutionStage >= 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(220, 2, 45, 15, 35, 12), 50, y);
            else
                addObject(new RangedRobot(140, 2, 375, 20, 35, 12), 50, y);
        }

        if (evolutionStage >= 3) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(250, 2.5, 50, 20, 40, 15), 50, y);
            else
                addObject(new RangedRobot(180, 2, 400, 25, 40, 15), 50, y);
        }

        if (evolutionStage >= 4) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(300, 2.5, 60, 25, 45, 20), 50, y);
            else
                addObject(new ExplodingRobot(300, 2, 100, 40, 20, 20), 50, y);
        }
    }

    private void spawnBuilder() {
        addObject(new Builders(), getWidth() - 100, 175);
    }

    private boolean fenceExists() {
        return !getObjects(Fences.class).isEmpty();
    }

    private boolean builderExists() {
        return !getObjects(Builders.class).isEmpty();
    }
}


