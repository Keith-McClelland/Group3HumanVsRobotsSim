import greenfoot.*;
import java.util.ArrayList;

public class ExplodingRobot extends Robot {
    private int explosionRadius = 100;
    private int detectionRange = 300;

    public ExplodingRobot(int health, double speed, int range, int damage) {
        super(health, speed, range, damage);
    }

    public void act() {
        if (getHealth() <= 0) {
            explode();
            return;
        }

        Human closest = getClosestHuman();
        if (closest != null) {
            double dist = getDistanceTo(closest);

            if (dist <= explosionRadius) {
                explode();
                return;
            }

            if (dist <= detectionRange) {
                turnTowards(closest);
            }
        }

        super.act();
    }

    private void explode() {
        attackHumans();
        getWorld().removeObject(this);
    }
}
