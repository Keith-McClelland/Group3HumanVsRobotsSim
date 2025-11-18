import greenfoot.*;

public class RangedHuman extends Human {

    private long lastShotTime = 0;
    private long cooldownTime = 600; // ms between shots
    private double projectileSpeed = 6;

    private boolean stoppedToShoot = false;

    private GreenfootImage[] walkFrames = new GreenfootImage[5]; // 0-4 walking
    private GreenfootImage shootFrame; // 5 = shooting
    private int currentFrame = 0;
    private int frameDelay = 5; // ticks per frame
    private int frameCount = 0;

    public RangedHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Load walking frames
        for (int i = 0; i < 5; i++) {
            walkFrames[i] = new GreenfootImage("rangedHuman00" + i + ".png");
            walkFrames[i].mirrorHorizontally();
            walkFrames[i].scale(40,50);
        }

        shootFrame = new GreenfootImage("rangedHuman005.png");
        shootFrame.mirrorHorizontally();
        shootFrame.scale(40,50);

        setImage(walkFrames[0]);
    }

    @Override
    public void act() {
        if (getHealth() <= 0) {
            getWorld().removeObject(this);
            return;
        }

        Robot target = getClosestRobot();

        if (target != null && getDistanceTo(target) <= range) {
            // Stop and shoot
            stoppedToShoot = true;
            setImage(shootFrame);
            shootIfReady(target);
        } else {
            // Move left with walking animation
            stoppedToShoot = false;
            moveLeft();
            animateWalk();
        }
    }

    private void shootIfReady(Robot target) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldownTime) {
            shootAt(target);
            lastShotTime = now;
        }
    }

    private void shootAt(Robot target) {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, damage, Projectile.Owner.HUMAN);
        getWorld().addObject(shot, getX(), getY());
    }

    private void moveLeft() {
        setLocation(getX() - (int)getSpeed(), getY());
    }

    private void animateWalk() {
        frameCount++;
        if (frameCount % frameDelay == 0) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            setImage(walkFrames[currentFrame]);
        }
    }

    @Override
    protected void attackBehavior() {
        // Not used — shooting handled in act()
    }
}




