import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Crystal extends SuperSmoothMover
{
    //initalizes required images
    private GreenfootImage crystal;
    private GreenfootImage[] revolvingCrystal;
    
    private int frameIndex = 0; //tracks the current frame
    private int revolvingFrameSpeed = 5; // interval frame must be changed
    private int frameCounter = 0; //counter to know when to change the frame
    
    public Crystal()
    {
        crystal = new GreenfootImage("objective000.png"); 
        //scales and sets image
        crystal.scale(45,90);
        setImage(crystal);
    
        //complets revolving animation (makes the crystal looks like its moving)
        revolvingCrystal = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) 
        {
            //scales and load images
            revolvingCrystal[i] = new GreenfootImage("objective00" + (i+1) + ".png"); // 002,003,004,005,006
            revolvingCrystal[i].scale(45,90);
        }
        
    }
    
    public void act()
    {
        //complete animation
        animateRevolution();
    }
    
    private void animateRevolution() 
    {
        setImage(revolvingCrystal[frameIndex]);
        frameCounter++;
        if (frameCounter >= revolvingFrameSpeed) 
        {
            frameCounter = 0;
            frameIndex++;
            if (frameIndex >= revolvingCrystal.length) 
            {
                frameIndex = 0;
                setImage(crystal);
            }
        }
    }
}
