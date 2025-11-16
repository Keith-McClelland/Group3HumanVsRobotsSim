import greenfoot.*;
import java.awt.Color;

public class Builders extends SuperSmoothMover {
    private int fenceSpacing = 40;
    private int lastFenceY;
    private int speed = 2;
    private int fenceCount = 0;
    private int maxFences = 13;  // how many fences total
    private SuperStatBar workProgressBar;
    private boolean reachedMiddle = false;
    private boolean finishedBuilding = false;

    private int targetX;  // X position for middle of world
    GreenfootImage  builder = new GreenfootImage("builder.png");
    public Builders() {
        setImage(builder);
        
        // Builder progress bar
        //workProgressBar = new SuperStatBar(maxFences, 0, this, 50, 6, -40, Color.RED, Color.YELLOW);
    }

    public void addedToWorld(World world) {
        //world.addObject(workProgressBar, getX(), getY() - 40); // place above builder
        lastFenceY = getY();

        // Middle X of the screen
        targetX = world.getWidth() / 2;
    }

    public void act() {
        if (getWorld() == null) return;

        // Move horizontally to the middle first
        if (!reachedMiddle) {
            moveToMiddleX();
            return; // don't build fences yet
        }

        // If finished building, move off-screen to the right
        if (finishedBuilding) {
            moveOffScreen();
            checkEdge();
            return;
        }

        // Fence-building logic
        if (fenceCount >= maxFences) {
            //getWorld().removeObject(workProgressBar);
            Fences.buildComplete(getWorld());
            finishedBuilding = true;
            return;
        }

        // Move downward
        setLocation(getX(), getY() + speed);

        // Build fence after moving enough distance
        if (getY() - lastFenceY >= fenceSpacing) {
            makeFence();
            fenceCount++;
            //workProgressBar.update(fenceCount);
            lastFenceY = getY();
        }
        
    }

    private void moveToMiddleX() {
        // Move horizontally toward targetX
        if (Math.abs(targetX - getX()) <= speed) {
            setLocation(targetX, getY());
            reachedMiddle = true;
        } else if (getX() < targetX) {
            setLocation(getX() + speed, getY());
        } else if (getX() > targetX) {
            setLocation(getX() - speed, getY());
        }
    }

    private void moveOffScreen() {
        // Move right until off-screen
        if (getX() < getWorld().getWidth()) {
            setLocation(getX() + speed, getY());
        } 
    }

    private void makeFence() {
        getWorld().addObject(new Fences(), getX() - 50, getY());
    }
    
    private void checkEdge() 
    {
        if (getWorld() == null) return;
    
        if (finishedBuilding) {
            if (getX() <= 0 || getX() >= getWorld().getWidth() - 1) {
                World world = getWorld(); // store reference first
                if (world != null) {
                    world.removeObject(this);  // remove builder safely
                }
                return; // stop any further actions
            }
        }
    }
}




