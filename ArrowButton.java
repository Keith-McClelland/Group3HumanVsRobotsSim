import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class ArrowButton extends Actor
{
    
    public ArrowButton(boolean right) {
        GreenfootImage img = new GreenfootImage(40, 40);
        img.setColor(Color.WHITE);

        int[] xPoints, yPoints;
        
        // Triangle arrow facing right otherwise it faces left
        if (right) {
            xPoints = new int[]{10, 30, 10};
            yPoints = new int[]{5, 20, 35};
        } else {
            xPoints = new int[]{30, 10, 30};
            yPoints = new int[]{5, 20, 35};
        }

        img.fillPolygon(xPoints, yPoints, 3);
        setImage(img);
    }
}