import greenfoot.*;
import java.util.List;

public class MeleeHuman extends Human {
    private GreenfootSound attackSound;

    private GreenfootImage idleImage;

    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5;
    private int attackCounter = 0;
    private int attackSpeed = 10;
    private int attackFrameIndex = 0;

    private boolean attacking = false;

    public MeleeHuman(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, animType);
        loadAnimations();

        idleImage = walkFrames[0];
        setImage(idleImage);
    }

    /** Load animation sets based on animationType */
    private void loadAnimations() {
        if (animationType.equalsIgnoreCase("caveman")) {
            // Caveman Walking (1–6)
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("cavemanwalk" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 2, img.getHeight() * 2);
                walkFrames[i] = img;
            }
    
            attackFrames = new GreenfootImage[4];
            for (int i = 0; i < 4; i++) {
                GreenfootImage img = new GreenfootImage("CavemanAttack" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(img.getWidth() * 2, img.getHeight() * 2);
                attackFrames[i] = img;
            }
            attackSound = new GreenfootSound("sword.mp3");
    
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
            attackSound = new GreenfootSound("punch.mp3");
    
        }
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Robot target = getClosestRobot();

        if (target == null) {
            // Move forward if no target
            attacking = false;
            setImage(idleImage);
            moveForward();
            animateWalk();
            return;
        }

        double distance = getDistanceTo(target);

        if (distance <= range) {
            // Attack
            attacking = true;
            animateAttack(target);
        } else {
            // Walk toward enemy
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

    /** Attack animation logic */
    private void animateAttack(Robot target) {
        setImage(attackFrames[attackFrameIndex]);
    
        if (cooldown == 0) {
            target.takeDamage(damage);
    
            if (attackSound != null) {
                attackSound.play();
            }
    
            cooldown = delay;
        }
    
        attackCounter++;
    
        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }
}
