import greenfoot.*;

public class RangedRobot extends Robot {

    public RangedRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);
    }

    public void act() {
        super.act();
    }

    private void spawnProjectile(Human target) {
        Projectile p = new Projectile(getSpeed());
        getWorld().addObject(p, getX(), getY());
    }

    private void moveForward() {
        move(getSpeed()); 
    }
}
