import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * RobotSpeech is a Greenfoot Actor that represents a speech bubble or message popup used by robot units 
 * or during the IntroWorld where the story begins. 
 * <p>
 * This actor simply displays the image robotSpeech1.png. Other classes can add or remove this actor 
 * to show temporary speech messages on screen. 
 * <p>
 * This class handles only the visual appearance of the speech bubble. 
 * 
 * @author veznu Premathas
 * @version November 2025
 */
public class RobotSpeech extends Actor
{
    //place holder for robot speeches 
    GreenfootImage speech1 = new GreenfootImage("robotSpeech1.png");
    GreenfootImage speech2 = new GreenfootImage("robotSpeech2.png");
    /**
     * Constructor for RobotSpeech that creates a RobotSpeech object and scales both available speech bubble images. 
     * The initial displayed bubble is speech1. 
     * Then the second displayed bubble is speech2. 
     */
    public RobotSpeech()
    {
        //scales the images
        speech1.scale(250,170); 
        speech2.scale(280,170);
        //sets the intial image
        setImage(speech1);
    }
    
    /**
     * Displays the first robot speech bubble image.
     */
    public void showSpeech1() 
    {
        setImage(speech1); 
    }
    
    /**
     * Displays the second robot speech bubble image.
     */
    public void showSpeech2() 
    { 
        setImage(speech2); 
    }
   
}
