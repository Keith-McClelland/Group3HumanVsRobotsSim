import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Turret extends SuperSmoothMover
{
    private GreenfootImage turretImage;
    private long lastShotTime = 0;
    private long cooldownTime = 300; // Faster firing (ms)
    private double projectileSpeed = 10; // Faster projectiles
    private int projectileDamage = 20; // Example damage
    private int range = 500; // Longer range

    public Turret() {
        turretImage = new GreenfootImage("images/turret.png");
        turretImage.mirrorHorizontally();
        turretImage.scale(100, 200);
        setImage(turretImage);
    }

    public void act() {
        Human target = getClosestHuman();
        if (target != null) {
            double distance = getDistanceTo(target);
            if (distance <= range) {
                shootIfReady(target);
            }
        }
    }

    private void shootIfReady(Human target) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldownTime) {
            shootAt(target);
            lastShotTime = now;
        }
    }

    private void shootAt(Human target) {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.ROBOT);
        getWorld().addObject(shot, getX() + 40, getY() - 40);
    }

    private Human getClosestHuman() {
        if (getWorld() == null) return null;

        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double minDist = Double.MAX_VALUE;

        for (Human h : humans) {
            double dist = getDistanceTo(h);
            if (dist < minDist) {
                minDist = dist;
                closest = h;
            }
        }
        return closest;
    }

    private double getDistanceTo(Actor a) {
        double dx = getX() - a.getX();
        double dy = getY() - a.getY();
        return Math.hypot(dx, dy);
    }
}


