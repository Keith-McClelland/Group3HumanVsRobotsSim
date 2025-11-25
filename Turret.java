import greenfoot.*;
import java.util.List;

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

    // constructor takes team side
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
        }
    }

    // main method to control turret behavior
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

    // check cooldown and fire if ready
    private void shootIfReady(Human target) 
    {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= cooldown) 
        {
            fire(target);           // fire projectile
            lastShotTime = now;     // reset cooldown timer
        }
    }

    // creates and fires projectile toward target
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

    // find closest human to turret
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

    // calculate distance from turret to any actor
    private double getDistance(Actor a) 
    {
        return Math.hypot(a.getX() - getX(), a.getY() - getY());
    }
}


