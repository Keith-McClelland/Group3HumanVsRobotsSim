import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Buildings extends SuperSmoothMover
{
    protected int maxHP;
    protected int health;
    protected SuperStatBar healthBar;

    // NEW: team identifier
    protected boolean isHumanSide;

    // Updated constructor: require team side
    public Buildings(int maxHP, boolean isHumanSide) {
        this.maxHP = maxHP;
        this.health = maxHP;
        this.isHumanSide = isHumanSide;
    }
    
    public Buildings(int maxHP) {
        this.maxHP = maxHP;
        this.health = maxHP;
        this.isHumanSide = isHumanSide;
    }

    public boolean isHumanSide() {
        return isHumanSide;
    }

    public boolean isRobotSide() {
        return !isHumanSide;
    }

    @Override
    protected void addedToWorld(World world) {
        healthBar = new SuperStatBar(maxHP, health, this, 40, 6, 30,
                Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20);
    }

    public int getHealth() {
        return health; 
    }

    public void setHealth(int hp) {
        this.health = hp;
        updateHealthBar();
    }

    protected void updateHealthBar() {
        if (healthBar != null) {
            healthBar.update(health);
            if (getWorld() != null)
                healthBar.setLocation(getX(), getY() - 20);
        }
    }

    protected void removeHealthBar() {
        if (healthBar != null && getWorld() != null) {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();

        if (health <= 0 && getWorld() != null) {
            removeHealthBar();
            getWorld().removeObject(this);
        }
    }
}
