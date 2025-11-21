import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StatBoard extends SuperSmoothMover
{
    //represents an image for both humans and robot s
    public StatBoard(String type)
    {
        if(type == "human")
        {
            GreenfootImage statboard = new GreenfootImage("humanScoreBoard.png");
            statboard.scale(180,130);
            setImage(statboard);
        }
        else
        {
            GreenfootImage statboard = new GreenfootImage("robotScoreBoard.png");
            statboard.scale(180,130);
            setImage(statboard);
            
        }
    }

}
