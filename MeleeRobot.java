import greenfoot.*;
import java.util.ArrayList;

public class MeleeRobot extends Robot {
    private int cooldown = 0;

    public MeleeRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);
    }

    public void act() {
        super.act();
        attackHumans();
        if (getHealth() <= 0) getWorld().removeObject(this);
    }
}
