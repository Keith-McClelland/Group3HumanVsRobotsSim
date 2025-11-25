import greenfoot.*;
import java.util.List;

public class Factory extends Buildings
{
    private int repairAmount = 5;     // how much to repair robots per tick
    private int cooldown = 50;        // ticks between repairs
    private int timer = 0;            // counts up to cooldown

    public Factory() 
    {
        super(400, false);  // max hp 400, belongs to robot side

        // set factory image
        GreenfootImage img = new GreenfootImage("factory.png");
        img.scale(200, 100);
        setImage(img);
    }

    // main method controlling factory actions
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

    // loop through all robots and repair them
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

