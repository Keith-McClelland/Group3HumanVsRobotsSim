import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class HospitalDoor extends SuperSmoothMover
{
    private GreenfootImage[] doorFrames;
    private int frameIndex = 0;
    private int frameSpeed = 5; // acts per frame
    private int frameCounter = 0;
    private boolean opening = false;
    private boolean closing = false;

    public HospitalDoor() {
        // Load frames
        doorFrames = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            doorFrames[i] = new GreenfootImage("door" + (i+1) + ".png");
        }
        setImage(doorFrames[0]); // start closed
    }

    public void act() {
        if (opening) {
            animateOpening();
        } else if (closing) {
            animateClosing();
        }
    }

    // Public method to trigger the door opening
    public void openDoor() {
        if (!opening && !closing) { // only trigger if not already animating
            opening = true;
            frameIndex = 0;
            frameCounter = 0;
        }
    }

    private void animateOpening() {
        frameCounter++;
        if (frameCounter >= frameSpeed) {
            frameCounter = 0;
            frameIndex++;
            if (frameIndex >= doorFrames.length) {
                // fully opened, start closing automatically
                opening = false;
                closing = true;
                frameIndex = doorFrames.length - 1; // start closing from last frame
            } else {
                setImage(doorFrames[frameIndex]);
            }
        }
    }

    private void animateClosing() {
        frameCounter++;
        if (frameCounter >= frameSpeed) {
            frameCounter = 0;
            frameIndex--;
            if (frameIndex < 0) {
                closing = false;
                setImage(doorFrames[0]); // back to closed
            } else {
                setImage(doorFrames[frameIndex]);
            }
        }
    }
}

