import greenfoot.*;
import java.util.List;
/**
 * Drone is an animated object that locates and scans Crystal objects 
 * in the world. It can move toward a crystal, display speech bubbles, and perform 
 * scanning animations. 
 * <p>
 * The Drone works with the RobotSpeech, which visually 
 * follows it and updates depending on its state. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class StoryWorldDrone extends SuperSmoothMover
{
    //initalizes image and object
    GreenfootImage drone = new GreenfootImage("drone.png");
    RobotSpeech speech;
    
    //track states if the drone finished certain actions
    private boolean isScanning = false;
    private boolean flippedOnce = false;

    /**
     * Constructor for Drone that creates a new Drone object. 
     */
    public StoryWorldDrone()
    {
        //scales and sets image
        drone.scale(50,40);
        setImage(drone);
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Creates a speech bubble, then it scans for the closest 
     * crystal and moves towards it if the crystal hasn't yet 
     * been scanned. 
     */
    public void act()
    {
        // create speech after drone is added to world
        if (speech == null && getWorld() != null) 
        {
            speech = new RobotSpeech();
            getWorld().addObject(speech, getX()+110, getY()-95);
        }

        if (!isScanning) 
        {
            //finds the crystal
            getClosestCrystal();
        }
    }

    /**
     * Moves the drone toward the Crystal object. 
     * When close enough, triggers the scanning animation and updates speech. 
     *
     * @param crystal       The Crystal object to approach. 
     */
    private void moveTowardCrystal(Crystal crystal) 
    {
        double dx = crystal.getX() - getX();
        double dy = crystal.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 100) 
        {
            setLocation(getX() + (int)(dx / distance * 4),getY() + (int)(dy / distance * 2));

            // keep speech attached to drone when it walks toward crystal
            if (speech != null)
            {
                speech.setLocation(getX()+110, getY()-95);
            }
        } 
        else 
        {
            //starts can animation
            startScanAnimation();

            // change to speech 2 once scanning 
            if (speech != null)
            {
                speech.showSpeech2();
            }
        }
    }
    
    /**
     * Finds the closest Crystal within 1000 pixels and moves toward it. 
     */
    private void getClosestCrystal() 
    {
        //finds the crystal
        List<Crystal> crystals = getObjectsInRange(1000, Crystal.class);
        if (crystals.isEmpty()) return;

        Crystal c = crystals.get(0);
        //move toward the crystal
        moveTowardCrystal(c);
    }
    
    /**
     * Moves the drone off-screen to the left side. 
     * Flips the image once and removes the drone from the world when it exits. 
     */
    public void runAway() 
    {
        int speed = 3;
    
        //flips the drone image to face left
         if (!flippedOnce) {
            getImage().mirrorHorizontally();   
            flippedOnce = true;
        }
        
        // move off the left side
        if (getX() > 5) 
        {
            setLocation(getX() - speed, getY());
        } 
        else 
        {
            //once the drone is at the edge of the world it will be removed
            if (getWorld() != null) 
            {
                getWorld().removeObject(this);
            }
        }
    }


    /**
     * Starts the scanning animation once. 
     * Creates a scan animation object. 
     */
    private void startScanAnimation()
    {
        if (isScanning) return;
        isScanning = true;

        //creates an scan animation object
        ScanAnimation scan = new ScanAnimation();
        getWorld().addObject(scan, getX() + getImage().getWidth()/2 + 10, getY());
    }
}




