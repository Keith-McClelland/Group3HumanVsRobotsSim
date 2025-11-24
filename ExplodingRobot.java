import greenfoot.*;
import java.util.List;

public class ExplodingRobot extends Robot {

    private int explosionRadius = 50;
    private int detectionRange;
    private double rushSpeed;

    // walking animation
    private GreenfootImage[] walkFrames;
    private int frameIndex = 0;
    private int frameDelay = 5;
    private int frameCounter = 0;

    public ExplodingRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        this.detectionRange = range * 2;
        this.rushSpeed = speed * 2.5;

        loadWalkingFrames();
        setImage(walkFrames[0]);
    }

    private void loadWalkingFrames() {
        walkFrames = new GreenfootImage[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = new GreenfootImage("ExplodingRobotWalking" + (i + 1) + ".png");
            walkFrames[i].scale(20, 50);
        }
    }

    @Override
    public void act() {
        if (getWorld() == null) return;

        // Only behave if not dying
        if (!isPendingRemoval()) {
            super.act();    // handles movement + health
            animateWalking();
        }
    }

    @Override
    protected void attackBehavior() {

        Human closest = getClosestHuman();
        if (closest != null) {

            double dist = getDistanceTo(closest);

            // explode on human
            if (dist <= explosionRadius) {
                explode();
                markForRemoval();
                return;
            }

            // rush if close enough
            if (dist <= detectionRange) {
                double dx = closest.getX() - getX();
                double dy = closest.getY() - getY();
                double length = Math.hypot(dx, dy);

                setLocation(
                    getX() + (int)(dx / length * rushSpeed),
                    getY() + (int)(dy / length * rushSpeed)
                );
            }
        }

        // fence explosion
        attackFence();
    }

    private void attackFence() {
        List<Fences> fences = getObjectsInRange(30, Fences.class);
        if (!fences.isEmpty()) {
            explode();
            markForRemoval();
        }
    }

    private void animateWalking() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            frameIndex = (frameIndex + 1) % walkFrames.length;
            setImage(walkFrames[frameIndex]);
        }
    }

    private void explode() {
        if (getWorld() == null) return;
    
        // Play explosion sound
        Greenfoot.playSound("explosion.mp3");
    
        // Visual explosion effect
        BombEffect explosion = new BombEffect(100);
        getWorld().addObject(explosion, getX(), getY());
    
        // Damage all humans in radius
        List<Human> humans = getObjectsInRange(explosionRadius, Human.class);
        for (Human h : humans) {
            h.takeDamage(damage);
            getWorld().removeObject(this);
        }
    }
}
