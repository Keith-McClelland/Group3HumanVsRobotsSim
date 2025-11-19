import greenfoot.*;
import java.util.List;

public class Drone extends SuperSmoothMover
{
    GreenfootImage drone = new GreenfootImage("drone.png");
    private boolean isScanning = false;
    RobotSpeech speech;

    public Drone()
    {
        drone.scale(50,40);
        setImage(drone);
    }

    public void act()
    {
        // Create speech ONLY AFTER drone is added to world
        if (speech == null && getWorld() != null) {
            speech = new RobotSpeech();
            getWorld().addObject(speech, getX()+110, getY()-95);
        }

        if (!isScanning) {
            getClosestCrystal();
        }
    }

    private void moveTowardCrystal(Crystal crystal) 
    {
        double dx = crystal.getX() - getX();
        double dy = crystal.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 100) {
            setLocation(getX() + (int)(dx / distance * 2),
                        getY() + (int)(dy / distance * 2));

            // keep speech attached to drone
            if (speech != null)
                speech.setLocation(getX()+110, getY()-95);

        } else {
            startScanAnimation();

            // change to speech 2
            if (speech != null)
                speech.showSpeech2();
        }
    }

    private void getClosestCrystal() 
    {
        List<Crystal> crystals = getObjectsInRange(1000, Crystal.class);
        if (crystals.isEmpty()) return;

        Crystal c = crystals.get(0);
        moveTowardCrystal(c);
    }

    private void startScanAnimation()
    {
        if (isScanning) return;
        isScanning = true;

        ScanAnimation scan = new ScanAnimation();
        getWorld().addObject(scan, getX() + getImage().getWidth()/2 + 10, getY());
    }
}



