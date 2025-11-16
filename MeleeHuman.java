import greenfoot.*; 
import java.util.List;

public class MeleeHuman extends Human {

    private GreenfootImage idleImage;              
    private GreenfootImage[] walkingFrames = new GreenfootImage[7]; 
    private GreenfootImage attackImage;
    private int animationCounter = 0;
    private int animationSpeed = 5; // acts per frame

    public MeleeHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Idle image (frame 0)
        idleImage = new GreenfootImage("meeleHuman000.png");
        idleImage.mirrorHorizontally();
        idleImage.scale(65,65);
        setImage(idleImage);

        // Walking frames 1–7
        for (int i = 1; i <= 7; i++) {
            walkingFrames[i-1] = new GreenfootImage("meeleHuman00" + i + ".png");
            walkingFrames[i-1].mirrorHorizontally();
            walkingFrames[i-1].scale(65,65);
        }

        // Attack frame
        attackImage = new GreenfootImage("meeleHuman006.png");
        attackImage.mirrorHorizontally();
        attackImage.scale(65,65);
    }

    @Override
    protected void attackBehavior() {
        // Not used, attack handled in act()
    }

    @Override
    public void act() {
        if (getWorld() == null || getHealth() <= 0) return;

        MeleeRobot target = getClosestMeleeRobot();
        if (target != null) {
            double distance = getDistanceTo(target);

            if (distance > range) {
                // Move toward robot
                moveToward(target);
                animateWalking();
            } else {
                // Attack when touching
                setImage(attackImage);
                attackTarget(target);
            }
        } else {
            // No target → idle
            setImage(idleImage);
        }
    }

    private void attackTarget(MeleeRobot target) {
        if (cooldown > 0) cooldown--;
        else {
            target.takeDamage(damage);
            cooldown = delay;
        }
    }

    private void animateWalking() {
        int frame = (animationCounter / animationSpeed) % walkingFrames.length;
        setImage(walkingFrames[frame]);
        animationCounter++;
    }

    private void moveToward(MeleeRobot target) {
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 0) {
            setLocation(
                getX() + (int)(dx / distance * getSpeed()),
                getY() + (int)(dy / distance * getSpeed())
            );
        }
    }

    private MeleeRobot getClosestMeleeRobot() {
        if (getWorld() == null) return null;

        List<MeleeRobot> robots = getWorld().getObjects(MeleeRobot.class);
        MeleeRobot closest = null;
        double minDist = Double.MAX_VALUE;

        for (MeleeRobot r : robots) {
            if (r.getHealth() <= 0) continue;
            double dist = getDistanceTo(r);
            if (dist < minDist) {
                minDist = dist;
                closest = r;
            }
        }
        return closest;
    }
}







