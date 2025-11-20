import greenfoot.*;
import java.util.List;

public class ExplodingRobot extends Robot {

    private int explosionRadius = 50;
    private int detectionRange;
    private double rushSpeed;
    private boolean rushing = false;

    // Walking animation
    private GreenfootImage[] walkFrames;
    private int frameIndex = 0;
    private int frameDelay = 5; // number of act() calls per frame
    private int frameCounter = 0;

    public ExplodingRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        this.detectionRange = range * 2;
        this.rushSpeed = 2.5 * speed;

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
        super.act();

        if (!pendingRemoval) {
            attackBehavior();
            animateWalking();
        } else {
            // safely remove after explosion
            if (getWorld() != null) getWorld().removeObject(this);
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

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Human closest = getClosestHuman();
        if (closest == null) return;

        double dist = getDistanceTo(closest);

        // Explode if human is within explosion radius
        if (dist <= explosionRadius) {
            explode();
            pendingRemoval = true;
            return;
        }

        // Move toward human if within detection range
        if (dist <= detectionRange) {
            double dx = closest.getX() - getX();
            double dy = closest.getY() - getY();
            double distance = Math.hypot(dx, dy);
            setLocation(getX() + (int)(dx / distance * getSpeed()),
                        getY() + (int)(dy / distance * getSpeed()));
        }
    }

    protected void attackFence(Fences fence) 
    {
        // Fence check (explode on contact)
List<Fences> fences = getObjectsInRange(30, Fences.class);
if (!fences.isEmpty()) {
    explode();
    return;
}

    }

    private void explode() {
        if (getWorld() == null) return;

        // Explosion effect
        BombEffect explosion = new BombEffect(100);
        getWorld().addObject(explosion, getX(), getY());

        // Damage all humans in explosion radius
        List<Human> humans = getObjectsInRange(explosionRadius, Human.class);
        for (Human h : humans) {
            h.takeDamage(damage);
        }
    }
}




