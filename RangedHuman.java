import greenfoot.*;
import java.util.ArrayList;

public class RangedHuman extends Human {

    public RangedHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);
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
