import greenfoot.*;
import java.util.List;
/**
 * ExplodingRobot is a subclass of Robot that rushes toward Humans and explodes 
 * when close, dealing area-of-effect damage. 
 * <p>
 * This class handles: 
 * - Walking animation 
 * - Detection and rush behavior 
 * - Explosion on contact with Humans or Fences 
 * - Damage in a radius around itself 
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 * 
 * @version November 2025 
 */
public class ExplodingRobot extends Robot {

    private int explosionRadius = 50;
    private GreenfootSound explosion = new GreenfootSound("explosion.mp3");
    private int detectionRange;
    private double rushSpeed;

    // walking animation
    private GreenfootImage[] walkFrames;
    private int frameIndex = 0;
    private int frameDelay = 5;
    private int frameCounter = 0;
    
    /**
     * Constructs a new ExplodingRobot. 
     *
     * @param health  Initial health points. 
     * @param speed   Base movement speed. 
     * @param range   Base attack range (also used to calculate detectionRange). 
     * @param damage  Damage dealt by the explosion. 
     * @param delay   Cooldown between attacks (unused here but required by superclass). 
     * @param value   Resource value granted to Humans when destroyed. 
     */
    public ExplodingRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        this.detectionRange = range * 2;
        this.rushSpeed = speed * 2.5;

        loadWalkingFrames();
        setImage(walkFrames[0]);
    }
    
    /** Loads walking animation frames */   
    private void loadWalkingFrames() {
        walkFrames = new GreenfootImage[8];
        for (int i = 0; i < 8; i++) {
            walkFrames[i] = new GreenfootImage("ExplodingRobotWalking" + (i + 1) + ".png");
            walkFrames[i].scale(20, 50);
        }
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Handles walking animation and calls superclass act method for movement/health. 
     */
    @Override
    public void act() {
        if (getWorld() == null) return;

        if (!isPendingRemoval()) {
            super.act();    // handles movement + health
            animateWalking();
        }
    }
    
    /**
     * Attack behavior for ExplodingRobot. 
     * <p>
     * - Explodes when within explosion radius of a Human 
     * - Rushes toward closest Human if within detection range 
     * - Explodes when touching a Fence 
     */
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
    
    /**
     * Animates walking frames 
     */
    private void animateWalking() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            frameIndex = (frameIndex + 1) % walkFrames.length;
            setImage(walkFrames[frameIndex]);
        }
    }
    
    /**
     * Explodes, dealing damage to Humans in radius and showing visual effect 
     */
    private void explode() {
        if (getWorld() == null) return;

        // Play explosion sound
        explosion.setVolume(90);
        explosion.play();

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