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

    int humanLevel = MyWorld.getHumanLevel();
    int robotLevel = MyWorld.getRobotLevel();
    if (isLeftSide) {
        text =
            "ROBOT CASH:    " + Units.robotCash + "\n\n" +
            "ROBOTS ALIVE:  " + Units.robotsAlive + "\n\n" +
            "ROBOTS DEAD:   " + Units.robotsDead + "\n\n" +
            "STAGE:         " + robotLevel;
    } else {
        text =
            "HUMAN CASH:    " + Units.humanCash + "\n\n" +
            "HUMANS ALIVE:  " + Units.humansAlive + "\n\n" +
            "HUMANS DEAD:   " + Units.humansDead + "\n\n" +
            "STAGE:         " + humanLevel;
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

