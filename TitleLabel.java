import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * TitleLabel is a simple Actor that displays a text label on the screen. 
 * <p>
 * The text is displayed as an image using GreenfootImage with a specified size and color. 
 * This class is mainly used for titles in the world. 
 * 
 * @author Jonathan Shi
 * @version November 2025
 */
public class TitleLabel extends Actor
{
    /**
     * Constructs a new TitleLabel. 
     *
     * @param text The string to display. 
     * @param size The font size of the text. 
     */
    //acts as a placeholder 
    public TitleLabel(String text, int size)
    {
        GreenfootImage img = new GreenfootImage(text, size, Color.WHITE, null);
        setImage(img);
    }
}