import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SmallLabel extends Actor
{
    public SmallLabel(String text)
    {
        //place holder for small text
        GreenfootImage img = new GreenfootImage(text, 24, Color.WHITE, null);
        setImage(img);
    }
}

