import greenfoot.*;
import java.util.List;

public class RangedRobot extends Robot {

    private long lastShotTime = 0;
    private long cooldownTime = 1200; // ms between shots
    private double projectileSpeed = 8;

    private GreenfootImage[] walkFrames = new GreenfootImage[8];
    private GreenfootImage shootFrame;
    private int currentFrame = 0;
    private int frameDelay = 5;
    private int frameCount = 0;

    private int stopDistance = 150; // stop 150 px away from fence

    // Sound array for overlapping firing sounds
    private GreenfootSound[] laserSounds;
    private int soundIndex = 0;
    private final int NUM_SOUNDS = 3; // number of copies for overlap

    public RangedRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Load walking frames
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = new GreenfootImage("rangedRobotWalk00" + i + ".png");
            walkFrames[i].scale(40,45);
        }

        // Load shooting frame
        shootFrame = new GreenfootImage("rangedRobotShooting.png");
        shootFrame.scale(40,45);

        setImage(walkFrames[0]);

        // Initialize sound array
        laserSounds = new GreenfootSound[NUM_SOUNDS];
        for (int i = 0; i < NUM_SOUNDS; i++) {
            laserSounds[i] = new GreenfootSound("laser.mp3");
        }
    }

    @Override
    public void act() {
        if (getHealth() <= 0) {
            if (getWorld() != null) getWorld().removeObject(this);
            return;
        }

        updateHealthBar();

        Fences fence = getClosestFence();
        if (fence != null) {
            double distToFence = getDistanceTo(fence);
            if (distToFence <= stopDistance) {
                attackFence(fence);
            } else {
                moveToward(fence);
                animateWalk();
            }
        } else {
            Human target = getClosestHuman();
            if (target != null) {
                double dist = getDistanceTo(target);
                if (dist <= range) {
                    setImage(shootFrame);
                    shootIfReady(target);
                } else {
                    moveToward(target);
                    animateWalk();
                }
            } else {
                move(speed);
                animateWalk();
            }
        }

        checkEdges();
    }

    private void animateWalk() {
        frameCount++;
        if (frameCount % frameDelay == 0) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            setImage(walkFrames[currentFrame]);
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
        // Play the next laser sound in the array
        laserSounds[soundIndex].play();
        soundIndex = (soundIndex + 1) % NUM_SOUNDS;

        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, damage, Projectile.Owner.ROBOT);

        // Set the projectile image
        GreenfootImage ray = new GreenfootImage("ray.png");
        ray.scale(40, 6);
        shot.setImage(ray);

        getWorld().addObject(shot, getX(), getY());
    }

    private void moveToward(Actor target) {
        if (target == null) return;
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);
        if (distance > 0) {
            setLocation(getX() + (int)(dx / distance * getSpeed()),
                        getY() + (int)(dy / distance * getSpeed()));
        }
    }

    private Fences getClosestFence() {
        if (getWorld() == null) return null;
        List<Fences> fences = getWorld().getObjects(Fences.class);
        Fences closest = null;
        double minDist = Double.MAX_VALUE;

        for (Fences f : fences) {
            double dist = getDistanceTo(f);
            if (dist < minDist) {
                minDist = dist;
                closest = f;
            }
        }
        return closest;
    }

    protected void attackFence(Fences fence) {
        setImage(shootFrame);
        long now = System.currentTimeMillis();
        if (fence != null && now - lastShotTime >= cooldownTime) {
            // Play laser sound when attacking fence
            laserSounds[soundIndex].play();
            soundIndex = (soundIndex + 1) % NUM_SOUNDS;

            Fences.damage(damage);
            lastShotTime = now;
        }
    }

    @Override
    protected void attackBehavior() {
        // Handled in act()
    }
}
