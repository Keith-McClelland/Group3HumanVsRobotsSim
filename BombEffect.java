import greenfoot.*;
import java.util.List;

public class BombEffect extends Effect
{
    private int radius; //explosion radius
    private int explosionDuration = 50; // frames to fade out
    private boolean exploded = false;
    
    public BombEffect(int radius) 
    {
        super(radius * 2, radius * 2, new Color(255, 0, 0, 180));
        this.radius = radius;
        
        // draw initial warning circle
        GreenfootImage circle = new GreenfootImage(radius * 2, radius * 2);
        circle.setColor(new Color(255, 0, 0, 180));
        circle.fillOval(0, 0, radius * 2, radius * 2);
        setImage(circle);
    }

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
            Fences.damage(3000); // optional
        }
    }

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
