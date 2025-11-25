import greenfoot.*;
import java.util.List;
/**
 * MeleeHuman is a subclass of Human. 
 * Type of Human that specializes in close-range combat. 
 * <p>
 * Supports multiple animation types(as the evolve they change): 
 * - "caveman": slower, larger, with sword attack sound. 
 * - "cyborg": faster, smaller, with punch attack sound. 
 * <p>
 * This class handles: 
 * - Walking animation 
 * - Attack animation 
 * - Damage application to enemy robots 
 * - Sound effects for attacks 
 *
 * @author Keith McClelland
 * @author Veznu Premathas
 * 
 * @version November 2025 
 */
public class MeleeHuman extends Human {
    private GreenfootSound[] attackSounds;
    private int attackSoundsIndex;

    private GreenfootImage idleImage;
    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5;
    private int attackCounter = 0;
    private int attackSpeed = 10;
    private int attackFrameIndex = 0;

    private boolean attacking = false;
    /**
     * Constructs a new MeleeHuman unit. 
     *
     * @param health Health points of the unit. 
     * @param speed Movement speed. 
     * @param range Attack range. 
     * @param damage Damage dealt per attack. 
     * @param delay Cooldown between attacks. 
     * @param value Resource value awarded when destroyed. 
     * @param animType Animation type (e.g., "caveman" or "cyborg"). 
     */
    public MeleeHuman(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, animType);
        loadAnimations();

        idleImage = walkFrames[0];
        setImage(idleImage);
    }

    /** Load animation sets based on animationType */   
    private void loadAnimations() {
        attackSoundsIndex = 0; 
        attackSounds = new GreenfootSound[20]; // keep pool at 20

        if (animationType.equalsIgnoreCase("caveman")) {
            // Caveman Walking (1–6)
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("cavemanwalk" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 2, img.getHeight() * 2);
                walkFrames[i] = img;
            }

            // Caveman Attack (1–4)
            attackFrames = new GreenfootImage[4];
            for (int i = 0; i < 4; i++) {
                GreenfootImage img = new GreenfootImage("CavemanAttack" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 2, img.getHeight() * 2);
                attackFrames[i] = img;
            }

            for (int i = 0; i < attackSounds.length; i++) {
                attackSounds[i] = new GreenfootSound("sword.mp3");
            }

        } else {
            // Cyborg Walking (1–6)
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("CyborgRun" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(40,50);
                walkFrames[i] = img;
            }

            // Cyborg Attack (1–6)
            attackFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("CyborgAttack" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(40, 45);
                attackFrames[i] = img;
            }

            for (int i = 0; i < attackSounds.length; i++) {
                attackSounds[i] = new GreenfootSound("punch.mp3");
            }
        }
    }
    /**
     * Implements the attack behavior for MeleeHuman. 
     */
    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Robot target = getClosestRobot();

        if (target == null) {
            attacking = false;
            setImage(idleImage);
            moveForward();
            animateWalk();
            return;
        }

        double distance = getDistanceTo(target);

        if (distance <= range) {
            attacking = true;
            animateAttack(target);
        } else {
            attacking = false;
            moveTowardRobot(target);
            animateWalk();
        }
    }

    /** Walking animation logic */  
    private void animateWalk() {
        int frame = (walkCounter / walkSpeed) % walkFrames.length;
        setImage(walkFrames[frame]);
        walkCounter++;
    }

    /** Attack animation logic with sound pool */   
    private void animateAttack(Robot target) {
        setImage(attackFrames[attackFrameIndex]);

        if (cooldown == 0) {
            target.takeDamage(damage);

            // Play attack sound from pool
            attackSounds[attackSoundsIndex].stop(); // stop if still playing
            attackSounds[attackSoundsIndex].play();
            attackSoundsIndex = (attackSoundsIndex + 1) % attackSounds.length;

            cooldown = delay;
        }

        attackCounter++;

        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }
}
