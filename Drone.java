import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Drone extends SuperSmoothMover
{
    GreenfootImage drone = new GreenfootImage("drone.png");
    
    public Drone()
    {
        drone.scale(50,40);
        setImage(drone);
        
        //ScanAnimation scan = new ScanAnimation();
        //addObject(ScanAnimation scan);
        
    }
    
    public void act()
    {
        getClosestCrystal();
    
    }
    

    
    private void moveTowardCrystal( Crystal crystal) {
        double dx = crystal.getX() - getX();
        double dy = crystal.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 100) { // move only if not in attack range
            setLocation(
                getX() + (int)(dx / distance * 2),
                getY() + (int)(dy / distance * 2)
            );
        }
    }
    
    private void getClosestCrystal() {

        List<Crystal> crystal = getObjectsInRange(1000, Crystal.class);

        for (Crystal c : crystal) {
            moveTowardCrystal(c);
        }
    }
}
