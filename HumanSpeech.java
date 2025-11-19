import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class HumanSpeech extends Actor
{
    GreenfootImage speech1 = new GreenfootImage("humanSpeech1.png");
    GreenfootImage speech2 = new GreenfootImage("humanSpeech2.png");

    public HumanSpeech()
    {

        speech1.scale(290,170); 
        speech2.scale(290,170);
        setImage(speech1);
    }
    
    public void act()
    {
        
    }
    
    public void showHuman1() { setImage(speech1); }
    public void showHuman2() { setImage(speech2); }

}
