import greenfoot.*;
import java.util.List;
/**
 * The Turret class is a subclass of Buildings. 
 * Robot-side defensive/offensive building that automatically scans for nearby Human units 
 * and fires projectiles using 
 * a cooldown-based firing system. 
 *
 * The Turret includes: 
 * - Automatic targeting of the closest Human  
 * - Fires projectiles at a fixed cooldown interval 
 * - Laser sound effects 
 * 
 * <p>
 * The Turret inherits HP, destruction logic, and health bar behavior from 
 * Buildings. Its behavior is executed through completeTask(). 
 * 
 * @author Veznu Premathas
 * @author Keith McClelland
 * @version November 2025 
 */
public class Turret extends Buildings 
{
    private long lastShotTime = 0;          // tracks last time turret fired
    private long cooldown = 300;            // ms between turret shots
    private double projectileSpeed = 10;    // projectile movement speed
    private int projectileDamage = 30;      // damage dealt per shot
    private int range = 500;                // max distance turret can target

    private GreenfootSound[] attackSounds;  // array of sounds for overlapping shots
    private int soundIndex = 0;             // keeps track of current sound in array
    private final int NUM_SOUNDS = 20;      // number of sound copies for overlap

    private GreenfootImage turretImage;     // image representing turret
    
    /**
     * Constructs a Turret building with preset HP and assigns its team side. 
     *
     * @param isHumanSide       false if the Turret belongs to the Robot side, 
     *                          otherwise True if it belongs to the Human side (which it doesn't). 
     */
    public Turret(boolean isHumanSide) 
    {
        super(300, isHumanSide); // robot turret = false

        // load and scale turret image
        turretImage = new GreenfootImage("images/turret.png");
        turretImage.mirrorHorizontally();  // flip for correct orientation
        turretImage.scale(100, 200);
        setImage(turretImage);

        // initialize sound array for overlapping laser sounds
        attackSounds = new GreenfootSound[NUM_SOUNDS];
        for (int i = 0; i < NUM_SOUNDS; i++) {
            attackSounds[i] = new GreenfootSound("laser.mp3");
            attackSounds[i].setVolume(40);
        }
    }
    
    /**
     * Performs the turret’s automated targeting and firing behavior. 
     * 
     * <p>
     * The turret only attacks opposing units. If on the robot side, it 
     * finds the closest human within range and attempts to fire if the 
     * cooldown allows. 
     */
    public void completeTask() 
    {
        // turret only shoots enemies
        if (isRobotSide()) 
        {
            Human target = getClosestHuman();
            if (target != null && getDistance(target) <= range) 
            {
                shootIfReady(target); // fire if cooldown has passed
            }
        }
    }
    
    /**
     * Fires a projectile toward the specified target if the cooldown timer 
     * has expired. 
     *
     * @param target the human unit to attack 
     */ 
    private void shootIfReady(Human target) 
    {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldown) 
        {
            fire(target);           // fire projectile
            lastShotTime = now;     // reset cooldown timer
        }
    }
    
    /**
     * Creates and launches a projectile toward the specified human unit. 
     *
     * Calculates the angle to the target, playing a sound 
     * effect, and spawning a projectile. 
     *
     * @param target the human the turret will shoot at 
     */
    private void fire(Human target) 
    {
        Greenfoot.playSound("laser.mp3");     // quick visual feedback sound

        // calculate angle to target
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double angle = Math.toDegrees(Math.atan2(dy, dx));

        // play sound from pool to allow overlapping shots
        attackSounds[soundIndex].play();
        soundIndex = (soundIndex + 1) % NUM_SOUNDS;

        // create projectile and set image
        Projectile shot = new Projectile(projectileSpeed, angle, projectileDamage, Projectile.Owner.ROBOT);
        GreenfootImage ray = new GreenfootImage("ray.png");
        ray.scale(40, 6);          // make projectile look like a laser beam
        shot.setImage(ray);

        // add projectile to world slightly offset for visual alignment
        getWorld().addObject(shot, getX() + 40, getY() - 40);
    }
    
    /**
     * Finds the nearest living Human unit in the world. 
     *
     * @return the closest Human, or null if none exist 
     */
    private Human getClosestHuman() 
    {
        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double best = Double.MAX_VALUE;

        for (Human h : humans) 
        {
            if (h.getHealth() <= 0) continue; // ignore dead humans
            double d = getDistance(h);         // calculate distance
            if (d < best) 
            {
                best = d;
                closest = h;
            }
        }
        return closest;
    }
    
    /**
     * Calculates the distance from this turret to another actor. 
     *
     * @param a the actor to measure distance to 
     * @return the straight-line distance between the turret and actor 
     */
    private double getDistance(Actor a) 
    {
        return Math.hypot(a.getX() - getX(), a.getY() - getY());
    }
}

