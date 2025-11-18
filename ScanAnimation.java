import greenfoot.*;

public class ScanAnimation extends SuperSmoothMover
{
    private GreenfootImage[] scans;
    private int frameIndex = 0;
    private int scanFrameSpeed = 10; // slower animation
    private int frameCounter = 0;

    private int cyclesDone = 0;
    private int totalCycles = 3;

    public ScanAnimation()
    {
        scans = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            scans[i] = new GreenfootImage("scan0" + (i+1) + ".png");
            scans[i].scale(200, 300);
        }

        setImage(scans[0]);
    }

    public void act()
    {
        animateScan();
    }

    private void animateScan()
    {
        frameCounter++;

        if (frameCounter >= scanFrameSpeed) {
            frameCounter = 0;
            frameIndex++;

            if (frameIndex >= scans.length) {
                frameIndex = 0;
                cyclesDone++;

                if (cyclesDone >= totalCycles) {
                    getWorld().removeObject(this); // destroy after 2 cycles
                    return;
                }
            }
            setImage(scans[frameIndex]);
        }
    }
}
