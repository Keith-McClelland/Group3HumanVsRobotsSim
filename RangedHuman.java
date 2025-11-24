import greenfoot.*;
import java.util.List;

public class RangedHuman extends Human {
    private GreenfootSound attackSound;
    
    private GreenfootImage idleImage;
    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5;

    private int attackCounter = 0;
    private int attackSpeed = 12;
    private int attackFrameIndex = 0;
    private boolean attacking;

    public RangedHuman(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, animType);
        loadAnimations();

        idleImage = new GreenfootImage("ArcherWalk1.png");
        setImage(idleImage);
    }

    /** Load animations */
    private void loadAnimations() {
        if (animationType != null && animationType.equalsIgnoreCase("archer")) {
            walkFrames = new GreenfootImage[4];
            attackFrames = new GreenfootImage[4];

            for (int i = 0; i < 4; i++) {
                GreenfootImage walkImg = new GreenfootImage("ArcherWalk" + (i + 1) + ".png");
                walkImg.mirrorHorizontally();
                walkImg.scale(walkImg.getWidth() * 2, walkImg.getHeight() * 2);
                walkFrames[i] = walkImg;

                GreenfootImage attackImg = new GreenfootImage("ArcherAttack" + (i + 1) + ".png");
                attackImg.mirrorHorizontally();
                attackImg.scale(attackImg.getWidth() * 2, attackImg.getHeight() * 2);
                attackFrames[i] = attackImg;
            }
            attackSound = new GreenfootSound("arrow.mp3");
        } else {
            // Gunner type
            walkFrames = new GreenfootImage[6];
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("Gunner" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(40, 45);
                walkFrames[i] = img;
            }
            attackFrames = null; // Gunner doesn’t animate while attacking
            attackSound = new GreenfootSound("gun.mp3");
        }
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Robot target = getClosestRobot();
        if (target == null) {
            attacking = false;
            moveForward();
            animateWalk();
            return;
        }

        double dist = getDistanceTo(target);

        if (dist <= range) {
            attacking = true;
            attack(target);
        } else {
            attacking = false;
            moveTowardRobot(target);
            animateWalk();
        }
    }

    /** Walking animation */
    private void animateWalk() {
        if (walkFrames != null && walkFrames.length > 0) {
            int frame = (walkCounter / walkSpeed) % walkFrames.length;
            setImage(walkFrames[frame]);
            walkCounter++;
        }
    }

    private void attack(Robot target) {
        if (animationType.equalsIgnoreCase("archer")) {
            // Archers animate while attacking
            attackCounter++;
            if (attackCounter >= attackSpeed) {
                attackCounter = 0;
                attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
            }
            setImage(attackFrames[attackFrameIndex]);
        } else {
            // Gunner stops moving while attacking
            setImage(walkFrames[5]);
        }
        if (attackSound != null) {
                attackSound.play();
            }


        if (cooldown == 0 && target != null) {
            double dx = target.getX() - getX();
            double dy = target.getY() - getY();
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            Projectile p = new Projectile(10, angle, damage, Projectile.Owner.HUMAN);
            if (animationType.equalsIgnoreCase("archer")) {
                p.setImage("Arrow.png");
            } else {
                p.setImage("laser.png");
            }
            getWorld().addObject(p, getX(), getY());
            cooldown = delay;
        }
    }
}
