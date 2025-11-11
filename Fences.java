import greenfoot.*;  
import java.util.List;

public class Fences extends SuperSmoothMover {
    //creates a bar to indicate the damage on the fence 
    public static SuperStatBar fenceHPBar;
    //track if the fence has been created
    private static boolean fencesComplete = false;
    //the amount of damage the fence can withstand 
    private static int hitpoint = 1000;
    //creates and holds the image of the fence 
    GreenfootImage fence = new GreenfootImage("images/fence.png");

    //fences length and width 
    private int fenceLength = 30;
    private int fenceHeight = 60;
    
    public Fences() {
        //rescales fence to desired size
        fence.scale(fenceLength, fenceHeight);
        setImage(fence);
    }

    public void act() {
        //checks if the fence is completly damaged and removes the following
        if (hitpoint <= 0 && getWorld() != null) {
            getWorld().removeObject(fenceHPBar);
            removeAllFences();
        }
    }

    private void removeAllFences() {
        if (getWorld() == null) return;
        
        //gets all the fences that are currently in the world and removes them
        List<Fences> all = getWorld().getObjects(Fences.class);
        for (Fences f : all) {
            if (f.getWorld() != null) f.getWorld().removeObject(f);
        }
    }

    public void damage(int amount) {
        //when this method is called it from projectile it will take damage
        if (fencesComplete && fenceHPBar != null) {
            hitpoint -= amount;
            fenceHPBar.update(hitpoint);
        }
    }

    public static void buildComplete(World world) {
        fencesComplete = true;
        fenceHPBar = new SuperStatBar(hitpoint, hitpoint, null, 120, 12, -40, Color.GREEN, Color.RED);
        world.addObject(fenceHPBar, world.getWidth() / 2, world.getHeight() - 30);
    }
}
