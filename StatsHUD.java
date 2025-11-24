import greenfoot.*;
import greenfoot.Color;

public class StatsHUD extends Actor {
    //place holder for the stats board representing each side money, number 
    //of people alive and dead
    private boolean isLeftSide; //dictates what text should be displayed
    private Font arcadeFont;

    public StatsHUD(boolean isLeftSide) {
        this.isLeftSide = isLeftSide;

        // loads ARCADECLASSIC font 
        arcadeFont = new Font("ARCADECLASSIC.ttf", false, false, 10);
        updateStats(); //updates the value as it constantly changes
    }

    public void act() {
        updateStats();
    }

    private void updateStats() {

        String text;

        if (isLeftSide) {
            text =
                "ROBOT CASH:    " + Units.robotCash + "\n" + "\n"+ "\n"+
                "ROBOTS ALIVE:  " + Units.robotsAlive + "\n" + "\n"+ "\n"+
                "ROBOTS DEAD:   " + Units.robotsDead;
        } else {
            text =
                "HUMAN CASH:    " + Units.humanCash + "\n" + "\n"+ "\n"+
                "HUMANS ALIVE:  " + Units.humansAlive + "\n" + "\n"+ "\n"+
                "HUMANS DEAD:   " + Units.humansDead;
        }

        // panel size
        GreenfootImage img = new GreenfootImage(140, 200);

        // background panel (clear)
        img.setColor(new Color(0, 0, 0, 0));
        img.fillRect(0, 0, img.getWidth(), img.getHeight());

    
        img.setColor(Color.WHITE);
        img.setFont(arcadeFont);
        img.drawString(text, 15, 40);
        setImage(img);
    }
}

