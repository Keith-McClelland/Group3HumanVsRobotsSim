import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Factory extends Buildings
{
    GreenfootImage factory = new GreenfootImage("factory.png");
    private boolean doorCreated = false;
    
    public Factory()
    {
        super(200);
        factory.scale(300,200);
        setImage(factory);
        
   
        
    }
    
    
    public void act()
    {
        if(!doorCreated)
        {
            
            
            
            FactoryDoor factorydoor = new FactoryDoor();
            getWorld().addObject(factorydoor,getX() + 30,getY() + 50);
            doorCreated = true;
        }
    }
}
