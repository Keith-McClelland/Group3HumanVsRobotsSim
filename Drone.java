import greenfoot.*;
import java.util.List;

public class Drone extends SuperSmoothMover
{
    GreenfootImage drone = new GreenfootImage("drone.png");

    private boolean isScanning = false;

    public Drone()
    {
        drone.scale(50,40);
        setImage(drone);
    }

    public void act()
    {
        if (!isScanning) {
            getClosestCrystal();
        }
    }

    private void moveTowardCrystal(Crystal crystal) 
    {
        double dx = crystal.getX() - getX();
        double dy = crystal.getY() - getY();
        double distance = Math.hypot(dx, dy);

        // Stop moving once in scan range (100)
        if (distance > 100) {
            setLocation(getX() + (int)(dx / distance * 2),
                        getY() + (int)(dy / distance * 2));
        } else {
            startScanAnimation();
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
        if (isScanning) return; // prevent duplicates
        isScanning = true;

        ScanAnimation scan = new ScanAnimation();
        getWorld().addObject(scan, getX()+ getImage().getWidth()/2 + 10, getY());
        
    }
}

