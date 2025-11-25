import greenfoot.*;
/**
 * The Hospital class is a subclass of Buildings. 
 * Human-side supportive building that automatically heals all humans when low on hp 
 * every cooldown cycle. 
 *
 * The Factory includes: 
 * - Heals all human units on the screen at a fixed interval. 
 * - Restores a configurable amount of health per cycle. 
 * <p>
 * The Hospital inherits HP, destruction logic, and health bar behavior from 
 * Buildings. Its behavior is executed through completeTask(). 
 * 
 * @author Jonathan Shi 
 * @author Veznu Premathas
 * @version November 2025 
 */
public class Hospital extends Buildings
{
    private int healAmount = 5;       // how much health to restore per tick
    private int cooldown = 50;        // ticks between healing
    private int timer = 0;            // counts up to cooldown

    private boolean doorConstructed = false;  // only spawn door once
    GreenfootImage hospital = new GreenfootImage("hospital.png");  // hospital image
    
    /**
     * Constructs a new Hospital building.
     *
     * <p>
     * The Hospital begins with 1000 maximum HP and belongs to the human side. 
     * The hospital sprite is loaded. 
     */
    public Hospital() 
    {
        super(1000, true);   // max hp 1000, belongs to human side
        setImage(hospital);  // set hospital sprite
    }
    
    /**
     * Performs this Hospital's automated behavior each act cycle. 
     *
     * <p>
     * This method runs a timer. Once the timer reaches 
     * the cooldown threshold, the Hospital heals all humans by running 
     * healHumans() and then resets the timer. 
     */
    public void completeTask() 
    {
        timer++;

        // spawn hospital door only once
        if(!doorConstructed)
        {
            getWorld().addObject(new HospitalDoor(), getX(), getY() + 20);
            doorConstructed = true;
        }

        // heal humans periodically
        if (timer >= cooldown) 
        {
            healHumans();  // restore health to humans
            timer = 0;     // reset timer
        }
    }
    
    /**
     * Heals all human units currently present in the world. 
     * Each human is healed by healAmount HP. 
     */
    private void healHumans() 
    {
        for (Human h : getWorld().getObjects(Human.class)) 
        {
            if (h.getHealth() < h.maxHealth) 
            {
                int newHealth = Math.min(h.getHealth() + healAmount, h.maxHealth);
                h.setHealth(newHealth);  // set capped health
            }
        }
    }
}
