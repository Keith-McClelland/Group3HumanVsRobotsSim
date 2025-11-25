import greenfoot.*;
import java.util.ArrayList;
/**
 * The Canon class is a subclass of Buildings. 
 * Human-side defensive/offensive building that automatically scans for nearby Robot units 
 * and fires cannonball projectiles using 
 * a cooldown-based firing system. 
 *
 * The Canon includes: 
 * - Automatic targeting of the closest Robot 
 * - Cooldown-limited firing (default 4000 ms) 
 * - 5-frame cannon shooting animation 
 * - High-speed, high-damage projectile 
 * - Overlapping sound pool for cannon audio 
 * <p>
 * The Canon inherits HP, destruction logic, and health bar behavior from 
 * Buildings. Its behavior is executed through completeTask(). 
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 * @version November 2025 
 */
public class Canon extends Buildings {
    // controls the shot fire rate
    private long lastShotTime = 0;
    private boolean shooting = false;
    private long cooldown = 4000; // ms

    private double projectileSpeed = 8;
    private int projectileDamage = 150;

    // animation
    private GreenfootImage idleImage;
    private GreenfootImage[] shootFrames;
    private int frameIndex = 0;
    private int frameCounter = 0;
    private int frameSpeed = 5;

    // cannon sound array
    private GreenfootSound[] cannonSounds;
    private int soundIndex = 0;
    private final int NUM_SOUNDS = 20; // number of sound copies for overlap
    
    /**
     * Constructs a Canon building with preset HP and assigns its side. 
     *
     * @param isHumanSide       true if the Canon belongs to the Human side, 
     *                          otherwise false if it belongs to the Robot side (which it doesn't). 
     */
    public Canon(boolean isHumanSide) 
    {
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

        // initialize sound array
        cannonSounds = new GreenfootSound[NUM_SOUNDS];
        for (int i = 0; i < NUM_SOUNDS; i++) {
            cannonSounds[i] = new GreenfootSound("cannon.mp3");
        }
    }
    
    /**
     * Performs the Canon's automated targeting and firing behavior. 
     * 
     * If not Human-owned, exits immediately. 
     * If currently firing, plays animation. 
     * Otherwise, finds closest Robot and attempts to fire. 
     */
    public void completeTask() 
    {
        if (!isHumanSide()) return;

        Robot target = getClosestRobot();

        if (shooting) 
        {
            animate();
        } 
        else if (target != null) 
        {
            attemptShoot(target);
        }
    }
    
    /**
     * Finds the nearest living Robot unit in the world. 
     *
     * @return the closest Robot, or null if none exist 
     */
    private Robot getClosestRobot() 
    {
        ArrayList<Robot> robots = new ArrayList<>( getWorld().getObjects(Robot.class) );
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
    
    /**
     * Attempts to fire at the target Robot. 
     * Fires only if the firing cooldown has fully passed. 
     *
     * @param target the Robot to shoot at 
     */
    private void attemptShoot(Robot target) 
    {
        long now = System.currentTimeMillis();

        if (now - lastShotTime >= cooldown) {
            shooting = true;
            frameIndex = 0;
            frameCounter = 0;

            fire(target);
            lastShotTime = now;
        }
    }
    
    /**
     * Fires a cannonball projectile toward the Robot. 
     * Also plays a cannon sound effect. 
     *
     * @param target the Robot to shoot at 
     */
    private void fire(Robot target) 
    {
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.HUMAN);
        shot.setImage("canonBall.png");
        getWorld().addObject(shot, getX(), getY());

        // play sound from pool
        cannonSounds[soundIndex].play();
        soundIndex = (soundIndex + 1) % NUM_SOUNDS;
    }
    
    /**
     * Plays the shooting animation frame-by-frame. 
     * Returns to the idle frame once the animation finishes. 
     */
    private void animate() 
    {
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

