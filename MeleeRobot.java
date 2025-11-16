import greenfoot.*;
import java.util.List;

public class MeleeRobot extends Robot {

    private GreenfootImage robotImage;

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

        // Get closest human
        Human target = getClosestHuman();
        if (target == null) return;

        double distance = getDistanceTo(target);

        // Attack if within range
        if (distance <= range) {
            if (cooldown == 0) {
                target.takeDamage(damage);
                cooldown = delay;
            }
        } else {
            // Move toward the human if out of range
            moveToward(target);
        }
    }

    // Move toward a human
    private void moveToward(Human target) {
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 0) {
            setLocation(getX() + (int)(dx / distance * getSpeed()),
                        getY() + (int)(dy / distance * getSpeed()));
        }
    }

    // Find closest human
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



