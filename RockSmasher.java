import greenfoot.*;
import java.util.ArrayList;

public class RockSmasher extends Human {
    private int cooldown = 0;
    
    private static final int DELAY = 60;

    public RockSmasher(int health, double speed, int range, int damage) {
        super(health, speed, range, damage);
    }

    public void act() {
        super.act();
        if (cooldown > 0) cooldown--;
        ArrayList<Robot> targets = new ArrayList<>(getObjectsInRange(range, Robot.class));
        if (!targets.isEmpty() && cooldown == 0) {
            for (Robot r : targets) r.takeDamage(damage);
            cooldown = DELAY;
        }
        if (getHealth() <= 0) getWorld().removeObject(this);
    }
}
