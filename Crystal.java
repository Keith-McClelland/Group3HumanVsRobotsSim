import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Crystal is an animated object that displays the crystal energy 
 * that is on the human side in the IntroWorld. 
 * <p>
 * The crystal begins with a static base image, then cycles through 
 * 6 animated frames to create a smooth rotation effect. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class Crystal extends SuperSmoothMover
{
    //initalizes required images
    private GreenfootImage crystal;
    private GreenfootImage[] revolvingCrystal;
    
    private int frameIndex = 0; //tracks the current frame
    private int revolvingFrameSpeed = 5; // interval frame must be changed
    private int frameCounter = 0; //counter to know when to change the frame
    
    /**
     * Constructor for Crystal that creates a new Crystal object. 
     * Loads both the base image and all animated revolving frames. 
     */
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
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Updates the crystal's animation cycle. 
     */
    public void act()
    {
        //complete animation
        animateRevolution();
    }
    
    /**
     * Handles the revolving animation: 
     * Displays the current frame  
     * Advances frames after a timed delay 
     * Loops back to frame 0 after the last frame 
     */
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
