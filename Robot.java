import greenfoot.*;
import java.util.ArrayList;

public abstract class Robot extends Units {
    private static int numRobots = 0;
    private int cooldown;
    private int DELAY;

    protected Robot(int health, double speed, int range, int damage) {
        super(health, speed, range, damage, true);
        numRobots++;
    }

    public void act() {
        super.act(); 
    }
    
    protected Human getClosestHuman() {
        ArrayList<Human> humans = new ArrayList<>(getWorld().getObjects(Human.class));
        Human closest = null;
        double minDist = Double.MAX_VALUE;
        for (Human h : humans) {
            double d = getDistanceTo(h);
            if (d < minDist) {
                minDist = d;
                closest = h;
            }
        }
        return closest;
    }
    
    public void attackHumans() {
        if (cooldown > 0) cooldown--;
        ArrayList<Human> targets = new ArrayList<>(getObjectsInRange(range, Human.class));
        if (!targets.isEmpty() && cooldown == 0) {
            for (Human h : targets) h.takeDamage(damage);
            cooldown = DELAY;
        }
    }

    public static int getNumRobots() {
        return numRobots;
    }

    public void removedFromWorld(World world) {
        numRobots--;
    }
}
