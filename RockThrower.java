import greenfoot.*;
import java.util.ArrayList;

public class RockThrower extends Human {
    private int cooldown = 0;
    private static final int DELAY = 60;
    private boolean isAttacking = false;

    public RockThrower(int health, double speed, int range, int damage) {
        super(health, speed, range, damage);
    }

    public void act() {
        super.act();
    }

    private void throwRock(Robot target) {
        Projectile rock = new Projectile(getSpeed());
        getWorld().addObject(rock, getX(), getY());
    }

    private void moveForward() {
        move(getSpeed()); 
    }
}
