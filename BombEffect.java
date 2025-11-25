import greenfoot.*;
import java.util.List;
/**
 * BombEffect is subclass for visual effects in the world. 
 * Represents a explosion. 
 * <p>
 * It displays a red warning circle. After a few seconds, 
 * the bomb explodes, applies damage to nearby Humans, and 
 * fades out gradually. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class BombEffect extends Effect
{
    private int radius; //explosion radius
    private int explosionDuration = 50; // frames to fade out
    private boolean exploded = false; //tracks if the bomb has exploded
    /**
     * Constructor for BombEffect creates a new BombEffect with a radius. 
     * <p>
     * Draws the initial warning circle in red. 
     *
     * @param radius    the radius of the explosion in pixels 
     */
    public BombEffect(int radius) {
        super(radius * 2, radius * 2, new Color(255, 0, 0, 180));
        this.radius = radius;
        
        // draw initial warning circle
        GreenfootImage circle = new GreenfootImage(radius * 2, radius * 2);
        circle.setColor(new Color(255, 0, 0, 180));
        circle.fillOval(0, 0, radius * 2, radius * 2);
        setImage(circle);
    }
    
    /**
     * Updates the bomb effect each frame. 
     * <p>
     * Waits for 180 frames (~1.5 seconds) before exploding, 
     * then damages all Humans within radius and starts fade-out. 
     */
    protected void updateEffect() {
        //increase timer
        timer++;
        
        if (!exploded && timer >= 180) 
        { // explode after 1.5 seconds
            exploded = true;
            timer = 0; // reset timer for fade out
            // damage humans in range
            List<Human> humans = getObjectsInRange(radius, Human.class);
            for (Human h : humans) 
            {
                getWorld().removeObject(h); // or remove if instant kill
            }
        }
    }

    /**
     * Draws the bomb effect each frame. 
     * <p> 
     * After explosion, gradually fades out the red circle 
     * until it's fully gone and removes itself from the world. 
     */
    protected void drawEffect() {
        if (exploded) 
        {
            int alpha = Math.max(0, 180 - (180 / explosionDuration) * timer);
            
            GreenfootImage fadeCircle = new GreenfootImage(radius * 2, radius * 2);
            fadeCircle.setColor(new Color(255, 0, 0, alpha));
            fadeCircle.fillOval(0, 0, radius * 2, radius * 2);
            setImage(fadeCircle);

            if (alpha <= 0 && getWorld() != null) 
            {
                getWorld().removeObject(this);
            }
        }
    }
}
