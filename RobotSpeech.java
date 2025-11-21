import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class RobotSpeech extends Actor
{
    GreenfootImage speech1 = new GreenfootImage("robotSpeech1.png");
    GreenfootImage speech2 = new GreenfootImage("robotSpeech2.png");
    GreenfootImage speech3 = new GreenfootImage("robotSpeech4.png");
    
    public RobotSpeech()
    {
        speech1.scale(250,170); 
        speech2.scale(280,170);
        speech3.scale(280,170);
        setImage(speech1);
    }
    
    public void showSpeech1() { setImage(speech1); }
    public void showSpeech2() { setImage(speech2); }
    public void showSpeech3() { setImage(speech3); }
    
}
