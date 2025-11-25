import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * The LevelUpIndicator class represents a temporary on-screen visual 
 * notification that displays when the player levels up. 
 * <p>
 * The indicator appears, plays a sound effect, blinks for a set duration, and then automatically 
 * disappears. 
 *
 * @author Veznu Premathas
 * @version November 2025
 */
public class LevelUpIndicator extends Actor
{
    private GreenfootImage levelUp; // initializes image
    private int timer = 0; // counts frames
    private final int duration = 240; // 4 seconds at 60 FPS
    private final int blinkInterval = 15; // frames between visibility toggle

    GreenfootSound levelUpSound = new GreenfootSound("levelUpSound.mp3");
    
    /**
     * Creates a new LevelUpIndicator, loads its image, 
     * Handles blinking animation and 
     * automatically removes the object after a duration. 
     */
    public LevelUpIndicator()
    {
        levelUp = new GreenfootImage("levelUp.png");
        levelUp.scale(180, 40); // scales the image
        setImage(levelUp); // sets the image
        levelUpSound.setVolume(50);
        levelUpSound.play();
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Runs the behaviour to complete the buildings task. 
     */
    public void act()
    {
        timer++;

        // make it blink by toggling visibility
        if ((timer / blinkInterval) % 2 == 0) 
        {
            setImage(levelUp);
        } else 
        {
            setImage((GreenfootImage) null); // hide image
        }

        // remove after certain period of time
        if (timer >= duration) 
        {
            if (getWorld() != null) 
            {
                getWorld().removeObject(this);
            }
        }
    }
}

