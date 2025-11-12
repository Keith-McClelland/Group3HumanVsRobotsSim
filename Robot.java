import greenfoot.*;
import java.util.ArrayList;

public abstract class Robot extends Units {
    private static int numRobots = 0;

    protected Robot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, true);
        numRobots++;
    }

    public void act() {
        super.act();
    }

    public void attackHumans() {
        if (cooldown > 0) cooldown--;
        ArrayList<Human> targets = new ArrayList<>(getObjectsInRange(range, Human.class));

        if (!targets.isEmpty() && cooldown == 0) {
            for (Human h : targets) h.takeDamage(damage);
            cooldown = delay;
            speed = 0;
        } else if (targets.isEmpty()) {
            speed = originalSpeed;
        }
    }

    public static int getNumRobots() {
        return numRobots;
    }

    public void removedFromWorld(World world) {
        numRobots--;
    }
}
