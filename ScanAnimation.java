import greenfoot.*;

public class ScanAnimation extends SuperSmoothMover
{
    //controls animation
    private GreenfootImage[] scans;
    private int frameIndex = 0;
    private int scanFrameSpeed = 10; 
    private int frameCounter = 0;

    //tracks how many cycles of animation has been completed
    private int cyclesDone = 0;
    //numbers of cycle animation it must complete 
    private int totalCycles = 3;

    public ScanAnimation()
    {
        //load scan frames
        scans = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            scans[i] = new GreenfootImage("scan0" + (i+1) + ".png");
            scans[i].scale(200, 300); //scale
        }

        //start on first frame
        setImage(scans[0]);
    }

    public void act()
    {
        //run animation every frame
        animateScan();
    }

    private void animateScan()
    {
        //count time between frame changes
        frameCounter++;

        if (frameCounter >= scanFrameSpeed) {
            frameCounter = 0;
            frameIndex++;

            //restart animation cycle
            if (frameIndex >= scans.length) {
                frameIndex = 0;
                cyclesDone++;

                //remove object after finishing cycles
                if (cyclesDone >= totalCycles) {
                    getWorld().removeObject(this);
                    ((IntroWorld)getWorld()).doneScanning = true;
                    return;
                }
            }

            //update displayed frame
            setImage(scans[frameIndex]);
        }
    }
}

