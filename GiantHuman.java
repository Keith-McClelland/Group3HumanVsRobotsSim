import greenfoot.*;
import java.util.List;
import java.util.ArrayList;
/**
 * GiantHuman is a subclass of Human with larger size, slower speed, and animations. 
 * <p>
 * This class handles walking, attacking enemy buildings, and selecting targets based on proximity. 
 * It supports two animation types: "giant" and "tank". 
 *
 * @author Keith McClelland
 * @version November 2025 
 */
public class GiantHuman extends Human {

    private GreenfootImage idleImage;
    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 15;      // slower for giant
    private int attackCounter = 0;
    private int attackSpeed = 12;   // slower attack for giant
    private int attackFrameIndex = 0;

    private boolean attacking = false;

    // Attack sound pool
    private GreenfootSound[] attackSounds;
    private int attackSoundsIndex;
    
    /**
     * Constructs a new GiantHuman unit.
     *
     * @param health Health points of the giant human. 
     * @param speed Movement speed. 
     * @param range Attack range. 
     * @param damage Damage dealt per attack. 
     * @param delay Cooldown delay between attacks. 
     * @param value Resource value awarded when destroyed. 
     * @param animType Animation type ("giant" or "tank"). 
     */
    public GiantHuman(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, animType);
        loadAnimations();
        loadSounds();

        idleImage = walkFrames[0];
        setImage(idleImage);
    }

    /** Load animation sets based on animationType ("giant" or "tank") */   
    private void loadAnimations() {
        if (animationType.equalsIgnoreCase("giant")) {
            // Giant Walking (1–6)
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("GiantWalk" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 3, img.getHeight() * 3);
                walkFrames[i] = img;
            }

            // Giant Attack (1–4)
            attackFrames = new GreenfootImage[4];
            for (int i = 0; i < 4; i++) {
                GreenfootImage img = new GreenfootImage("GiantAttack" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 3, img.getHeight() * 3);
                attackFrames[i] = img;
            }

        } else { // tank
            // Tank Walking (1–6)
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("TankRun" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 3, img.getHeight() * 3);
                walkFrames[i] = img;
            }

            // Tank Attack (1–6)
            attackFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("TankAttack" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 3, img.getHeight() * 3);
                attackFrames[i] = img;
            }
        }
    }

    /** Load attack sounds pool */  
    private void loadSounds() {
        attackSoundsIndex = 0;
        attackSounds = new GreenfootSound[20];
        for (int i = 0; i < attackSounds.length; i++) {
            attackSounds[i] = new GreenfootSound("punch.mp3");
        }
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Does the giants attack behaviour. 
     */
    @Override
    public void act() {
        super.act();
        attackBehavior();
    }
    
    /**
     * Implements the attack behavior for the GiantHuman. 
     * <p>
     * - Moves toward enemy buildings. 
     * - Attacks when within range. 
     * - Updates walking and attack animations. 
     */
    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Buildings target = getClosestEnemyBuilding();

        // No target → idle + walk forward
        if (target == null) {
            attacking = false;
            setImage(idleImage);
            moveForward();
            animateWalk();
            return;
        }

        double dist = getDistanceTo(target);

        // IN RANGE → ATTACK
        if (dist <= range) {
            attacking = true;
            animateAttack(target);

            if (cooldown == 0) {
                target.takeDamage(damage);

                // Play attack sound
                attackSounds[attackSoundsIndex].stop();
                attackSounds[attackSoundsIndex].play();
                attackSoundsIndex = (attackSoundsIndex + 1) % attackSounds.length;

                cooldown = delay;
            }
        }

        // NOT IN RANGE → MOVE TOWARD + WALK ANIMATION
        else {
            attacking = false;
            moveTowardBuilding(target);
            animateWalk();
        }
    }

    /** Walking animation logic */  
    private void animateWalk() {
        int frame = (walkCounter / walkSpeed) % walkFrames.length;
        setImage(walkFrames[frame]);
        walkCounter++;
    }

    /** Attack animation logic */   
    private void animateAttack(Buildings target) {
        setImage(attackFrames[attackFrameIndex]);

        attackCounter++;
        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }

    /** Move toward a target building */    
    private void moveTowardBuilding(Buildings target) {
        if (target == null) return;

        double dx = target.getX() - getPreciseX();
        double dy = target.getY() - getPreciseY();
        double distance = Math.hypot(dx, dy);

        if (distance == 0) return;

        double stepX = dx / distance * speed;
        double stepY = dy / distance * speed;

        setLocation(getPreciseX() + stepX, getPreciseY() + stepY);
    }

    /** Find closest enemy building (isHumanSide == false) */   
    private Buildings getClosestEnemyBuilding() {
        ArrayList<Buildings> buildings =
            (ArrayList<Buildings>) getObjectsInRange(1500, Buildings.class);

        Buildings closest = null;
        double minDist = Double.MAX_VALUE;

        for (Buildings b : buildings) {
            if (b.isHumanSide()) continue;

            double dist = getDistanceTo(b);
            if (dist < minDist) {
                minDist = dist;
                closest = b;
            }
        }

        return closest;
    }
   
}