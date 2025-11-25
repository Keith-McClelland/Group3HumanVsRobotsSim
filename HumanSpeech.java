import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * HumanSpeech is a Greenfoot Actor that represents a speech bubble or message popup used by human units 
 * or during the IntroWorld where the story begins. 
 * <p>
 * This actor simply displays the image humanSpeech1.png, which is scaled 
 * to 300×160 pixels. Other classes can add or remove this actor 
 * to show temporary speech messages on screen. 
 * <p>
 * This class handles only the visual appearance of the speech bubble. 
 * 
 * @author Veznu Premathas
 * @version November 2025
 */
public class HumanSpeech extends Actor
{
    //place holder for human speech 
    GreenfootImage speech1 = new GreenfootImage("humanSpeech1.png");
    /**
     * Constructor for HumanSpeech that creates a HumanSpeech object and scales the speech bubble image to 
     * 300x100 before setting it as the actor's appearance. 
     */
    public HumanSpeech()
    {
        speech1.scale(300,160); 
        setImage(speech1);
    }
}