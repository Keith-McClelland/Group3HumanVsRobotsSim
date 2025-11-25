import greenfoot.*;
import java.util.ArrayList;
/**
 * Units is an abstract class representing a fighting unit in the simulation. 
 * <p>
 * Units can be either human or robot, have health, speed, attack range, and damage. 
 * They interact with other units, projectiles, and the simulation world. 
 * Each unit maintains a health bar and can earn cash for the team when destroying enemies. 
 * 
 * @author Keith McClelland
 * @author Veznu Premathas
 * @version November 2025 
 */
public abstract class Units extends SuperSmoothMover {
    //keeps track of the units current and starting health 
    protected int health;
    protected int maxHealth;
    protected SuperStatBar healthBar;

    //keeps tracks of the units original and modified speed 
    protected double originalSpeed;
    protected double speed;

    //the range they search for enemies and the amount of damage they do 
    protected int range;
    protected int damage;

    //tracks if the units is robot 
    //the amount of each unit
    protected boolean isRobot;
    protected static int numUnits;
    
    public static int humansAlive = 0;
    public static int robotsAlive = 0;

    public static int humansDead = 0;
    public static int robotsDead = 0;

    //works on timing
    protected int delay;
    protected int cooldown;
    
    //manages the resource
    protected int value;
    protected static int humanCash = 0;
    protected static int robotCash = 0;


