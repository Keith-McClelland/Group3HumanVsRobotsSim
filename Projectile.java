import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Projectile extends SuperSmoothMover
{
    //speed for the projectile 
    private double speed = 0;

    //working on range(dissapears at certain range)
    private int timer = 0;
    private int transparency = 255;
    
    //image for the projectile
    GreenfootImage laser = new GreenfootImage("images/laser.png");
    public Projectile(double speed)
    {
        this.speed = speed;
    }
    
    public void act()
    {
        if(getWorld() == null)
        {
            return;
        }
        timer++;
        move();
        checkCollision();
        //dissapear();
    }
    
    private void dissapear()
    {
        if(timer %  2 == 0)
        {
            if(transparency < 0)
            {
                transparency = 0;
            }
            else if(transparency > 0)
            {
                speed += .5;
                transparency -= 7;
        
            }
        }
        
        if(transparency <= 0)
        {
            getWorld().removeObject(this);
        }
        else 
        {
            getImage().setTransparency(transparency);
        }
    
    }
    
    private void move()
    {
        if(timer %  20 == 0 )
        {
            speed += 1.5;
        }
        
        setLocation(getX() + speed,getY());
        
    }
    
    private void checkCollision()
    {
        //InfantryMen menHit = (InfantryMen)getOneIntersectingObject(InfantryMen.class);
        Fences buildingHit = (Fences)getOneIntersectingObject(Fences.class);
        
        if(buildingHit != null)
        {
            getWorld().removeObject(this);
            buildingHit.damage(500);
        }
    }
    
    
}
