import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Factory extends Buildings
{
    GreenfootImage factory = new GreenfootImage("factory.png");
    private boolean doorCreated = false;
    private int repairAmount = 5;
    private int cooldown = 50;    
    private int timer = 0;
    
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
        
        if (timer >= cooldown) 
        {
            repairRobots();
            timer = 0;
        }
    }
    
    private void repairRobots() {
        if (getWorld() == null) return;
        for (Robot r : getWorld().getObjects(Robot.class)) {
            if (r.getHealth() < r.maxHealth) {
                int newHealth = Math.min(r.getHealth() + repairAmount, r.maxHealth);
                r.setHealth(newHealth);
            }
        }
    }

}
