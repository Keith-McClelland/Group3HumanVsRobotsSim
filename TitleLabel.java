import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TitleLabel extends Actor
{
    public TitleLabel(String text, int size)
    {
        GreenfootImage img = new GreenfootImage(text, size, Color.WHITE, null);
        setImage(img);
    }
}
