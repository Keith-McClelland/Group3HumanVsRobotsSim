import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * ContinueButton is a Greenfoot Actor that represents simple clickable UI element used in menus or transition screens. 
 * <p>
 * It displays a scaled image (button.jpg) that acts as a button. 
 * Other classes (such as IntroWorld) detect when this button is clicked 
 * using Greenfoot.mouseClicked() and then perform actions such as switching worlds. 
 * <p>
 * This class only handles how the button looks — not what happens when it is clicked. 
 * 
 * @author Veznu Premathas 
 * @version November 2025
 */
public class ContinueButton extends Actor
{
    //place holder for the continue button
    GreenfootImage button = new GreenfootImage("button.jpg");
    /**
     * Constructor for ContinueButton that creates a ContinueButton and scales the button image to a readable size 
     * (300x100), then sets it as the actor's image. 
     */
    public ContinueButton()
    {
        button.scale(300,100);
        setImage(button);
    }
}