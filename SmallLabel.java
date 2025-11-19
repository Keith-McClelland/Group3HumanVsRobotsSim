import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class SmallLabel extends Actor
{
    public SmallLabel(String text)
    {
        GreenfootImage img = new GreenfootImage(text, 24, Color.WHITE, null);
        setImage(img);
    }
}

