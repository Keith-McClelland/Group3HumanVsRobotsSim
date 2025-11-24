import greenfoot.*;
import java.util.List;

public class Turret extends Buildings {

    private long lastShotTime = 0;
    private long cooldown = 300; // ms between shots
    private long cooldownTime = 240;
    private double projectileSpeed = 10;
    private int projectileDamage = 30;
    private int range = 500;

    private GreenfootImage turretImage;

    // constructor takes team side
    public Turret(boolean isHumanSide) {
        super(300, isHumanSide); // robot turret = false

        // load turret image
        turretImage = new GreenfootImage("images/turret.png");
        turretImage.mirrorHorizontally();
        turretImage.scale(100, 200);
        setImage(turretImage);
    }

    public void act() {
        // only shoot opposite team
        if (isRobotSide()) {
            Human target = getClosestHuman();
            if (target != null && getDistance(target) <= range) {
                shootIfReady(target);
            }
        }
    }

    private void shootIfReady(Human target) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldown) {
            fire(target);
            lastShotTime = now;
        }
    }
    
    private void fire(Human target) {
        Greenfoot.playSound("laser.mp3");
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));
    
        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.ROBOT);
        getWorld().addObject(shot, getX() + 40, getY() - 40);
    }


    private Human getClosestHuman() {
        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double best = Double.MAX_VALUE;

        for (Human h : humans) {
            if (h.getHealth() <= 0) continue; // skip dead humans
            double d = getDistance(h);
            if (d < best) {
                best = d;
                closest = h;
            }
        }
        return closest;
    }

    private double getDistance(Actor a) {
        return Math.hypot(a.getX() - getX(), a.getY() - getY());
    }
}


