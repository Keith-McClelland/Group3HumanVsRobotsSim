import greenfoot.*;
import java.util.ArrayList;
/**
 * TerritoryBar is a UI element displayed at the top of the screen. 
 * <p>
 * It visually represents the "territory control" of the human and robot sides. 
 * The bar shows: 
 * - Human-controlled territory in blue 
 * - Robot-controlled territory in red 
 * - Neutral territory in gray 
 * <p>
 * The bar updates every frame based on the furthest positions of humans and robots. 
 * 
 * @author Veznu Premathas
 * @version November 2025
 */
public class TerritoryBar extends Actor {
    //properties of the territory bar located on top of the screen
    private int barWidth = 400;
    private int barHeight = 20;
    private int borderThickness = 4;

    //color used for the territory bar
    private Color humanColor = Color.BLUE;
    private Color robotColor = Color.RED;
    private Color neutralColor = Color.GRAY;
    private Color borderColor = Color.BLACK;

    private GreenfootImage barImage;
    
    /**
     * Constructs a new TerritoryBar. 
     * Initializes the bar image and draws it in a neutral state. 
     */
    public TerritoryBar() {
        //  create blank image here
        barImage = new GreenfootImage(barWidth, barHeight);
        setImage(barImage);
        
        // initial drawing with neutral color
        drawNeutral();
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Updates the territory bar based on the current positions of humans and robots. 
     */
    public void act() {
        // reguarly update every frame as location of both sides change
        updateBar();
    }
    
    /**
     * Draws the bar in a neutral state. 
     * Fills the bar with the neutral color and draws the border. 
     */
    private void drawNeutral() {
        //draws the frame of the territory bar
        barImage.clear();
        barImage.setColor(neutralColor);
        barImage.fillRect(0, 0, barWidth, barHeight);
        barImage.setColor(borderColor);
        for (int i = 0; i < borderThickness; i++) {
            barImage.drawRect(i, i, barWidth - 1 - (i*2), barHeight - 1 - (i*2));
        }
        setImage(barImage);
    }
    
    /**
     * Updates the territory bar each frame. 
     * Calculates the positions of the furthest humans and robots and draws the bar. 
     */
    private void updateBar() {
        //if no bar return (null check ensuring there is no errors)
        if (getWorld() == null) return;

        //gets world length 
        int worldWidth = getWorld().getWidth();

        //finds the furthest human and robot 
        int humanX = getFurthestHumanX();
        int robotX = getFurthestRobotX();

        //convert world positions to bar pixels
        int humanPos = (int)((double)humanX / worldWidth * barWidth);
        int robotPos = (int)((double)robotX / worldWidth * barWidth);

        // draw bar
        barImage.clear();
        barImage.setColor(neutralColor);
        barImage.fillRect(0, 0, barWidth, barHeight);

        barImage.setColor(humanColor);
        barImage.fillRect(0, 0, humanPos, barHeight);

        barImage.setColor(robotColor);
        barImage.fillRect(robotPos, 0, barWidth - robotPos, barHeight);

        // creates the border
        barImage.setColor(borderColor);
        for (int i = 0; i < borderThickness; i++) {
            barImage.drawRect(i, i, barWidth - 1 - (i*2), barHeight - 1 - (i*2));
        }

        setImage(barImage);
    }
    
    /**
     * Finds the X-coordinate of the human unit closest to the robot side. 
     *
     * @return X-coordinate of the furthest human, or 0 if no humans exist. 
     */
    private int getFurthestHumanX() {
        //loops through list of all humans within the world and by process of
        //elimination, it will identify the human closest to the robot side
        ArrayList<Human> humans = new ArrayList<>( getWorld().getObjects(Human.class) );
        if (humans.isEmpty()) return 0;

        int x = getWorld().getWidth(); // humans start on right side
        for (Human h : humans) {
            if (h.getX() < x) 
            {
                x = h.getX();
            }
        }
        return x;
    }
    
    /**
     * Finds the X-coordinate of the robot unit closest to the human side. 
     *
     * @return X-coordinate of the furthest robot, or 0 if no robots exist. 
     */
    private int getFurthestRobotX() {
        //loops through list of all robot within the world and by process of
        //elimination, it will identify the human closest to the human side
        ArrayList<Robot> robots = new ArrayList<>( getWorld().getObjects(Robot.class) );
        if (robots.isEmpty()) 
        {
            return 0;
        }

        int x = 0;
        for (Robot r : robots) {
            if (r.getX() > x) 
            {
                x = r.getX();
            }       
        }
        return x;
    }
}
