import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * SmallLabel is a Greenfoot Actor used for displaying short text labels 
 * in the settings menu. It creates a simple text image using GreenfootImage. 
 * <p>
 * This class is used in settings menus, including SettingsWorld, to label 
 * each row such as "Spawn rate", "Starting HP", "Speed", and "Cash gained per kill". 
 * 
 * @author Jonathan Shi 
 * @version November 2025 
 */
public class SmallLabel extends Actor
{
    /**
     * Constructor for SmallLabel that creates a SmallLabel object containing the given text. 
     *
     * @param text  The text to display in the label. 
     */
    public SmallLabel(String text)
    {
        //place holder for small text
        GreenfootImage img = new GreenfootImage(text, 24, Color.WHITE, null);
        setImage(img);
    }
}
