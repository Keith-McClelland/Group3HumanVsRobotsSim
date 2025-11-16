import greenfoot.*;
import java.util.List;

public abstract class Human extends Units {
    protected int cooldown = 0;

    protected Human(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, false);
    }

    @Override
    public void act() {
        super.act();
        if (getHealth() <= 0) {
            getWorld().removeObject(this);
            return;
        }
        attackBehavior();
        moveTowardRobot();
    }

    protected abstract void attackBehavior();

    protected Robot getClosestRobot() {
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

    protected void moveTowardRobot() {
        Robot target = getClosestRobot();
        if (target != null) {
            double dx = target.getX() - getX();
            double dy = target.getY() - getY();
            double distance = Math.hypot(dx, dy);
            if (distance > range) {  // move only if not in attack range
                setLocation(getX() + (int)(dx / distance * getSpeed()), 
                            getY() + (int)(dy / distance * getSpeed()));
            }
        }
    }
}
