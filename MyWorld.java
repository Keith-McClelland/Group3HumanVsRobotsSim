import greenfoot.*;
import java.util.List;

public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int frameCount = 0;

    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;
    private int spawnInterval = 200;

    private int evolutionStage = 0; 
    private int nextStageCash = 100;

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
        addObject(new TerritoryBar(), getWidth()/2, 30);

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

        updateEvolutionStage();

        if (Units.getHumanCash() >= 100 && !fenceExists() && !builderExists()) {
            spawnBuilder();
            Units.setHumanCash(Units.getHumanCash() - 100);
        }
    }

    private void updateEvolutionStage() {
        int cash = Units.getHumanCash();

        if (cash >= nextStageCash) {
            evolutionStage++;
            if (evolutionStage == 2) nextStageCash = 200;
            else if (evolutionStage == 3) nextStageCash = 500;
            else if (evolutionStage == 4) nextStageCash = 1000;
            else if (evolutionStage == 5) nextStageCash = 2000;
        }
    }


    // ======================================================================================
    // HUMAN SPAWNING — IF STATEMENTS ONLY (NO SWITCH)
    // ======================================================================================
    private void spawnHumans() {
        int minY = 175;
        int maxY = getHeight() - 5;

        int y = minY + Greenfoot.getRandomNumber(maxY - minY);

        // base units for stage 0–3
        Human melee;
        Human ranged;

        // ============================================================
        // STAGE 0 — Caveman Only
        // ============================================================
        if (evolutionStage == 0) {
            melee = new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman");
            addObject(melee, getWidth() - 50, y);
            return;
        }

        // ============================================================
        // STAGE 1 — Caveman + Archer
        // ============================================================
        if (evolutionStage == 1) {
            melee = new MeleeHuman(150, 2, 40, 50, 40, 10, "caveman");
            addObject(melee, getWidth() - 50, y);

            y = minY + Greenfoot.getRandomNumber(maxY - minY);
            ranged = new RangedHuman(50, 2, 400, 50, 40, 10, "archer");
            addObject(ranged, getWidth() - 50, y);
            return;
        }

        // ============================================================
        // STAGE 2 — Caveman + Archer + Giant
        // ============================================================
        if (evolutionStage == 2) {
            int roll = Greenfoot.getRandomNumber(5);

            melee = new MeleeHuman(150, 2, 40, 60, 40, 10, "caveman");
            addObject(melee, getWidth() - 50, y);

            y = minY + Greenfoot.getRandomNumber(maxY - minY);
            ranged = new RangedHuman(50, 2, 400, 60, 40, 10, "archer");
            addObject(ranged, getWidth() - 50, y);

            if (roll >= 3) {
                y = minY + Greenfoot.getRandomNumber(maxY - minY);
                Human giant = new GiantHuman(300, 1.1, 50, 60, 50, 25, "giant");
                addObject(giant, getWidth() - 50, y);
            }

            return;
        }

        // ============================================================
        // STAGE 3 — Cyborg + Gunner + Tank
        // ============================================================
        if (evolutionStage >= 3) {

            int roll = Greenfoot.getRandomNumber(5);

            melee = new MeleeHuman(225, 2.5, 50, 75, 40, 20, "cyborg");
            addObject(melee, getWidth() - 50, y);

            y = minY + Greenfoot.getRandomNumber(maxY - minY);
            ranged = new RangedHuman(225, 2, 450, 75, 35, 15, "Gunner");
            addObject(ranged, getWidth() - 50, y);

            if (roll >= 3) {
                y = minY + Greenfoot.getRandomNumber(maxY - minY);
                Human tank = new GiantHuman(550, 1.1, 60, 70, 60, 40, "tank");
                addObject(tank, getWidth() - 50, y);
            }
        }
    }


    // ======================================================================================
    // ROBOT SPAWNING
    // ======================================================================================
    private void spawnRobots() {
        int minY = 175;
        int maxY = getHeight() - 5;
        int y = minY + Greenfoot.getRandomNumber(maxY - minY);

        int cash = Units.getRobotCash();
        int stage = 1;

        if (cash >= 200) stage = 3;
        else if (cash >= 100) stage = 2;

        // STAGE 1 — melee only
        if (stage == 1) {
            addObject(new MeleeRobot(220, 2, 45, 15, 35, 12), 50, y);
            return;
        }

        // STAGE 2 — melee + ranged
        if (stage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(220, 2, 45, 15, 35, 12), 50, y);
            else
                addObject(new RangedRobot(140, 2, 375, 20, 35, 12), 50, y);
            return;
        }

        // STAGE 3 — melee + ranged + explosive
        if (stage == 3) {

            int roll = Greenfoot.getRandomNumber(3);

            if (roll == 0)
                addObject(new MeleeRobot(300, 2.5, 60, 25, 45, 20), 50, y);
            else if (roll == 1)
                addObject(new RangedRobot(180, 2, 400, 25, 40, 15), 50, y);
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
