import greenfoot.*;  
import java.util.List;

public class Canon extends Buildings
{
    private long lastShotTime = 0;
    private long cooldown = 4000; // ms between shots
    private double projectileSpeed = 8;
    private int projectileDamage = 80;

    private GreenfootImage idleImage;
    private GreenfootImage[] shootFrames;

    private boolean shooting = false;
    private int shootFrameIndex = 0;
    private int shootFrameSpeed = 5; // acts per frame
    private int shootFrameCounter = 0;

    public Canon(boolean isHumanSide) {
        super(200, isHumanSide); // <-- team side now set

        // Idle image
        idleImage = new GreenfootImage("canon001.png");
        idleImage.scale(70,80);
        setImage(idleImage);
    
        // Shooting animation frames 002–006
        shootFrames = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            shootFrames[i] = new GreenfootImage("canon00" + (i+1) + ".png");
            shootFrames[i].scale(70,80);
        }
    }

    public void act() {
        Robot target = getClosestRobot();

        if (shooting) {
            animateShoot();
        } else if (target != null) {
            shootIfReady(target);
        }
    }

    private Robot getClosestRobot() {
        List<Robot> robots = getWorld().getObjects(Robot.class);
        Robot closest = null;
        double minDist = Double.MAX_VALUE;

        for (Robot r : robots) {
            if (r.getHealth() <= 0) continue;
            double dist = Math.hypot(r.getX() - getX(), r.getY() - getY());
            if (dist < minDist) {
                minDist = dist;
                closest = r;
            }
        }
        return closest;
    }

    private void shootIfReady(Robot target) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldown) {
            shooting = true;
            shootFrameIndex = 0;
            shootFrameCounter = 0;

            // Fire projectile at first frame
            shootAt(target);
            lastShotTime = now;
        }
    }

    private void shootAt(Robot target) {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.CANON);
        getWorld().addObject(shot, getX(), getY());
    }

    private void animateShoot() {
        setImage(shootFrames[shootFrameIndex]);
        shootFrameCounter++;
        if (shootFrameCounter >= shootFrameSpeed) {
            shootFrameCounter = 0;
            shootFrameIndex++;
            if (shootFrameIndex >= shootFrames.length) {
                shooting = false;
                setImage(idleImage);
            }
        }
    }
}
