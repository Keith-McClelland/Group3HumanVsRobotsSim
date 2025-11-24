import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LevelUpIndicator extends Actor
{
    private GreenfootImage levelUp; // initializes image
    private int timer = 0; // counts frames
    private final int duration = 240; // 4 seconds at 60 FPS
    private final int blinkInterval = 15; // frames between visibility toggle

    public LevelUpIndicator()
    {
        levelUp = new GreenfootImage("levelUp.png");
        levelUp.scale(180, 40); // scales the image
        setImage(levelUp); // sets the image
    }

    public void act()
    {
        timer++;

        // Make it blink by toggling visibility
        if ((timer / blinkInterval) % 2 == 0) {
            setImage(levelUp);
        } else {
            setImage((GreenfootImage) null); // hide image
        }

        // Remove after duration
        if (timer >= duration) {
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
        }
    }
}

