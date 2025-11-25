import greenfoot.*;
import greenfoot.Color;
/**
 * SmallLabel is a Greenfoot Actor used to display statistics about the 
 * Human and Robot teams during gameplay. This includes each side's cash, number 
 * of units alive, and number of units dead. 
 * <p>
 * Two objects of this class are created — one for Humans and one for Robots. 
 * The boolean value passed to the constructor determines which side’s data is shown. 
 * <p>
 * The HUD updates every frame, grabbing all its information from static data stored in the Units class. 
 * The text is using the "ARCADECLASSIC" font. 
 * 
 * @author Veznu Premathas 
 * @version November 2025 
 */
public class StatsHUD extends Actor 
{
    //place holder for the stats board representing each side money, number 
    //of people alive and dead
    private boolean isLeftSide; //dictates what text should be displayed
    private Font arcadeFont;

    /**
     * Constructor for StatsHUD that creates a StatsHUD panel.
     *
     * @param isLeftSide    If true, displays robot statistics.  
     *                      If false, displays human statistics. 
     */
    public StatsHUD(boolean isLeftSide) 
    {
        this.isLeftSide = isLeftSide;

        // loads ARCADECLASSIC font 
        arcadeFont = new Font("ARCADECLASSIC.ttf", false, false, 10);
        updateStats(); //updates the value as it constantly changes
    }

    /**
     * The act method is called repeatedly by Greenfoot. 
     * Continuously updates the HUD every frame to reflect changing game values.
     */
    public void act() 
    {
        updateStats();
    }

    /**
     * Rebuilds the HUD panel image, pulling live values from the Units class.
     * This method draws the texts depending on whether the HUD is displaying
     * Human or Robot statistics.
     */
    private void updateStats() 
    {

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

