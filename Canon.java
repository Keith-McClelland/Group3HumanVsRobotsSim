import greenfoot.*;
import java.util.List;

public class Canon extends Buildings {

    // controls the shot fire rate
    private long lastShotTime = 0;
    private boolean shooting = false;
    private long cooldown = 4000; // ms

    private double projectileSpeed = 8;
    private int projectileDamage = 80;

    // animation
    private GreenfootImage idleImage;
    private GreenfootImage[] shootFrames;
    private int frameIndex = 0;
    private int frameCounter = 0;
    private int frameSpeed = 5;

    public Canon(boolean isHumanSide) {
        super(200, isHumanSide);

        // idle frame
        idleImage = new GreenfootImage("canon001.png");
        idleImage.scale(70, 80);
        setImage(idleImage);

        // shooting frames
        shootFrames = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            shootFrames[i] = new GreenfootImage("canon00" + (i + 1) + ".png");
            shootFrames[i].scale(70, 80);
        }
    }

    public void act() {
        if (!isHumanSide()) return;

        Robot target = getClosestRobot();

        if (shooting) {
            animate();
        } else if (target != null) {
            attemptShoot(target);
        }
    }

    private Robot getClosestRobot() {
        List<Robot> robots = getWorld().getObjects(Robot.class);
        Robot closest = null;
        double best = Double.MAX_VALUE;

        for (Robot r : robots) {
            if (r.getHealth() <= 0) continue;
            double d = Math.hypot(r.getX() - getX(), r.getY() - getY());
            if (d < best) {
                best = d;
                closest = r;
            }
        }
        return closest;
    }

    private void attemptShoot(Robot target) {
        long now = System.currentTimeMillis();

        if (now - lastShotTime >= cooldown) {
            shooting = true;
            frameIndex = 0;
            frameCounter = 0;

            fire(target);
            lastShotTime = now;
        }
    }

    private void fire(Robot target) {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.CANON);
        shot.setImage("canonBall.png");
        getWorld().addObject(shot, getX(), getY());
    }

    private void animate() {
        setImage(shootFrames[frameIndex]);
        frameCounter++;

        if (frameCounter >= frameSpeed) {
            frameCounter = 0;
            frameIndex++;

            if (frameIndex >= shootFrames.length) {
                shooting = false;
                setImage(idleImage);
            }
        }
    }
}

