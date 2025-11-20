import greenfoot.*;
import java.util.ArrayList;

public class GiantHuman extends Human {

    private boolean attacking = false;

    public GiantHuman(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, "giant");
    }

    @Override
    protected void attackBehavior() {
        if (cooldown > 0) cooldown--;

        Buildings target = getClosestBuilding();
        if (target == null) {
            // No buildings → walk forward
            attacking = false;
            moveForward();
            return;
        }

        double distance = getDistanceTo(target);

        if (distance <= range) {
            attacking = true;
            attackBuilding(target);
        } else {
            attacking = false;
            moveTowardBuilding(target);
        }
    }

    /** Find closest Buildings object using ArrayList */
    private Buildings getClosestBuilding() {
        ArrayList<Buildings> buildings = (ArrayList<Buildings>) getObjectsInRange(1500, Buildings.class);

        Buildings closest = null;
        double minDist = Double.MAX_VALUE;

        for (Buildings b : buildings) {
            double dist = getDistanceTo(b);
            if (dist < minDist) {
                minDist = dist;
                closest = b;
            }
        }

        return closest;
    }

    /** Move toward a building */
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

    /** Giant attack using ONLY delay/cooldown */
    private void attackBuilding(Buildings target) {
        if (cooldown == 0) {
            target.takeDamage(damage);   // Buildings class must have takeDamage()
            cooldown = delay;
        }
    }
}
