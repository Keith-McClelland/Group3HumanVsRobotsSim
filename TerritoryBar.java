import greenfoot.*;
import java.util.List;

public class TerritoryBar extends Actor {
    
    //properties of the territory bar located on top of the screen
    private int barWidth = 400;
    private int barHeight = 20;
    private int borderThickness = 4;

    private Color humanColor = Color.BLUE;
    private Color robotColor = Color.RED;
    private Color neutralColor = Color.GRAY;
    private Color borderColor = Color.BLACK;

    private GreenfootImage barImage;

    public TerritoryBar() {
        //  create blank image here
        barImage = new GreenfootImage(barWidth, barHeight);
        setImage(barImage);
        
        // initial drawing with neutral color
        drawNeutral();
    }

    public void act() {
        // reguarly update every frame as location of both sides change
        updateBar();
    }

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

    private int getFurthestHumanX() {
        //loops through list of all humans within the world and by process of
        //elimination, it will identify the human closest to the robot side
        List<Human> humans = getWorld().getObjects(Human.class);
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

    private int getFurthestRobotX() {
        //loops through list of all robot within the world and by process of
        //elimination, it will identify the human closest to the human side
        List<Robot> robots = getWorld().getObjects(Robot.class);
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




