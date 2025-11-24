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

    @Override
    public void act() {
        if (getWorld() == null || getHealth() <= 0) return;
        updateHealthBar();
        attackBehavior();
        checkEdges();
    }

    protected abstract void attackBehavior();

    protected Robot getClosestRobot() {
        if (getWorld() == null) return null;
        return getClosest(getObjectsInRange(1000, Robot.class));
    }

    protected void moveTowardRobot(Robot target) {
        if (target != null) moveToward(target, range);
    }

    protected void moveForward() {
        setLocation(getPreciseX() - speed, getPreciseY());
    }

    public static void setTotalHumansSpawned(int amount) {
        totalHumansSpawned = amount;
    }

    // Utility: get closest actor
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

    // Utility: move toward a target unless in range
    protected void moveToward(Actor target, double stopRange) {
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
}
