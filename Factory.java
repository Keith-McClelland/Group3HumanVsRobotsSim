import greenfoot.*;
import java.util.ArrayList;
/**
 * The Factory class is a subclass of Buildings. 
 * Robot-side supportive building that automatically heals all robots when low on hp 
 * every cooldown cycle. 
 *
 * The Factory includes: 
 * - Repairs all robot units on the screen at a fixed interval. 
 * - Restores a configurable amount of health per cycle. 
 * <p>
 * The Factory inherits HP, destruction logic, and health bar behavior from 
 * Buildings. Its behavior is executed through completeTask(). 
 * 
 * @author Jonathan Shi
 * @version November 2025
 */
public class Factory extends Buildings
{
    private int repairAmount = 5;     // how much to repair robots per tick
    private int cooldown = 50;        // ticks between repairs
    private int timer = 0;            // counts up to cooldown
    
    /**
     * Constructs a new Factory building. 
     *
     * <p>
     * This Factory starts with 400 maximum HP and belongs to the robot side. 
     * Its sprite is automatically loaded. 
     */ 
    public Factory() 
    {
        super(400, false);  // max hp 400, belongs to robot side

        // set factory image
        GreenfootImage img = new GreenfootImage("factory.png");
        img.scale(200, 100);
        setImage(img);
    }
    
    /**
     * Performs this Factory's automated behavior each act cycle.
     *
     * <p>
     * This method runs a timer. Once the timer reaches 
     * the cooldown threshold, the Factory repairs all robots by running 
     * healRobots() and then resets the timer. 
     */
    public void completeTask()
    {
        timer++;

        // heal robots periodically
        if (timer >= cooldown) 
        {
            repairRobots();   // restore robot health
            timer = 0;        // reset timer
        }
    }
    
    /**
     * Repairs all robot units currently present in the world. 
     * Each robot is healed by repairAmount HP. 
     */
    private void repairRobots() 
    {
        for (Robot r : getWorld().getObjects(Robot.class)) 
        {
            if (r.getHealth() < r.maxHealth) 
            {
                r.setHealth(Math.min(r.getHealth() + repairAmount, r.maxHealth));
            }
        }
    }
}

