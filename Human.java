import greenfoot.*;
import java.util.ArrayList;

public abstract class Human extends Units {
    private static int numHumans = 0;

    protected Human(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, false);
        numHumans++;
    }

    public void act() {
        super.act();
    }

    public void attackRobots() {
        if (cooldown > 0) cooldown--;
        ArrayList<Robot> targets = new ArrayList<>(getObjectsInRange(range, Robot.class));

        if (!targets.isEmpty() && cooldown == 0) {
            for (Robot r : targets) r.takeDamage(damage);
            cooldown = delay;
            speed = 0;
        } else if (targets.isEmpty()) {
            speed = originalSpeed;
        }
    }

    public static int getNumHumans() {
        return numHumans;
    }

    public void removedFromWorld(World world) {
        numHumans--;
    }
}
