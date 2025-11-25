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

        if (!isPendingRemoval()) {
            super.act();    // handles movement + health
            animateWalking();
        }
    }

    @Override
    protected void attackBehavior() {

        // Find closest human or building
        Human closestHuman = getClosestHuman();
        Buildings closestBuilding = getClosestEnemyBuilding();

        double humanDist = closestHuman != null ? getDistanceTo(closestHuman) : Double.MAX_VALUE;
        double buildingDist = closestBuilding != null ? getDistanceTo(closestBuilding) : Double.MAX_VALUE;

        // Decide target: whichever is closer
        if (humanDist < buildingDist && humanDist <= detectionRange) {
            rushToward(closestHuman, humanDist);
        } else if (buildingDist <= detectionRange) {
            rushToward(closestBuilding, buildingDist);
        }

        // Explode if within radius
        if (humanDist <= explosionRadius || buildingDist <= explosionRadius) {
            explode();
            markForRemoval();
        }
    }

    /** Rush toward target */
    private void rushToward(Actor target, double distance) {
        if (target == null || distance == 0) return;

        double dx = target.getX() - getX();
        double dy = target.getY() - getY();

        setLocation(
            getX() + (int)(dx / distance * rushSpeed),
            getY() + (int)(dy / distance * rushSpeed)
        );
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

        // Damage all humans and enemy buildings in radius
        List<Human> humans = getObjectsInRange(explosionRadius, Human.class);
        for (Human h : humans) {
            h.takeDamage(damage);
        }

        List<Buildings> buildings = getObjectsInRange(explosionRadius, Buildings.class);
        for (Buildings b : buildings) {
            if (!b.isRobotSide()) { // only damage enemy buildings
                b.takeDamage(damage);
            }
        }

        // Remove self after explosion
        getWorld().removeObject(this);
    }

    /** Find closest enemy building (isHumanSide == true) */
    private Buildings getClosestEnemyBuilding() {
        if (getWorld() == null) return null;

        List<Buildings> buildings = getWorld().getObjects(Buildings.class);
        Buildings closest = null;
        double minDist = Double.MAX_VALUE;

        for (Buildings b : buildings) {
            if (b.isRobotSide()) continue; // skip friendly buildings

            double dist = getDistanceTo(b);
            if (dist < minDist) {
                minDist = dist;
                closest = b;
            }
        }

        return closest;
    }
}
