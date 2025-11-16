import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
public class BombEffect extends SuperSmoothMover
{
    //controls the timing
    private int timer = 0;
    private int endTime = 180; //when the explosion will end
    private boolean tickingStarted = false;
    
    //tracks if the bomb effect visuals is visible
    private boolean visible = true; 
    
    //controls the properties bomb effect
    private int baseTransparency = 255;
    private int radius = 350;
    private int blinkingInterval = 36;

    //static GreenfootSound bombticking = new GreenfootSound("bombticking.mp3");
    
    public BombEffect()
    {
        GreenfootImage warningCircle = new GreenfootImage(radius,radius);
        warningCircle.setColor((new Color(255,0,0,240)));
        warningCircle.fillOval(0,0,radius,radius);
        setImage(warningCircle);
    }

    public void act()
    {
        // increases the timer every frame
        timer ++;
        //playTickingSound();
        createBlinkingEffect();
        explode();
        
    }
    
    private void createBlinkingEffect()
    {
        //creates an blinking effect before explosion
        if(timer % blinkingInterval == 0)
        {
            visible = !visible;
            //makes the blinking speed faster as the bomb is about to explode
            blinkingInterval -= 2;
            //bombticking.setVolume(60);
            //bombticking.playLoop();
            //bombexplosion.stop();
            //created the blinking effect by changing the transparency after  
            //a certain period
            if (visible)
            {
                getImage().setTransparency(baseTransparency);
            }
            else
            {
                getImage().setTransparency(0);
            }
        }
    }
    
    /*private void playTickingSound()
    {
        if (!tickingStarted) {
            bombticking.setVolume(60);
            bombticking.playLoop();
            tickingStarted = true;
        }
    }*/
    
    private void explode()
    {
        //when the timer reaches the endtime it will trigger an explosion
        if(timer >= endTime)
        {

            //RedFlashEffect redScreen = new RedFlashEffect(1024,800);
            //getWorld().addObject(redScreen, getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            
            //finds all vehicles within the explosion radius
            List<Human> humanInRange = getObjectsInRange(radius,Human.class);
            for (Human human : humanInRange) {
                getWorld().removeObject(human);
            }
            Fences.damage(3000);
            getWorld().removeObject(this);
        }
    }
}