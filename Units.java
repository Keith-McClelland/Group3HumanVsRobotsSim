import greenfoot.*;
import java.util.ArrayList;

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

    protected void addedToWorld(World world) {
        healthBar = new SuperStatBar(maxHealth, health, this, 40, 6, 30,
                Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20);
    }

     public static void addHumanCash(int amount) {
        humanCash += amount;
        if (humanCash < 0) humanCash = 0;
    }

    public static void addRobotCash(int amount) {
        robotCash += amount;
        if (robotCash < 0) robotCash = 0;
    }

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

    public void act() {
        if (getWorld() == null) return;

 

        updateHealthBar();
        checkEdges();
        restrictPlayableArea();
    }

    protected void checkEdges() {
        if (getWorld() == null) return;

        int x = getX();
        int worldWidth = getWorld().getWidth();

        // Human reaches left edge, humans win
        if (!isRobot && x <= 5) {
            Greenfoot.setWorld(new EndSimWorld("Human"));
            return;
        }
    
        // Robot reaches right edge, robots win
        if (isRobot && x >= getWorld().getWidth() - 1) {
            Greenfoot.setWorld(new EndSimWorld("Robots"));  
            return;
        }

    
        // Existing cleanup
        if (x < 5 || x >= worldWidth - 5) {
            removeHealthBar();
            getWorld().removeObject(this);
        }
    }




    // Restrict humans to playable vertical area
    protected void restrictPlayableArea() {
        if (!isRobot && getWorld() != null) {
            int y = getY();
            int maxY = getWorld().getHeight() - 1;

            if (y < MIN_PLAYABLE_Y) setLocation(getX(), MIN_PLAYABLE_Y);
            if (y > maxY) setLocation(getX(), maxY);
        }
    }

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

    protected double getDistanceTo(Actor a) {
        double dx = getPreciseX() - ((SuperSmoothMover)a).getPreciseX();
        double dy = getPreciseY() - ((SuperSmoothMover)a).getPreciseY();
        return Math.hypot(dx, dy);
    }
    //getters and setters 
    public void setHealth(int hp) {
        this.health = hp;
        updateHealthBar();
    }
    public int getHealth() { return health; }
    public boolean isRobot() { return isRobot; }
    public double getSpeed() { return speed; }
    public int getRange() { return range; }
    public static int getHumanCash() { return humanCash; }
    public static int getRobotCash() { return robotCash; }
    public static void setHumanCash(int a) { humanCash = a; }
    public static void setRobotCash(int a) { robotCash = a; }
    public static void setRobotsAlive(int a) { robotsAlive = a; }
    public static void setRobotsDead(int a) { robotsDead = a; }
    public static void setHumansAlive(int a) { humansAlive = a; }
    public static void setHumansDead(int a) { humansDead = a; }
}

