import greenfoot.*;  

public class Factory extends Buildings
{
    private GreenfootImage factory = new GreenfootImage("factory.png");
    private boolean doorCreated = false;
    private int repairAmount = 5;
    private int cooldown = 50;
    private int timer = 0;
    private FactoryDoor factoryDoor;


    public Factory() {
        super(400, false);

        // set image
        GreenfootImage img = new GreenfootImage("factory.png");
        img.scale(300, 200);
        setImage(img);
    }

    public void act()
    {
        timer++;

        if(!doorCreated)
        {   
            factoryDoor = new FactoryDoor(); // store reference
            getWorld().addObject(factoryDoor, getX() + 30, getY() + 50);
            doorCreated = true;
        }

        // heal robots every cooldown tick
        if (timer >= cooldown) {
            repairRobots();
            timer = 0;
        }
    }

    private void repairRobots() {
        // loop through robots and heal them
        for (Robot r : getWorld().getObjects(Robot.class)) {
            if (r.getHealth() < r.maxHealth) {
                r.setHealth(Math.min(r.getHealth() + repairAmount, r.maxHealth));
            }
        }
    }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(dmg);

        // Remove the door if factory dies
        if (health <= 0 && factoryDoor != null && factoryDoor.getWorld() != null) {
            getWorld().removeObject(factoryDoor);
            factoryDoor = null; // prevent double removal
        }
    }
}

