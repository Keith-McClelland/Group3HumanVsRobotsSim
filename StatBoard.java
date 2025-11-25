import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * StatBoard represents a visual scoreboard for a team in the world. 
 * <p>
 * It displays either a human or robot scoreboard. 
 * 
 * @author Veznu Premathas 
 * @version Novemrber 2025
 */
public class StatBoard extends SuperSmoothMover
{
    /**
     * Constructor for StatBoard that creates a StatBoard for the specified team type. 
     * The constructor loads the corresponding image and scales it to a fixed size. 
     * 
     * @param type      The type of scoreboard: "human" or any other string for robot. 
     */
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