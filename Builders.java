import greenfoot.*;
//NOT USING ANYMORE
public class Builders extends SuperSmoothMover {

    private final int FENCE_SPACING = 40;
    private final int SPEED = 2;

    private int lastFenceY;
    private int fenceCount = 0;
    private int maxFences = 13;

    private boolean reachedMiddle = false;
    private boolean finishedBuilding = false;

    private int targetX;   // set once world exists

    public Builders() {
        setImage("builder.png");
    }

    public void addedToWorld(World world) {
        targetX = world.getWidth() / 2 - 50;   // safe to do here
        lastFenceY = getY();
    }

    public void act() {
        if (getWorld() == null) return;

        if (!reachedMiddle) {
            moveToMiddle();
            return;
        }

        if (finishedBuilding) {
            exitRight();
            return;
        }

        buildFences();
    }

    /** ===================== MOVEMENT ======================== */

    private void moveToMiddle() {
        if (Math.abs(targetX - getX()) <= SPEED) {
            setLocation(targetX, getY());
            reachedMiddle = true;
            return;
        }

        int direction = (targetX > getX()) ? SPEED : -SPEED;
        setLocation(getX() + direction, getY());
    }

    private void exitRight() {
        setLocation(getX() + SPEED, getY());

        // if fully off screen → remove
        if (getX() >= getWorld().getWidth() - 1) {
            getWorld().removeObject(this);
        }
    }

    /** ===================== FENCE BUILDING ======================== */

    private void buildFences() {
        // finished?
        if (fenceCount >= maxFences) {
            Fences.buildComplete(getWorld());
            finishedBuilding = true;
            return;
        }

        // move downward
        setLocation(getX(), getY() + SPEED);

        // check spacing
        if (getY() - lastFenceY >= FENCE_SPACING) {
            placeFence();
            lastFenceY = getY();
            fenceCount++;
        }
    }

    private void placeFence() {
        getWorld().addObject(new Fences(), getX() - 50, getY());
    }
}


