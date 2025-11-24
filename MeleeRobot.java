import greenfoot.*;
import java.util.List;

public class MeleeRobot extends Robot {

    private GreenfootImage idleImage;
    private GreenfootImage[] walkingFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5;   // animation pacing

    private int attackCounter = 0;
    private int attackSpeed = 10;
    private int attackFrameIndex = 0;

    private int cooldown = 0;
    private boolean dying = false;

    public MeleeRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // Idle frame
        idleImage = new GreenfootImage("meleeRobot000.png");
        idleImage.scale(20, 50);
        setImage(idleImage);

        // Walking frames
        walkingFrames = new GreenfootImage[7];
        for (int i = 0; i < walkingFrames.length; i++) {
            walkingFrames[i] = new GreenfootImage("meleeRobot00" + i + ".png");
            walkingFrames[i].scale(20, 50);
        }

        // Attack frames
        attackFrames = new GreenfootImage[3];
        for (int i = 0; i < attackFrames.length; i++) {
            attackFrames[i] = new GreenfootImage("robotAttack00" + i + ".png");
            attackFrames[i].scale(80, 100);
        }
    }

    @Override
    public void act() {
        if (getWorld() == null) return;

        updateHealthBar();

        if (getHealth() <= 0 && !dying) {
            dying = true;
            removeHpBar();
        }

        if (dying) {
            fadeOut();
            return;
        }

        attackBehavior();
    }

    @Override
    protected void attackBehavior() {

        // Fence takes priority
        Fences fence = findFence();
        if (fence != null) {
            attackFence(fence);
            return;
        }

        if (cooldown > 0) cooldown--;

        Human target = getClosestHuman();

        // No target: idle & walk forward
        if (target == null) {
            setImage(idleImage);
            move(speed);
            animateWalk();
            return;
        }

        double dist = getDistanceTo(target);

        // In range: attack
        if (dist <= range) {
            animateAttack();

            if (cooldown == 0) {
                target.takeDamage(damage);
                cooldown = delay;
            }
        } 
        
        // Out of range: walk to target
        else {
            moveToward(target);
            animateWalk();
        }
    }

    /** Find fence within melee range */
    private Fences findFence() {
        List<Fences> fences = getObjectsInRange(40, Fences.class);
        if (!fences.isEmpty()) {
            return fences.get(0);
        }
        return null;
    }

    /** Attack a fence */
    protected void attackFence(Fences fence) {

        animateAttack();

        if (cooldown == 0) {
            Fences.damage(damage);
            cooldown = delay;
        }
    }

    private void animateWalk() {
        int frame = (walkCounter / walkSpeed) % walkingFrames.length;
        setImage(walkingFrames[frame]);
        walkCounter++;
    }

    private void animateAttack() {
        setImage(attackFrames[attackFrameIndex]);

        attackCounter++;
        if (attackCounter >= attackSpeed) {
            attackCounter = 0;
            attackFrameIndex = (attackFrameIndex + 1) % attackFrames.length;
        }
    }

    private void moveToward(Human target) {
        if (target == null) return;

        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 0) {
            double nx = dx / distance;
            double ny = dy / distance;
            setLocation(
                getX() + (int)(nx * getSpeed()),
                getY() + (int)(ny * getSpeed())
            );
        }
    }

    private void fadeOut() {
        GreenfootImage img = getImage();
        int alpha = img.getTransparency() - 10;

        if (alpha <= 0) {
            getWorld().removeObject(this);
        } else {
            img.setTransparency(alpha);
        }
    }

    private void removeHpBar() {
        if (healthBar != null && getWorld() != null) {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }

    @Override
    protected Human getClosestHuman() {
        if (getWorld() == null) return null;

        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double minDist = Double.MAX_VALUE;

        for (Human h : humans) {
            if (h.getHealth() <= 0) continue;

            double dist = getDistanceTo(h);

            if (dist < minDist) {
                minDist = dist;
                closest = h;
            }
        }
        return closest;
    }
}
