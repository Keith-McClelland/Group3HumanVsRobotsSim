import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StatBoard extends SuperSmoothMover
{
    //represents an image for both humans and robot 
    public StatBoard(String type)
    {
        //depending on the type it will spawn a display board accordingly
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
