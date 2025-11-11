import greenfoot.*;
import java.util.ArrayList;

public abstract class Human extends Units {
    private static int numHumans = 0;

    private int cooldown = 0;
    private int DELAY = 50; 

    protected Human(int health, double speed, int range, int damage) {
        super(health, speed, range, damage, false); 
        numHumans++;
    }


    public void act() {
        super.act();
    }

    //Attacks all Robots within range by dealing damage.
    public void attackRobots() {
        if (cooldown > 0) cooldown--;
        ArrayList<Robot> targets = new ArrayList<>(getObjectsInRange(range, Robot.class));
        if (!targets.isEmpty() && cooldown == 0) {
            for (Robot r : targets) r.takeDamage(damage);
            cooldown = DELAY;
        }
    }
     
    public static int getNumHumans() {
        return numHumans;
    }

    //Decrease human count when removed from the world.
    public void removedFromWorld(World world) {
        numHumans--;
    }
}