    public static final int MIN_PLAYABLE_Y = 175;
    /**
     * Constructor for Units that creates a new unit. 
     *
     * @param health Health points of the unit. 
     * @param speed Movement speed of the unit. 
     * @param range Attack range. 
     * @param damage Damage dealt per attack. 
     * @param delay Attack/movement delay. 
     * @param value Resource value when destroyed. 
     * @param isRobot True if the unit belongs to the robot team. 
     */
    public Units(int health, double speed, int range, int damage, int delay, int value, boolean isRobot) {

        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.delay = delay;
        this.value = value;
        this.isRobot = isRobot;
        numUnits++;
        this.originalSpeed = speed;

        // Track alive counts
        if (isRobot) robotsAlive++;
        else humansAlive++;
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Updates hp bar, checks for boundaries, and restricts them going out off the screen. 
     */
    public void act() {
        if (getWorld() == null) return;
        updateHealthBar();
        checkEdges();
        restrictPlayableArea();
    }
    
    /**
     * Called when unit is added to the world. 
     * Adds a health bar above the unit. 
     */
    protected void addedToWorld(World world) {
        healthBar = new SuperStatBar(maxHealth, health, this, 40, 6, 30,
                Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20);
    }
    
    /**
     * Adds cash to the human side.  
     */
     public static void addHumanCash(int amount) {
        humanCash += amount;
        if (humanCash < 0) humanCash = 0;
    }
    
    /**
     * Adds cash to the robot side. 
     */
    public static void addRobotCash(int amount) {
        robotCash += amount;
        if (robotCash < 0) robotCash = 0;
    }
    
    /**
     * Applies damage to the unit and handles death. 
     * <p>
     * Updates health bar and removes the unit from the world if health reaches 0. 
     *
     * @param dmg Damage to apply.  
     */
    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();

        if (health <= 0 && getWorld() != null) {

            // Count dead + give cash
            if (isRobot) {
                robotsAlive--;
                robotsDead++;
                humanCash += value;
            } else {
                humansAlive--;
                humansDead++;
                robotCash += value;
            }

            removeHealthBar();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Updates the health bar to reflect current health. 
     */
    protected void updateHealthBar() {
        if (healthBar != null) {
            healthBar.update(health);
            if (getWorld() != null)
                healthBar.setLocation(getX(), getY() - 20);
        }
    }
    
    /**
     * Removes the health bar from the world. 
     */
    protected void removeHealthBar() {
        if (healthBar != null && getWorld() != null) {
            getWorld().removeObject(healthBar);
            healthBar = null;
        }
    }
    
    /**
     * Checks world boundaries and triggers win conditions. 
     */
    protected void checkEdges() {
        if (getWorld() == null) return;

        int x = getX();
        int worldWidth = getWorld().getWidth();

        // Human reaches left edge, humans win
        if (!isRobot && x <= 5) {
            Greenfoot.setWorld(new EndSimWorld("Human"));
            MyWorld.stopMusic();
            return;
        }
    
 
        // Robot reaches right edge, robots win
        if (isRobot && x >= worldWidth - 5) {
            Greenfoot.setWorld(new EndSimWorld("Robots"));
            MyWorld.stopMusic();
            
            return;
        }
        
        // Cleanup only if NOT winning
        if (!isRobot && x < 5) { // Human cleanup
            removeHealthBar();
            getWorld().removeObject(this);
        }
    }



    /**
     * Restricts human units to a minimum vertical area. 
     */
    protected void restrictPlayableArea() {
        if (!isRobot && getWorld() != null) {
            int y = getY();
            int maxY = getWorld().getHeight() - 1;

            if (y < MIN_PLAYABLE_Y) setLocation(getX(), MIN_PLAYABLE_Y);
            if (y > maxY) setLocation(getX(), maxY);
        }
    }
    
    /**
     * Finds the closest human unit to this unit. 
     */
    protected Human getClosestHuman() {
        if (getWorld() == null) return null;

        ArrayList<Human> humans = new ArrayList<>(getWorld().getObjects(Human.class));
        Human closest = null;
        double minDist = Double.MAX_VALUE;

        for (Human h : humans) {
            double d = getDistanceTo(h);
            if (d < minDist) {
                minDist = d;
                closest = h;
            }
        }
        return closest;
    }
    
    /**
     * Finds the closest robot unit to this unit. 
     */
    protected Robot getClosestRobot() {
        if (getWorld() == null) return null;

        ArrayList<Robot> robots = new ArrayList<>(getWorld().getObjects(Robot.class));
        Robot closest = null;
        double minDist = Double.MAX_VALUE;

        for (Robot r : robots) {
            double d = getDistanceTo(r);
            if (d < minDist) {
                minDist = d;
                closest = r;
            }
        }
        return closest;
    }
    
    /**
     * Moves the unit toward a target Actor until within stopRange. 
     *
     * @param target The Actor to move toward. 
     * @param stopRange Distance to stop at. 
     */
        protected void moveToward(Actor target, double stopRange) {
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > stopRange) {
            setLocation(
                getX() + (int)(dx / distance * getSpeed()),
                getY() + (int)(dy / distance * getSpeed())
            );
        }
    }
    
    /**
     * Returns the distance to another Actor. 
     *
     * @param a Target Actor. 
     * @return Distance to the actor. 
     */
    protected double getDistanceTo(Actor a) {
        double dx = getPreciseX() - ((SuperSmoothMover)a).getPreciseX();
        double dy = getPreciseY() - ((SuperSmoothMover)a).getPreciseY();
        return Math.hypot(dx, dy);
    }
    
    
      /**
     * Setter method for healthbar.
     * @param hp    sets the health to hp.
     */
    public void setHealth(int hp) {
        this.health = hp;
        updateHealthBar();
    }
    
    /**
     * Getter method for hp.
     * @return    gets the health.
     */
    public int getHealth() { return health; }
    
    /**
     * Boolean method for determining team side.
     * @return    true or false if isRobot.  
     */
    public boolean isRobot() { return isRobot; }
    
    /**
     * Getter method for speed.
     * @return    gets the speed as a double.  
     */
    public double getSpeed() { return speed; }
    
    /**
     * Getter method for range.
     * @return    gets the range.
     */
    public int getRange() { return range; }
    
    /**
     * Getter method for human cash.
     * @return    gets the human cash.  
     */
    public static int getHumanCash() { return humanCash; }
    
    /**
     * Getter method for robot cash.
     * @return    gets the robot cash.
     */
    public static int getRobotCash() { return robotCash; }
    
    /**
     * Setter method for human cash.
     * @param a    sets the humanCash.
     */
    public static void setHumanCash(int a) { humanCash = a; }
    
    /**
     * Setter method for robot cash.
     * @param a    sets the robotCash.
     */
    public static void setRobotCash(int a) { robotCash = a; }
    
    /**
     * Setter method for determining how many robots are alive.
     * @param a    sets the robotsAlive.
     */
    public static void setRobotsAlive(int a) { robotsAlive = a; }
    
    /**
     * Setter method for determining how many robots are dead.
     * @param a    sets the robotsDead.
     */
    public static void setRobotsDead(int a) { robotsDead = a; }
    
    /**
     * Setter method for determining how many humans are alive.
     * @param a    sets the humansAlive.
     */
    public static void setHumansAlive(int a) { humansAlive = a; }
    
    /**
     * Setter method for determining how many humans are dead.
     * @param a    sets the humansDead.
     */
    public static void setHumansDead(int a) { humansDead = a; }
}



