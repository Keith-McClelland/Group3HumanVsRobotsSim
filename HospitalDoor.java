import greenfoot.*;

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

    public HospitalDoor() {
        // load frames for animation
        doorFrames = new GreenfootImage[5];
        for (int i = 0; i < 5; i++) {
            doorFrames[i] = new GreenfootImage("door" + (i + 1) + ".png");
        }
        setImage(doorFrames[0]);  // start closed
    }

    public void act() {
        autoOpenLogic();
        if (opening) animateOpening();
        else if (closing) animateClosing();
    }

  
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

    //completes opening animation
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

    //completes closing animation
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




