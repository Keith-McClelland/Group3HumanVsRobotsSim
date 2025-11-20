import greenfoot.*;
import java.util.List;

public class RangedHuman extends Human {
    //images when the robot is standing still 
    private GreenfootImage idleImage;

    //array list for each type of animation (walking and attacking)
    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    //timer that counts the walking animation + interval timing to switch between images
    private int walkCounter = 0;
    private int walkSpeed = 5;

    //timer that counts the attacking animation + interval to switch between the images
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

        /** Load animation sets based on animationType */
    private void loadAnimations() {
        // ARCHER TYPE
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
    
        } else {  
            // GUNNER TYPE
    
            walkFrames = new GreenfootImage[6];
            attackFrames = new GreenfootImage[6];
    
            for (int i = 0; i < 6; i++) {
                GreenfootImage img = new GreenfootImage("Gunner" + (i + 1) + ".png");
                img.mirrorHorizontally();
                img.scale(60, 60);
                walkFrames[i] = img;
                attackFrames[i] = img;
            }
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
        int frame = (walkCounter / walkSpeed) % walkFrames.length;
        setImage(walkFrames[frame]);
        walkCounter++;

    }

    
    private void attack(Robot target) {
        setImage(attackFrames[attackFrameIndex]);
    
        if (cooldown == 0 && target != null) {
            //find the diffrence in position between the ranged human and robot
            //(find angle to shoot at)
            double dx = target.getX() - getX();
            double dy = target.getY() - getY();
            double angle = Math.toDegrees(Math.atan2(dy, dx));
        
            // spawn projectile
            Projectile p = new Projectile(10, angle, damage, Projectile.Owner.HUMAN);
            // if this is an archer, use arrow image
            if (animationType.equalsIgnoreCase("archer")) {
                p.setImage("Arrow.png");
            }
            // the projectile class deals with the collision
            getWorld().addObject(p, getX(), getY());
        
            cooldown = delay;
        }

    
        attackCounter++;
        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }
}
