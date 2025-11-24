import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Buildings extends SuperSmoothMover
{
    //variables that tracks:
    protected int maxHP; //inital damage it can withstand
    protected int health; //current damage it can withstand
    protected SuperStatBar healthBar;

    //tracks which side is the building on(heals or damages that certain side)
    protected boolean isHumanSide;

    //this constructor is used for turret + canon
    public Buildings(int maxHP, boolean isHumanSide) 
    {
        this.maxHP = maxHP;
        this.health = maxHP;
        this.isHumanSide = isHumanSide;
    }
    
    //this constructor is used for hospital and factory
    public Buildings(int maxHP) 
    {
        this.maxHP = maxHP;
        this.health = maxHP;
    }

    //returns if building belongs to human
    public boolean isHumanSide() 
    {
        return isHumanSide;
    }

    //returns if the building belongs to robot
    public boolean isRobotSide() 
    {
        return !isHumanSide;
    }

    //creates health bar for any buildings and adds to the world
    protected void addedToWorld(World world) 
    {
        healthBar = new SuperStatBar(maxHP, health, this, 40, 6, 30,Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20);
    }

    //returns the health of the building
    public int getHealth() 
    {
        return health; 
    }

    //changes the health of the building
    public void setHealth(int hp) 
    {
        this.health = hp;
        updateHealthBar();
    }

    //makes changes to health bar
    protected void updateHealthBar() 
    {
        if (healthBar != null) 
        {
            healthBar.update(health);
            if (getWorld() != null)
            {
                healthBar.setLocation(getX(), getY() - 20);
            }
        }
    }

    //if building is destroyed it would remove the healthbar
    protected void removeHealthBar() {
        if (healthBar != null && getWorld() != null) {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }

    //can be called by any object or character that wants to damage the 
    //building (remove if the building cannot withstand any more damage)
    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();

        if (health <= 0 && getWorld() != null) {
            removeHealthBar();
            getWorld().removeObject(this);
        }
    }
}
