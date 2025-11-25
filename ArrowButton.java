import greenfoot.*;
/**
 * SettingsWorld is a Greenfoot Actor that represents a clickable arrow in the settings menu. 
 * Each arrow is linked to a ValueBox, and clicking it will either increase 
 * or decrease the value displayed inside that box. 
 * <p>
 * Clicking the left arrow button will decrease the value inside the middle box by 1. 
 * Clicking the right arrow button will increase the value inside the middle box by 1. 
 * 
 * The arrow is drawn as a simple filled triangle using a GreenfootImage. 
 * <p>
 * This ArrowButton is used in the SettingsWorld to modify gameplay settings before 
 * the simulation starts. 
 * @author Jonathan Shi 
 * @version November 2025
 */
public class ArrowButton extends Actor
{
    // true  = arrow points right (increase value)
    // false = arrow points left  (decrease value)
    private boolean right;
    
    // The ValueBox this arrow will modify
    private ValueBox targetBox;
    /**
     * Constructor for ArrowButton that creates an arrow button that points either left or right, 
     * and attaches it to the ValueBox. 
     *
     * @param right    true if the arrow points right (increase value), 
     *                 false if the arrow points left (decrease value),
     *
     * @param targetBox     the ValueBox to modify when the arrow is clicked
     */
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
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Detects when this arrow is clicked, and updates the attatched ValueBox. 
     */
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