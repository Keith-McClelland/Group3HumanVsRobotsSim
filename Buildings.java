import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Buildings is an abstract class that represents any stationary building placed on the battlefield, 
 * such as Cannons, Turrets, Hospitals, or Factories. Buildings may belong to either 
 * the Human or Robot faction and contain hit points that decrease when damaged. 
 * <p>
 * This abstract superclass provides shared functionality such as: 
 * - Maximum and current HP 
 * - A floating health bar that updates automatically 
 * - Damage-taking logic 
 * - Team identity (Human or Robot side) 
 *
 * <p>
 * All building subclasses must extend this class and call the constructor to 
 * initialize HP and ownership. 
 * 
 * @author Veznu Premathas
 * @author Jonathan Shi
 * @version November 2025
 */
public abstract class Buildings extends SuperSmoothMover
{
    //variables that tracks:
    protected int maxHP; //inital damage it can withstand
    protected int health; //current damage it can withstand
    protected SuperStatBar healthBar;

    //tracks which side is the building on(heals or damages that certain side)
    protected boolean isHumanSide;
    
    /**
     * Constructor for Buildings that creates a new building with the specified maximum HP and ownership side. 
     *
     * @param maxHP        the total health this building starts with 
     * @param isHumanSide  true if the building belongs to humans, false if it belongs to robots 
     */
    public Buildings(int maxHP, boolean isHumanSide) 
    {
        this.maxHP = maxHP;
        this.health = maxHP;
        this.isHumanSide = isHumanSide;
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Runs the behaviour to complete the buildings task. 
     */
    public void act()
    {
        completeTask();
    }
    
    /**
     * Returns whether this building belongs to the Human side. 
     *
     * @return true if the building is a Human building 
     */
    public boolean isHumanSide() 
    {
        return isHumanSide;
    }
    
    /**
     * Returns whether this building belongs to the Robot side. 
     *
     * @return true if the building is a Robot building 
     */
    public boolean isRobotSide() 
    {
        return !isHumanSide;
    }
    
    /**
     * Called automatically when the building is added to the world. 
     * Creates and attaches a SuperStatBar above the building to display its HP. 
     *
     * @param world the world this building was added to 
     */
    protected void addedToWorld(World world) 
    {
        healthBar = new SuperStatBar(maxHP, health, this, 40, 6, 30,Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20);
    }
    
    /**
     * Gets the current health of the building. 
     *
     * @return the building's current HP 
     */
    public int getHealth() 
    {
        return health; 
    }
    
    /**
     * Sets the building's health to a specific value and updates the health bar. 
     *
     * @param hp the new health value to assign 
     */
    public void setHealth(int hp) 
    {
        this.health = hp;
        updateHealthBar();
    }
    
    /**
     * Updates the health bar's visual value and position so it always follows the building. 
     */
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
    
    /**
     * Removes the floating health bar from the world when destroyed. 
     */
    protected void removeHealthBar() {
        if (healthBar != null && getWorld() != null) 
        {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }
    
    /**
     * Applies damage taken to the building. If HP reaches zero or below, the building 
     * removes itself and its health bar from the world. 
     *
     * @param dmg the amount of damage to apply
     */
    public void takeDamage(int dmg) 
    {
        health -= dmg;
        updateHealthBar();

        if (health <= 0 && getWorld() != null) 
        {
            removeHealthBar();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Makes the child class to implement it's own unique function 
     */
    public abstract void completeTask();
}