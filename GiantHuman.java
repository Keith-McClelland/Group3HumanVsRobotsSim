import greenfoot.*;
import java.util.List;
import java.util.ArrayList;

public class GiantHuman extends Human {

    private GreenfootImage idleImage;

    private GreenfootImage[] walkFrames;
    private GreenfootImage[] attackFrames;

    private int walkCounter = 0;
    private int walkSpeed = 7;      // slower for giant
    private int attackCounter = 0;
    private int attackSpeed = 12;   // slower attack for giant
    private int attackFrameIndex = 0;

    private boolean attacking = false;

    public GiantHuman(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, animType);
        loadAnimations();

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
            img.scale(img.getWidth() * 2, img.getHeight() * 2);
            walkFrames[i] = img;
        }

        // Giant Attack (1–6)
        attackFrames = new GreenfootImage[6];
        for (int i = 0; i < 4; i++) {
            GreenfootImage img = new GreenfootImage("GiantAttack" + (i + 1) + ".png");
            img.mirrorHorizontally();
            img.scale(img.getWidth() * 2, img.getHeight() * 2);
            attackFrames[i] = img;
        }

    } else {
        // Tank Walking (1–6)
        walkFrames = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            GreenfootImage img = new GreenfootImage("TankRun" + (i + 1) + ".png");
            img.mirrorHorizontally();
            img.scale(img.getWidth() * 2, img.getHeight() * 2);
            walkFrames[i] = img;
        }

        // Tank Attack (1–6)
        attackFrames = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            GreenfootImage img = new GreenfootImage("TankAttack" + (i + 1) + ".png");
            img.mirrorHorizontally();
            img.scale(img.getWidth() * 2, img.getHeight() * 2);
            attackFrames[i] = img;
        }
    }
}


    @Override
    public void act() {
        super.act();
        attackBehavior();
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Buildings target = getClosestEnemyBuilding();

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

    /** Move toward a target building */
    private void moveTowardBuilding(Buildings target) {
        if (target == null) return;

        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance == 0) return;

        setLocation(
            getX() + (int)(dx / distance * getSpeed()),
            getY() + (int)(dy / distance * getSpeed())
        );
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
