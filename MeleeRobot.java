import greenfoot.*;
import java.util.List;

public class MeleeRobot extends Robot {

    private GreenfootImage idleImage;
    private GreenfootImage[] walkingFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 5; // lower = faster animation

    private int attackCounter = 0;
    private int attackSpeed = 10; // lower = faster attack animation
    private int attackFrameIndex = 0;

    private int cooldown = 0;
    private boolean dying = false;

    public MeleeRobot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value);

        // --- Idle frame ---
        idleImage = new GreenfootImage("meleeRobot000.png");
        idleImage.scale(20,50);
        setImage(idleImage);

        // --- Walking frames 0–6 ---
        walkingFrames = new GreenfootImage[7];
        for (int i = 0; i < 7; i++) {
            walkingFrames[i] = new GreenfootImage("meleeRobot00" + i + ".png");
            walkingFrames[i].scale(20,50);
        }

        // --- Attack frames 0–2 ---
        attackFrames = new GreenfootImage[3];
        for (int i = 0; i < 3; i++) {
            attackFrames[i] = new GreenfootImage("robotAttack00" + i + ".png");
            attackFrames[i].scale(80,100);
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
        if (cooldown > 0) cooldown--;

        Human target = getClosestHuman();
        if (target == null) {
            // No target → idle
            setImage(idleImage);
            move(speed); // keep moving forward if no target
            animateWalk();
            return;
        }

        double dist = getDistanceTo(target);

        if (dist <= range) {
            // Attack animation
            animateAttack();
            if (cooldown == 0) {
                target.takeDamage(damage);
                cooldown = delay;
            }
        } else {
            // Move toward target + walking animation
            moveToward(target);
            animateWalk();
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
            setLocation(getX() + (int)(dx / distance * getSpeed()),
                        getY() + (int)(dy / distance * getSpeed()));
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


