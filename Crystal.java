import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Crystal extends SuperSmoothMover
{
    private GreenfootImage crystal;
    private GreenfootImage[] revolvingCrystal;
    
    private int frameIndex = 0;
    private int revolvingFrameSpeed = 5; // acts per frame
    private int frameCounter = 0;
    
    public Crystal()
    {
        crystal = new GreenfootImage("objective000.png"); // start at 1 since you don't have 0
        crystal.scale(45,90);
        setImage(crystal);
    
        // Shooting animation frames 002–006
        revolvingCrystal = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            revolvingCrystal[i] = new GreenfootImage("objective00" + (i+1) + ".png"); // 002,003,004,005,006
            revolvingCrystal[i].scale(45,90);
        }
        
    }
    
    public void act()
    {
        animateRevolution();
    }
    
    private void animateRevolution() {
        setImage(revolvingCrystal[frameIndex]);
        frameCounter++;
        if (frameCounter >= revolvingFrameSpeed) {
            frameCounter = 0;
            frameIndex++;
            if (frameIndex >= revolvingCrystal.length) {
                frameIndex = 0;
                setImage(crystal);
            }
        }
    }
}
