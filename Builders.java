import greenfoot.*;

public class Builders extends SuperSmoothMover {
    private int fenceSpacing = 40;
    private int lastFenceY;
    private int speed = 2;
    private int fenceCount = 0;
    private int maxFences = 13;  // how many fences total
    private SuperStatBar workProgressBar;
    private long stopUntil = 0;

    public Builders() {
        //when the builder is spawned he will have a bar indicating his progress
        workProgressBar = new SuperStatBar(maxFences, 0, this, 50, 6, -40, Color.GREEN, Color.RED);
    }

    public void addedToWorld(World world) {
        world.addObject(workProgressBar, getX(), getY() - 40); // place above soldier
        lastFenceY = getY();
    }

    public void act() {
        if (getWorld() == null) 
        {
            return;
        }
        
        //once fence has been created it will remove progress bar
        if (fenceCount >= maxFences) 
        {
            getWorld().removeObject(workProgressBar);
            return;
        }
        
        //once the builder reaches the bottom of the world 
        if (getY() >= getWorld().getHeight()) {
            setLocation(getX(), getWorld().getHeight());
        }
        

        // Move downward
        setLocation(getX(), getY() + speed);

        //build fence after moving enough distance
        if (getY() - lastFenceY >= fenceSpacing) {
            makeFence();
            fenceCount++;
            workProgressBar.update(fenceCount); // update bar
            lastFenceY = getY();
            stopUntil = System.currentTimeMillis() + 30000;
        }

        // Stop at bottom of the world
        
    }

    private void makeFence() {
        getWorld().addObject(new Fences(), getX() - 50, getY());
    }
    
}



