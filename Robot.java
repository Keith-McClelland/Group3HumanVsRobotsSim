import greenfoot.*;
import java.util.List;

public abstract class Robot extends Units {
    protected int cooldown = 0;
    protected SuperStatBar hpBar;
    private static int numRobots = 0;
    protected boolean pendingRemoval = false; // flag for safe removal

    protected Robot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, true);
        numRobots++;

        hpBar = new SuperStatBar(health, this);
    }

    @Override
    public void addedToWorld(World w) {
        if (hpBar != null) w.addObject(hpBar, getX(), getY() - 20);
    }

    @Override
    public void act() {
        if (getWorld() == null) return;

        if (getHealth() <= 0 && !pendingRemoval) {
            pendingRemoval = true; // mark for removal
        }

        // Update HP bar
        if (hpBar != null) hpBar.update(getHealth());

        // Only continue actions if not pending removal
        if (!pendingRemoval) {
            attackBehavior();
            moveTowardHuman();
        } else {
            die();
        }
    }

    protected abstract void attackBehavior();

    protected Human getClosestHuman() {
        List<Human> humans = getObjectsInRange(1000, Human.class);
        Human closest = null;
        double minDist = Double.MAX_VALUE;

        for (Human h : humans) {
            double dist = getDistanceTo(h);
            if (dist < minDist) {
                minDist = dist;
                closest = h;
            }
        }
        return closest;
    }

    protected void moveTowardHuman() {
        Human target = getClosestHuman();
        if (target != null) {
            double dx = target.getX() - getX();
            double dy = target.getY() - getY();
            double distance = Math.hypot(dx, dy);
            if (distance > range) {
                setLocation(getX() + (int)(dx / distance * getSpeed()),
                            getY() + (int)(dy / distance * getSpeed()));
            }
        }
    }

    protected void die() {
        if (hpBar != null && getWorld() != null) getWorld().removeObject(hpBar);
        if (getWorld() != null) getWorld().removeObject(this);
        numRobots--;
    }

    public static int getNumRobots() {
        return numRobots;
    }
}




