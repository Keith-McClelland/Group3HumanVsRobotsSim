import greenfoot.*;

public class RangedRobot extends Robot {

    private long lastShotTime = 0;
    private long cooldownTime = 1200; // ms between shots
    private double projectileSpeed = 8; // can be faster than RangedHuman

    private boolean stoppedToShoot = false;

    private GreenfootImage[] walkFrames = new GreenfootImage[8]; // 0-7 walking
    private GreenfootImage shootFrame; // shooting image
    private int currentFrame = 0;
    private int frameDelay = 5; // ticks per frame
    private int frameCount = 0;

    public RangedRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Load walking frames
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = new GreenfootImage("rangedRobotWalk00" + i + ".png");
        }

        // Load shooting image
        shootFrame = new GreenfootImage("rangedRobotShooting.png");

        // Set initial image
        setImage(walkFrames[0]);
    }

    public void act() {
        if (getHealth() <= 0) {
            getWorld().removeObject(this);
            return;
        }

        Human target = getClosestHuman();

        if (target != null && getDistanceTo(target) <= range) {
            // stop and attack
            setImage(shootFrame);
            shootIfReady(target);
        } else if (target != null) {
            // move toward the target
            moveTowardHuman();
            animateWalk();
        } else {
            // no target, move normally
            move(speed); // right
            animateWalk();
        }

        updateHealthBar();
        checkEdges();
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

        Projectile shot = new Projectile(projectileSpeed, angle, damage, Projectile.Owner.ROBOT);
        getWorld().addObject(shot, getX(), getY());
    }

    private void animateWalk() {
        frameCount++;
        if (frameCount % frameDelay == 0) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            setImage(walkFrames[currentFrame]);
        }
    }

    protected void moveTowardRobot() {
        // Not used — robot moves toward humans
    }

    @Override
    protected void attackBehavior() {
        // Shooting handled in act()
    }
}




