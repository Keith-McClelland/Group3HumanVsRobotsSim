import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Effect is an abstract class for visual effects in the world. 
 * <p>
 * Effects have a width, height, color, and a timer that tracks 
 * how long the effect has been active. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public abstract class Effect extends SuperSmoothMover
{
    //manages the dimensions of the effect
    protected int width;
    protected int height;
    protected GreenfootImage image;

    //tracks the time
    protected int timer = 0;
    /**
     * Constructor for Effect creates a new Effect with specified dimensions and base color. 
     *
     * @param width     Width of the effect. 
     * @param height    Height of the effect. 
     * @param baseColor Initial color of the effect. 
     *
     * The constructor creates a filled rectangle of the specified size 
     * and color and sets it as the effect's image. 
     */
    public Effect(int width, int height, Color baseColor)
    {
        //creates the required rectangle based on paramters and set values
        this.width = width;
        this.height = height;
        
        image = new GreenfootImage(width, height);
        image.setColor(baseColor);
        image.fill();
        setImage(image);
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Updates the effect's behavior, and redraws its appearance. 
     */
    public void act()
    {
        timer++; //increases timer
        updateEffect();   // describe what happens (flash speed)
        drawEffect();     // describe how it looks (colour, dimension, transparency)
    }
    
    /**
     * Updates the behavior of the effect. 
     * <p>
     * Subclasses must define this method to specify how the effect changes 
     * over time. 
     */
    protected abstract void updateEffect();
    
    /**
     * Draws the appearance of the effect. 
     * <p>
     * Subclasses must define this method to customize the visual 
     * representation. 
     */
    protected abstract void drawEffect();
}
