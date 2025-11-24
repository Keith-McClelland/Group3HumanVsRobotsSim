import greenfoot.*;
import java.util.List;

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
        if (getHealth() <= 0) pendingRemoval = true;

        if (!pendingRemoval) {
            attackBehavior();
            moveTowardHuman();
        } else {
            die();
        }

        checkEdges();
    }

    protected abstract void attackBehavior();

    protected Human getClosestHuman() {
        if (getWorld() == null) return null;
        return getClosest(getObjectsInRange(1000, Human.class));
    }

    protected void moveTowardHuman() {
        Human target = getClosestHuman();
        if (target != null) moveToward(target, range);
    }

    protected void die() {
        removeHealthBar();
        if (getWorld() != null) {
            getWorld().removeObject(this);
            numRobots--;
        }
    }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(dmg);
        if (getHealth() <= 0) pendingRemoval = true;
    }

    public static int getNumRobots() {
        return numRobots;
    }

    public static void setTotalRobotsSpawned(int amount) {
        totalRobotsSpawned = amount;
    }

    // Utility: return closest actor from list
    protected <T extends Actor> T getClosest(List<T> list) {
        T closest = null;
        double minDist = Double.MAX_VALUE;

        for (T obj : list) {
            double d = getDistanceTo(obj);
            if (d < minDist) {
                minDist = d;
                closest = obj;
            }
        }
        return closest;
    }

    // Utility: move toward actor unless already in range
    protected void moveToward(Actor target, double stopRange) {
        if (target == null) return;

        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > stopRange) {
            setLocation(
                getX() + (int)(dx / distance * getSpeed()),
                getY() + (int)(dy / distance * getSpeed())
            );
        }
    }
    protected boolean isPendingRemoval() {
        return pendingRemoval;
    }
    
    protected void markForRemoval() {
        pendingRemoval = true;
    }
}
