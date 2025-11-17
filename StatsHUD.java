import greenfoot.*;
import greenfoot.Color;

public class StatsHUD extends Actor {

    private boolean isLeftSide; 
    private Font arcadeFont;

    public StatsHUD(boolean isLeftSide) {
        this.isLeftSide = isLeftSide;

        // Load ARCADECLASSIC font (the only Greenfoot supported way)
        arcadeFont = new Font("ARCADECLASSIC.ttf", false, false, 10);

        updateStats();
    }

    public void act() {
        updateStats();
    }

    private void updateStats() {

        String text;

        if (isLeftSide) {
            text =
                "ROBOTS ALIVE:  " + Units.robotsAlive + "\n" + "\n"+ "\n"+
                "ROBOTS DEAD:   " + Units.robotsDead + "\n" + "\n"+ "\n"+
                "ROBOT CASH:    " + Units.robotCash;
        } else {
            text =
                "HUMANS ALIVE:  " + Units.humansAlive + "\n" + "\n"+ "\n"+
                "HUMANS DEAD:   " + Units.humansDead + "\n" + "\n"+ "\n"+
                "HUMAN CASH:    " + Units.humanCash;
        }

        // Panel size
        GreenfootImage img = new GreenfootImage(140, 200);

        // Background panel
        img.setColor(new Color(0, 0, 0, 0));
        img.fillRect(0, 0, img.getWidth(), img.getHeight());

        // Text
        img.setColor(Color.WHITE);
        img.setFont(arcadeFont);

        img.drawString(text, 15, 40);

        setImage(img);
    }
}

