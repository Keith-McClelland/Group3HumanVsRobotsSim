import greenfoot.*;

public class ArrowButton extends Actor
{
    // true  = arrow points right (increase value)
    // false = arrow points left  (decrease value)
    private boolean right;
    
    // The ValueBox this arrow will modify
    private ValueBox targetBox;

    // Creates the image of the arrow
    public ArrowButton(boolean right, ValueBox targetBox) {
        this.right = right;
        this.targetBox = targetBox;

        GreenfootImage img = new GreenfootImage(40, 40);
        img.setColor(Color.WHITE);

        int[] xPoints, yPoints;
        // If arrow points right, then the triangle will be drawn facing right
        if (right) {
            xPoints = new int[]{10, 30, 10};
            yPoints = new int[]{5, 20, 35};
        } else { // Arrow points left, then triangle is drawn facing left
            xPoints = new int[]{30, 10, 30};
            yPoints = new int[]{5, 20, 35};
        }

        img.fillPolygon(xPoints, yPoints, 3);
        setImage(img);
    }
    
    
    // Checks for if the arrow is clicked by the mouse
    // If the left arrow is clicked then it will decrement the value in the box
    // If the right arrow is clicked then it will increment the value in the box
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (targetBox != null) {
                if (right) {
                    targetBox.increment();
                } else {
                    targetBox.decrement();
                }
            }
        }
    }
}