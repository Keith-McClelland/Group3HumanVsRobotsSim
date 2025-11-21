import greenfoot.*;
import java.util.List;

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int frameCount = 0;

    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;

    private int evolutionStage = 1; // stage 1
    private int maxHumans = 10;
    private int maxRobots = 10;

    private int humanSpawnInterval = 120; // frames (~2s)
    private int robotSpawnInterval = 120;

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

        humanStatboard = new StatBoard();
        robotStatboard = new StatBoard();
        addObject(humanStatboard, 105, 75);
        addObject(robotStatboard, getWidth() - 105, 75);

        addObject(new StatsHUD(true), 126, 96);
        addObject(new StatsHUD(false), 1157, 96);
        addObject(new TerritoryBar(), getWidth() / 2, 30);

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
        if (humanSpawnTimer >= humanSpawnInterval) {
            humanSpawnTimer = 0;
            if (getObjects(Human.class).size() < maxHumans) spawnHumans();
        }

        robotSpawnTimer++;
        if (robotSpawnTimer >= robotSpawnInterval) {
            robotSpawnTimer = 0;
            if (getObjects(Robot.class).size() < maxRobots) spawnRobots();
        }

        updateEvolutionStage();
    }

    private void updateEvolutionStage() {
    // Human upgrades and cannon
    int humanCash = Units.getHumanCash();
    if (evolutionStage == 1 && humanCash >= 50) {
        Units.addHumanCash(-50); // spend cash
        evolutionStage = 2;
    } else if (evolutionStage == 2 && humanCash >= 100) {
        Units.addHumanCash(-100);
        evolutionStage = 3;
    } else if (evolutionStage == 3 && humanCash >= 150) {
        Units.addHumanCash(-150);
        evolutionStage = 4;
        // Spawn human canon
        if (getObjects(Canon.class).isEmpty()) { // only one canon
            addObject(new Canon(true), getWidth() - 100, getHeight() / 2);
        }
    }

    // Robot upgrades and turret
    int robotCash = Units.getRobotCash();
    if (evolutionStage == 1 && robotCash >= 50) {
        Units.addRobotCash(-50);
        evolutionStage = 2;
    } else if (evolutionStage == 2 && robotCash >= 100) {
        Units.addRobotCash(-100);
        evolutionStage = 3;
        // Spawn robot turret
        if (getObjects(Turret.class).isEmpty()) { // only one turret
            addObject(new Turret(false), 100, getHeight() / 2);
        }
    }
}


    private void spawnHumans() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);

        if (evolutionStage == 1) {
            addObject(new MeleeHuman(100, 1.8, 40, 30, 20, 10, "caveman"), getWidth() - 50, y);
        } else if (evolutionStage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(100, 1.8, 40, 30, 20, 10, "caveman"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(50, 1.5, 300, 25, 20, 12, "archer"), getWidth() - 50, y);
        } else if (evolutionStage >= 3) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(100, 2.0, 50, 40, 25, 15, "cyborg"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(50, 1.8, 350, 30, 25, 15, "gunner"), getWidth() - 50, y);
        }else if (evolutionStage >= 4) {
            int choice = Greenfoot.getRandomNumber(3); // 0,1,2
            if(choice == 0)
            {
                addObject(new MeleeHuman(100, 2.0, 50, 40, 25, 15, "cyborg"), getWidth() - 50, y);
            }
            else if (choice == 1)
            {
                addObject(new RangedHuman(50, 1.8, 350, 20, 25, 15, "gunner"), getWidth() - 50, y);
            }
            else
            {
                addObject(new GiantHuman(1000, 0.5, 100, 20, 30, 500, "giant"), getWidth() - 50, y);
            }
        }
    }

    private void spawnRobots() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);

        if (evolutionStage == 1) {
            addObject(new MeleeRobot(100, 1.8, 40, 30, 20, 12), 50, y);
        } else if (evolutionStage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(150, 1.8, 35, 40, 20, 12), 50, y);
            else
                addObject(new RangedRobot(100, 1.5, 300, 60, 15, 10), 50, y);
        } else if (evolutionStage >= 3) {
            int choice = Greenfoot.getRandomNumber(3); // 0,1,2
            if(choice == 0)
            {
                addObject(new MeleeRobot(180, 2.0, 45, 40, 25, 15), 50, y);
            }
            else if (choice == 1)
            {
                addObject(new RangedRobot(200, 1.5, 300, 70, 20, 20), 50, y);
            }
            else
            {
                addObject(new ExplodingRobot(500, 1.5, 50, 40, 20, 20), 50, y);
            }
    }
}
}

