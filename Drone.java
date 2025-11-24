import greenfoot.*;
import java.util.List;

public class Drone extends SuperSmoothMover
{
    //initalizes image and object
    GreenfootImage drone = new GreenfootImage("drone.png");
    RobotSpeech speech;
    
    //track states if the drone finished certain actions
    private boolean isScanning = false;
    private boolean flippedOnce = false;


    public Drone()
    {
        //scales and sets image
        drone.scale(50,40);
        setImage(drone);
    }

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


    private void moveTowardCrystal(Crystal crystal) 
    {
        double dx = crystal.getX() - getX();
        double dy = crystal.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 100) 
        {
            setLocation(getX() + (int)(dx / distance * 2),getY() + (int)(dy / distance * 2));

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

    private void getClosestCrystal() 
    {
        //finds the crystal
        List<Crystal> crystals = getObjectsInRange(1000, Crystal.class);
        if (crystals.isEmpty()) return;

        Crystal c = crystals.get(0);
        //move toward the crystal
        moveTowardCrystal(c);
    }
    
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



    private void startScanAnimation()
    {
        if (isScanning) return;
        isScanning = true;

        //creates an scan animation object
        ScanAnimation scan = new ScanAnimation();
        getWorld().addObject(scan, getX() + getImage().getWidth()/2 + 10, getY());
    }
}



