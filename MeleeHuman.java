import greenfoot.*;
import java.util.List;

public class MeleeHuman extends Human {

    private GreenfootImage idleImage;              
    private GreenfootImage[] walkingFrames; 
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5;

    private int attackCounter = 0;
    private int attackSpeed = 10;
    private int attackFrameIndex = 0;
    private boolean attacking = false;

    public MeleeHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Idle image
        idleImage = new GreenfootImage("meeleHuman000.png");
        idleImage.mirrorHorizontally();
        idleImage.scale(45, 55);
        setImage(idleImage);

        // Walking frames
        walkingFrames = new GreenfootImage[7];
        for (int i = 0; i < 7; i++) {
            walkingFrames[i] = new GreenfootImage("meeleHuman00" + i + ".png");
            walkingFrames[i].mirrorHorizontally();
            walkingFrames[i].scale(45, 55);
        }

        // Attack frames
        attackFrames = new GreenfootImage[3];
        for (int i = 0; i < 3; i++) {
            attackFrames[i] = new GreenfootImage("humanMeeleAttack00" + i + ".png");
            attackFrames[i].mirrorHorizontally();
            attackFrames[i].scale(60, 65);
        }
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Robot target = getClosestRobot();
        if (target == null) {
            // No target → move forward
            attacking = false;
            setImage(idleImage);
            moveForward();
            animateWalk();
            return;
        }

        double distance = getDistanceTo(target);

        if (distance <= range) {
            // Attack target
            attacking = true;
            animateAttack(target);
        } else {
            // Move toward target
            attacking = false;
            moveTowardRobot(target);
            animateWalk();
        }
    }

    private void animateWalk() {
        int frame = (walkCounter / walkSpeed) % walkingFrames.length;
        setImage(walkingFrames[frame]);
        walkCounter++;
    }

    private void animateAttack(Robot target) {
        setImage(attackFrames[attackFrameIndex]);

        if (cooldown == 0) {
            target.takeDamage(damage);
            cooldown = delay;
        }

        attackCounter++;
        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }
}


