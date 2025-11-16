import greenfoot.*;
import java.util.List;

public class MeleeRobot extends Robot {

    private GreenfootImage robotImage;
    private int animationCounter = 0;
    private int animationSpeed = 5; // in case you add walking frames later
    private int cooldown = 0;

    public MeleeRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Load robot image
        robotImage = new GreenfootImage("robot.png");
        robotImage.scale(50, 50);
        setImage(robotImage);
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Human target = getClosestHuman();
        if (target == null) return;

        double distance = getDistanceTo(target);

        if (distance <= range) {
            // Attack human
            if (cooldown == 0) {
                target.takeDamage(damage);
                cooldown = delay;
            }
        } else {
            // Move toward the human
            moveToward(target);
        }
    }

    private void moveToward(Human target) {
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 0) {
            setLocation(
                getX() + (int)(dx / distance * getSpeed()),
                getY() + (int)(dy / distance * getSpeed())
            );
        }
    }

    @Override
    public void act() {
        if (getWorld() == null) return;
        if (getHealth() <= 0) {
            // Remove robot safely
            if (hpBar != null) getWorld().removeObject(hpBar);
            getWorld().removeObject(this);
            return;
        }

        attackBehavior();
    }

    @Override
    protected Human getClosestHuman() {
        if (getWorld() == null) return null;

        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double minDist = Double.MAX_VALUE;

        for (Human h : humans) {
            if (h.getHealth() <= 0) continue;
            double dist = getDistanceTo(h);
            if (dist < minDist) {
                minDist = dist;
                closest = h;
            }
        }
        return closest;
    }
}

