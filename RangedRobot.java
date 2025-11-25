import greenfoot.*;
import java.util.ArrayList;
/**
 * RangedRobot is a type of Robot that attacks humans and fences from range. 
 * <p>
 * This class handles: 
 * - Moves toward targets (humans or fences) while animating. 
 * - Stops at a set distance to shoot projectiles. 
 * - Uses a cooldown between shots. 
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 * 
 * @version November 2025 
 */
public class RangedRobot extends Robot {

    private long lastShotTime = 0;
    private long cooldownTime = 1200; // ms between shots
    private double projectileSpeed = 8;

    private GreenfootImage[] walkFrames = new GreenfootImage[8];
    private GreenfootImage shootFrame;
    private int currentFrame = 0;
    private int frameDelay = 5;
    private int frameCount = 0;

    private int stopDistance = 150; 

    // Sound array for overlapping firing sounds
    private GreenfootSound[] laserSounds;
    private int soundIndex = 0;
    private final int NUM_SOUNDS = 3; // number of copies for overlap
    
    /**
     * Constructs a new RangedRobot. 
     *
     * @param health  Initial health points. 
     * @param speed   Movement speed. 
     * @param range   Attack range. 
     * @param damage  Damage per projectile. 
     * @param delay   Frames between attacks (unused here; handled with cooldownTime). 
     * @param value   Resource value granted when defeated. 
     */
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
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * 
     * Controls the RangedRobot's behavior: 
     * - Moves toward humans or fences. 
     * - Stops at the correct shooting distance. 
     * - Fires projectiles with cooldown. 
     * - Animates walking. 
     * - Handles death and fade-out. 
     */
    @Override
    public void act() {
        if (getHealth() <= 0) {
            if (getWorld() != null) getWorld().removeObject(this);
            return;
        }
        updateHealthBar();
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
        checkEdges();
    }
    
    /** Walking animation logic */  
    private void animateWalk() {
        frameCount++;
        if (frameCount % frameDelay == 0) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            setImage(walkFrames[currentFrame]);
        }
    }
    
    /**
     * Shoots at the given human if the cooldown has passed. 
     *
     * @param target The human to attack. 
     */
    private void shootIfReady(Human target) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldownTime) {
            shootAt(target);
            lastShotTime = now;
        }
    }
    
    /**
     * Creates a projectile aimed at the specified human. 
     *
     * @param target The human to shoot at. 
     */
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
    
    /**
     * Moves the robot toward the specified actor. 
     *
     * @param target The actor to move toward. 
     */
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

    /**
     * Defines the specific attack behaviour for this unit. 
     */
    @Override
    protected void attackBehavior() {
    }
}
