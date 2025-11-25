import greenfoot.*;
/**
 * ScanAnimation is a visual effect representing a scanning animation, 
 * used by drones. 
 * <p>
 * The animation cycles through a series of frames a fixed number 
 * of times before removing itself from the world. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class ScanAnimation extends SuperSmoothMover
{
    //controls animation
    private GreenfootImage[] scans;
    private int frameIndex = 0;
    private int scanFrameSpeed = 10; 
    private int frameCounter = 0;

    //tracks how many cycles of animation has been completed
    private int cyclesDone = 0;
    //numbers of cycle animation it must complete 
    private int totalCycles = 3;
    
     /**
     * Constructor for ScanAnimation that creates a new ScanAnimation object. 
     * <p>
     * Loads the animation frames and scales them to the desired size. 
     */
    public ScanAnimation()
    {
        //load scan frames
        scans = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            scans[i] = new GreenfootImage("scan0" + (i+1) + ".png");
            scans[i].scale(200, 300); //scale
        }

        //start on first frame
        setImage(scans[0]);
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Plays the animation. 
     */
    public void act()
    {
        //run animation every frame
        animateScan();
    }
    
     /**
     * Creates the scan animation. 
     * <p>
     * Updates the frame every cycle, 
     * loops through the frames, counts completed cycles, 
     * and removes the object after finishing its cycles. 
     */
    private void animateScan()
    {
        //count time between frame changes
        frameCounter++;

        if (frameCounter >= scanFrameSpeed) {
            frameCounter = 0;
            frameIndex++;

            //restart animation cycle
            if (frameIndex >= scans.length) {
                frameIndex = 0;
                cyclesDone++;

                //remove object after finishing cycles
                if (cyclesDone >= totalCycles) {
                    getWorld().removeObject(this);
                    ((IntroWorld)getWorld()).doneScanning = true;
                    return;
                }
            }

            //update displayed frame
            setImage(scans[frameIndex]);
        }
    }
}

