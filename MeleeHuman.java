import greenfoot.*;
import java.util.ArrayList;

public class MeleeHuman extends Human {
    private int cooldown = 0;
    
    public MeleeHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);
    }

    public void act() {
        super.act();
        attackRobots();
        if (getHealth() <= 0) getWorld().removeObject(this);
    }
}
