import greenfoot.*;
import java.util.ArrayList;
/**
 * Abstract subclass for human units in the simulation. 
 * <p>
 * Extends the Units class and provides human-specific behavior. 
 * Humans move forward, attack robots, and maintain their own cooldowns and animations. 
 * @author Keith McClelland
 * @version November 2025 
 */

public abstract class Human extends Units {

    protected int cooldown = 0;
    protected String animationType;

    public static int totalHumansSpawned = 0;
    /**
     * Constructs a new Human unit. 
     *
     * @param health Health points of the human. 
     * @param speed Movement speed of the human. 
     * @param range Attack range of the human. 
     * @param damage Damage dealt per attack. 
     * @param delay Delay between actions. 
     * @param value Resource value awarded when destroyed. 
     * @param animType Type of animation for the human unit. 
     */
    protected Human(int health, double speed, int range, int damage, int delay, int value, String animType) {
        super(health, speed, range, damage, delay, value, false);
        totalHumansSpawned++;
        this.animationType = animType;
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Executes attack behavior. 
     */
    public void act() {
        super.act();
        attackBehavior();
    }
    
    /**
     * Defines the attack behavior for the human. 
     * <p>
     * This is abstract and must be implemented by subclasses to define specific human attacks. 
     */
    protected abstract void attackBehavior();
    
    /**
     * Moves the human forward (to the left side) by its current speed. 
     */
    protected void moveForward() {
        setLocation(getPreciseX() - speed, getPreciseY());
    }

    /**
     * Moves the human toward a robot target. 
     *
     * @param target The Robot unit to move toward. 
     */
    protected void moveTowardRobot(Robot target) {
        if (target != null) moveToward(target, range);
    }
    
    /**
     * Sets the total number of humans spawned. 
     *
     * @param amount The new total humans spawned. 
     */
        public static void setTotalHumansSpawned(int amount) {
        totalHumansSpawned = amount;
    }
}
