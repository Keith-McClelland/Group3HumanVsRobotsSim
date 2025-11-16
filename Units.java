import greenfoot.*;
import java.util.ArrayList;

public abstract class Units extends SuperSmoothMover {
    //tracks properties of the army
    
    //amount of health and maxhealth is used to see if the being is about to 
    //die and helps to display infomation on the statbar
    protected int health;
    protected int maxHealth;
    protected SuperStatBar healthBar;
    
    //how fast the robot can move across world
    protected double originalSpeed;
    protected double speed;
    
    //damage it can inflict on enemy and the range it can attack at
    protected int range;
    protected int damage;
    
    //tracks if it is a robot(helps to idenitfy which being to attack)
    protected boolean isRobot;
    protected static int numUnits;

    //helps with the timing
    protected int delay;    
    protected int cooldown;
    
    //manages the resource
    protected int value;
    protected static int humanCash = 0;
    protected static int robotCash = 0;

    //limits the units where they can be
    public static final int MIN_PLAYABLE_Y = 175; // minimum y position 

    public Units(int health, double speed, int range, int damage, int delay, int value, boolean isRobot) {
        //assign values to variables 
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
    }

    
    protected void addedToWorld(World world) {
        // create hp bar for each unit
        healthBar = new SuperStatBar(maxHealth, health, this, 40, 6, 30,Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() - 20); // above the unit
    }

    //returns the amount of health the being has
    public int getHealth() {
        return health;
    }

    //sets the amount of health the being 
    public void setHealth(int hp) {
        this.health = hp;
        updateHealthBar();
    }

    //checks if the being is a robot
    public boolean isRobot() {
        return isRobot;
    }

    //returns speed of the being
    public double getSpeed() {
        return speed;
    }

    //returns the range of the being
    public int getRange() {
        return range;
    }

    //
    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();

        if (health <= 0 && getWorld() != null) {
            if (isRobot) humanCash += value;
            else robotCash += value;

            removeHealthBar();
            getWorld().removeObject(this);
        }
    }

    
    protected void updateHealthBar() {
        if (healthBar != null) {
            healthBar.update(health);
            if (getWorld() != null) {
                healthBar.setLocation(getX(), getY() - 20); // follow the unit
            }
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

    // Default movement if no target
    if (isRobot) {
        move(speed); // move right
    } else {
        move(-speed); // move left
    }

    updateHealthBar();
    checkEdges();
    restrictPlayableArea();
}


    
    protected void checkEdges() {
        if (getWorld() == null) return;

        int x = getX();
        int y = getY();
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

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

    protected double getDistanceTo(Actor a) {
        double dx = getPreciseX() - ((SuperSmoothMover) a).getPreciseX();
        double dy = getPreciseY() - ((SuperSmoothMover) a).getPreciseY();
        return Math.hypot(dx, dy);
    }

    public static int getHumanCash() { return humanCash; }
    public static int getRobotCash() { return robotCash; }
    public static void setHumanCash(int amount) { humanCash = amount; }
    public static void setRobotCash(int amount) { robotCash = amount; }
}
