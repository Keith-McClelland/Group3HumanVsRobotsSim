import greenfoot.*;
import java.util.ArrayList;

public abstract class Human extends Units {

    protected int cooldown = 0;
    protected String animationType;

    public static int totalHumansSpawned = 0;

    protected Human(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, false);
        totalHumansSpawned++;
        this.animationType = animType;
    }

    public void act() {
        super.act();
        attackBehavior();
    }

    protected abstract void attackBehavior();

    protected void moveForward() {
        setLocation(getPreciseX() - speed, getPreciseY());
    }


    protected void moveTowardRobot(Robot target) {
        if (target != null) moveToward(target, range);
    }
        public static void setTotalHumansSpawned(int amount) {
        totalHumansSpawned = amount;
    }
}
