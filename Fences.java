import greenfoot.*;
import java.util.List;

//NOT USING ANYMORE
public class Fences extends SuperSmoothMover {

    private static SuperStatBar fenceHPBar;
    private static boolean fencesComplete = false;

    private static int totalHitpoints = 2000;
    private static int currentHitpoints = totalHitpoints;

    private GreenfootImage fence = new GreenfootImage("images/fence.png");

    public Fences() {
        fence.scale(30, 60);
        setImage(fence);
    }

    public void act() {
        buildComplete(getWorld());

        if (currentHitpoints <= 0 && getWorld() != null) {
            if (fenceHPBar != null)
                getWorld().removeObject(fenceHPBar);

            damageRobotsOnBreak();  // <-- Damage robots when fence breaks
            removeAllFences();

            fencesComplete = false;
            currentHitpoints = totalHitpoints;
        }
    }

    // Method to damage all robots in the world
    private void damageRobotsOnBreak() {
        if (getWorld() == null) return;

        List<Robot> robots = getWorld().getObjects(Robot.class);
        for (Robot r : robots) {
            r.takeDamage(50); // Adjust damage as needed
        }
    }

    private void removeAllFences() {
        if (getWorld() == null) return;

        List<Fences> all = getWorld().getObjects(Fences.class);
        for (Fences f : all) {
            if (f.getWorld() != null)
                f.getWorld().removeObject(f);
        }
    }

    public static void damage(int amount) {
        if (fencesComplete && fenceHPBar != null) {
            currentHitpoints -= amount;
            if (currentHitpoints < 0) currentHitpoints = 0;

            fenceHPBar.update(currentHitpoints);
        }
    }

    public static void buildComplete(World world) {
        if (!fencesComplete) {
            fencesComplete = true;

            fenceHPBar = new SuperStatBar(
                totalHitpoints,
                totalHitpoints,
                null,
                50,
                10,
                0,
                Color.GREEN,
                Color.RED
            );

            world.addObject(fenceHPBar, world.getWidth() / 2 - 50, world.getHeight() / 2 + 50);
        }
    }

    // Helper to let other classes know if fences are still up
    public static boolean isFencesUp() {
        return fencesComplete && currentHitpoints > 0;
    }
}



