import greenfoot.*;
import java.util.List;

public abstract class Human extends Units {
    protected int cooldown = 0;
    protected String animationType;
    
    public static int totalHumansSpawned = 0;

    protected Human(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, false);
        totalHumansSpawned++;
        this.animationType = animType;
    }


    public void act() {
        if (getWorld() == null || getHealth() <= 0) return;
        updateHealthBar();
        attackBehavior();
        checkEdges();
    }

    protected abstract void attackBehavior();

    protected Robot getClosestRobot() {
        if (getWorld() == null) return null;

        List<Robot> robots = getObjectsInRange(1000, Robot.class);
        Robot closest = null;
        double minDist = Double.MAX_VALUE;

        for (Robot r : robots) {
            double dist = getDistanceTo(r);
            if (dist < minDist) {
                minDist = dist;
                closest = r;
            }
        }
        return closest;
    }

    protected void moveTowardRobot(Robot target) {
        if (target == null) return;

        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > range) { // move only if not in attack range
            setLocation(
                getX() + (int)(dx / distance * getSpeed()),
                getY() + (int)(dy / distance * getSpeed())
            );
        }
    }

    // If no target, humans can keep moving forward
    protected void moveForward() {
        setLocation(getX() + (int)-speed, getY());
    }
    public static void setTotalHumansSpawned(int amount) { totalHumansSpawned = amount; }
}





