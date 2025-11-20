import greenfoot.*;

public class Builders extends SuperSmoothMover {
    
    private int fenceSpacing = 40;
    private int lastFenceY;
    private int speed = 2;
    private int fenceCount = 0;
    private int maxFences = 13;  // how many fences total
    private SuperStatBar workProgressBar;
    private boolean reachedMiddle = false;
    private boolean finishedBuilding = false;

    //the x position required for the builder to contruct the barricade 
    private int targetX = getWorld().getWidth() / 2 - 50;  
    //initalizes image 
    GreenfootImage  builder = new GreenfootImage("builder.png");
    
    public Builders() {
        //sets image for the actor 
        setImage(builder);
        
        /*
        //indicates the progress of the building process 
        workProgressBar = new SuperStatBar(maxFences, 0, this, 50, 6, -40, Color.RED, Color.YELLOW);*/
    }

    public void addedToWorld(World world) {
        //world.addObject(workProgressBar, getX(), getY() - 40); // place above builder
        lastFenceY = getY();
    }

    public void act() {
        if (getWorld() == null) return;

        // makes the builder move horizontally to the middle first
        if (!reachedMiddle) {
            moveToMiddle();
            return; 
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

        // move downward
        setLocation(getX(), getY() + speed);

        // Build fence after moving enough distance
        if (getY() - lastFenceY >= fenceSpacing) {
            makeFence();
            fenceCount++;
            //workProgressBar.update(fenceCount);
            lastFenceY = getY();
        }
        
    }

    private void moveToMiddle() {
        // move horizontally toward required position to build the fence
        if (Math.abs(targetX - getX()) <= speed) 
        {    
            setLocation(targetX, getY());
            reachedMiddle = true;
        } 
        else if (getX() < targetX) 
        {
            setLocation(getX() + speed, getY());
        } 
        else if (getX() > targetX) 
        {
            setLocation(getX() - speed, getY());
        }
    }

    private void moveOffScreen() {
        // move right until the builder exits the screen
        if (getX() < getWorld().getWidth()) {
            setLocation(getX() + speed, getY());
        } 
    }

    private void makeFence() {
        //creates a fence infront of the builder
        getWorld().addObject(new Fences(), getX() - 50, getY());
    }
    
    private void checkEdge() 
    {
        if (getWorld() == null) return;
    
        //ensures builder does not get removed when spawned(builder is spawned at edge of the world), but when it is finished building
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




