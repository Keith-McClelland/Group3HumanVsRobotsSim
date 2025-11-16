import greenfoot.*;
import java.util.List;

public class MeleeRobot extends Robot {

    private GreenfootImage robotImage;
    private int cooldown = 0;
    private boolean dying = false; // for fade out

    public MeleeRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        robotImage = new GreenfootImage("robot.png");
        robotImage.scale(50, 50);
        setImage(robotImage);
    }

    @Override
    public void act() {
        if (getWorld() == null) return;

        // Update health bar each frame
        updateHealthBar();

        // Handle death
        if (getHealth() <= 0 && !dying) {
            dying = true;
            removeHpBar(); // remove bar immediately
        }

        if (dying) {
            fadeOut();
            return;
        }

        attackBehavior();
    }

    private void fadeOut() {
        GreenfootImage img = getImage();
        int alpha = img.getTransparency() - 10; // fade speed
        if (alpha <= 0) {
            getWorld().removeObject(this);
        } else {
            img.setTransparency(alpha);
        }
    }

    private void removeHpBar() {
        if (healthBar != null && getWorld() != null) {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Human target = getClosestHuman();
        if (target == null) return;

        double distance = getDistanceTo(target);

        if (distance <= range) {
            if (cooldown == 0) {
                target.takeDamage(damage);
                cooldown = delay;
            }
        } else {
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

