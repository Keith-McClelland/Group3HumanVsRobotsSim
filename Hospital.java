import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Hospital here.
 * 
 * @author (Jonathan)
 * @version (a version number or a date)
 */
public class Hospital extends Buildings
{
    private int healAmount = 10; // how much HP humans get per tick
    private int cooldown = 30; // healing every 1 seconds
    private int timer = 0; // counts frames since last heal

    public Hospital() {
        super(300);
    }
    
    public void act()
    {
        timer++;
        
        // When 300 frames/5 seconds passes
        if (timer >= cooldown) {
            healHumans();
            timer = 0; // Reset the timer
        }
    }
    
    private void healHumans() {
        ArrayList<Human> humans = new ArrayList<>(getWorld().getObjects(Human.class));
    
        // Only heal if their HP is BELOW HALF
        for (Human h : humans) {
            if (h.getHealth() < h.maxHealth) {
                int newHealth = h.getHealth() + healAmount;
                
                if (newHealth > h.maxHealth) {
                    newHealth = h.maxHealth;
                }
                h.setHealth(newHealth);
            }
        }
    }
}
