import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class ScanAnimation extends SuperSmoothMover
{
    private GreenfootImage scan;
    private GreenfootImage[] scans;
    
    private int frameIndex = 0;
    private int scanFrameSpeed = 5; // acts per frame
    private int frameCounter = 0;
    
    public ScanAnimation()
    {
        scan = new GreenfootImage("scan01.png"); // start at 1 since you don't have 0
        scan.scale(45,90);
        setImage(scan);
    
        // Shooting animation frames 002–006
        scans = new GreenfootImage[6];
        for (int i = 0; i < 6; i++) {
            scans[i] = new GreenfootImage("scan0" + (i+1) + ".png"); // 002,003,004,005,006
            scans[i].scale(45,90);
        }
        
    }
    
    public void act()
    {
        animateRevolution();
    }
    
    private void animateRevolution() {
        setImage(scans[frameIndex]);
        frameCounter++;
        if (frameCounter >= scanFrameSpeed) {
            frameCounter = 0;
            frameIndex++;
            if (frameIndex >= scans.length) {
                frameIndex = 0;
                setImage(scan);
            }
        }
    }
}
