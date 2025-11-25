import greenfoot.*;
/**
 * HospitalDoor represents an automated door that opens and closes 
 * with an animation. 
 * <p>
 * The door opens automatically after a fixed interval, remains open 
 * for a short period, then closes again. It uses an array of images 
 * for the animation frames. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class HospitalDoor extends SuperSmoothMover {
    //manages opening and closing animation
    private GreenfootImage[] doorFrames;
    private int frameIndex = 0;
    private int frameCounter = 0;
    private int frameSpeed = 5;
    
    //checks if the door is opening or closing
    private boolean opening = false;
    private boolean closing = false;

    private int autoCounter = 0;          // counts frames
    private int openInterval = 300;       // how long it should stay open
    /**
     * Constructor for HospitalDoor that creates a HospitalDoor object and loads its animation frames. 
     * The door starts in the closed state. 
     */
    public HospitalDoor() {
        // load frames for animation
        doorFrames = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            doorFrames[i] = new GreenfootImage("door" + (i + 1) + ".png");
        }
        setImage(doorFrames[0]);  // start closed
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Plays opening or closing animation. 
     */
    public void act() {
        autoOpenLogic();
        if (opening) animateOpening();
        else if (closing) animateClosing();
    }
    
    /**
     * Controls automatic opening of the door. 
     * <p>
     * Increments a counter each frame, and triggers the opening 
     * animation once the interval is reached. 
     */
    private void autoOpenLogic() {
        autoCounter++;

        //checks if it has been 5 seconds to start opening door animation
        if (autoCounter >= openInterval) 
        {
            autoCounter = 0;
            // trigger your animation
            if (!opening && !closing) 
            {
                opening = true;
                frameIndex = 0;
                frameCounter = 0;
            }
        }
    }
    
    /**
     * Animates the door opening by cycling forward through the frames. 
     * Once fully opened, triggers the closing animation. 
     */
    private void animateOpening() 
    {
        frameCounter++;

        if (frameCounter >= frameSpeed) 
        {
            frameCounter = 0;
            frameIndex++;

            if (frameIndex >= doorFrames.length) 
            {
                opening = false;
                closing = true;
                frameIndex = doorFrames.length - 1; // start closing from last
            } else 
            {
                setImage(doorFrames[frameIndex]);
            }
        }
    }
    
    /**
     * Animates the door closing by cycling backward through the frames. 
     * Once fully closed, stops the animation. 
     */
    private void animateClosing() {
        frameCounter++;

        if (frameCounter >= frameSpeed) {
            frameCounter = 0;
            frameIndex--;

            if (frameIndex < 0) {
                closing = false;
                setImage(doorFrames[0]);
            } else {
                setImage(doorFrames[frameIndex]);
            }
        }
    }
}




