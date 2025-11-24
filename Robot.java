import greenfoot.*;
import java.util.ArrayList;

public abstract class Robot extends Units {

    protected int cooldown = 0;
    private boolean pendingRemoval = false;

    private static int numRobots = 0;
    public static int totalRobotsSpawned = 0;

    protected Robot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, true);
        numRobots++;
        totalRobotsSpawned++;
    }

    @Override
    public void act() {
        if (getWorld() == null) return;

        if (getHealth() <= 0) {
            markForRemoval();
        }

        if (!pendingRemoval) {
            attackBehavior();
            moveTowardHuman();
        } else {
            die();
        }

        checkEdges();
    }

    protected abstract void attackBehavior();

    protected void moveTowardHuman() {
        Human target = getClosestHuman();
        if (target != null) {
            moveToward(target, range);
        }
    }

    protected void markForRemoval() {
        //For exploding robot
        pendingRemoval = true;
    }

    protected void die() {
        removeHealthBar();
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
        numRobots--;
    }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(dmg);
        if (getHealth() <= 0) {
            markForRemoval();
        }
    }

    // getters and setters
    public static int getNumRobots() {
        return numRobots;
    }
    
        protected boolean isPendingRemoval() {
        return pendingRemoval;
    }

    public static void setTotalRobotsSpawned(int amount) {
        totalRobotsSpawned = amount;
    }
}
