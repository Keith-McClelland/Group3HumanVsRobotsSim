import greenfoot.*;
import java.util.ArrayList;
/**
 * Robot is an abstract subclass of Units representing enemy units. 
 * Robots automatically move toward Humans and attack when in range. 
 * <p>
 * This class handles: 
 * - Tracking number of robots alive and spawned 
 * - Movement toward nearest Human 
 * - Health and death logic
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 */
public abstract class Robot extends Units {

    protected int cooldown = 0;
    private boolean pendingRemoval = false;

    private static int numRobots = 0;
    public static int totalRobotsSpawned = 0;
    
    /**
     * Constructs a new Robot unit. 
     *
     * @param health  The initial health points of the robot. 
     * @param speed   The movement speed of the robot. 
     * @param range   The attack range of the robot. 
     * @param damage  The damage the robot deals per attack. 
     * @param delay   The cooldown (in frames) between attacks. 
     * @param value   The resource value granted to Humans when defeated. 
     */

    protected Robot(int health, double speed, int range, int damage, int delay, int value) {
        super(health, speed, range, damage, delay, value, true);
        numRobots++;
        totalRobotsSpawned++;
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Executes attack behavior, and checks world boundaries and removes units. 
     */
    @Override
    public void act() {

        if (getWorld() == null) return;

        if (getHealth() <= 0) {
            markForRemoval();
        }

        if (!pendingRemoval) {
            attackBehavior();
            moveTowardHuman();
        } else {
            die();
        }
        checkEdges();

    }
    
    /**
     * Defines the specific attack behavior of the Robot. 
     * <p>
     * Must be implemented by subclasses. 
     */
    protected abstract void attackBehavior();
    
    /**
     * Moves the Robot toward the closest Human within its range. 
     */
    protected void moveTowardHuman() {
        Human target = getClosestHuman();
        if (target != null) {
            moveToward(target, range);
        }
    }
    
    /**
     * Marks the Robot for removal. 
     */
    protected void markForRemoval() {
        //For exploding robot
        pendingRemoval = true;
    }
    
    /**
     * Handles removal of the Robot from the world. 
     * Removes its health bar and decrements alive count. 
     */
    protected void die() {
        removeHealthBar();
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
        numRobots--;
    }
    
    /**
     * Overrides takeDamage to handle pending removal state. 
     *
     * @param dmg Amount of damage to take. 
     */
    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(dmg);
        if (getHealth() <= 0) {
            markForRemoval();
        }
    }
    
    /**
     * Returns the number of robots currently alive in the simulation. 
     * 
     * @return number of robots alive 
     */
    public static int getNumRobots() {
        return numRobots;
    }
    
    /**
     * Returns whether this Robot is pending removal. 
     *
     * @return true if marked for removal 
     */
        protected boolean isPendingRemoval() {
        return pendingRemoval;
    }
    
    /**
     * Sets the total number of Robots spawned. 
     * 
     * @param amount Total robots spawned 
     */
    public static void setTotalRobotsSpawned(int amount) {
        totalRobotsSpawned = amount;
    }
}
