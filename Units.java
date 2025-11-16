import greenfoot.*;
import java.util.ArrayList;

public abstract class Units extends SuperSmoothMover {
    protected int health;
    protected int maxHealth;
    protected double speed;
    protected int damage;
    protected boolean isRobot;
    protected static int numUnits;
    protected int range;
    protected int delay;
    protected double originalSpeed;
    protected int cooldown;
    protected int value; 

    protected static int humanCash = 0;
    protected static int robotCash = 0;

    protected SuperStatBar healthBar;

    protected static final int MIN_PLAYABLE_Y = 175; // minimum Y for humans

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
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int hp) {
        this.health = hp;
        updateHealthBar();
    }

    public boolean isRobot() {
        return isRobot;
    }

    public double getSpeed() {
        return speed;
    }
    public int getRange(){
        return range;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();
        if (health <= 0 && getWorld() != null) {
            if (isRobot) humanCash += value;
            else robotCash += value;

            if (healthBar != null && getWorld() != null)
                getWorld().removeObject(healthBar);
            getWorld().removeObject(this);
        }
    }

    public void act() {
        if (isRobot) {
            move(speed);
        } else {
            move(-speed);
        }

        updateHealthBar();
        checkEdges(); // Remove if out of world bounds
        restrictPlayableArea(); // Restrict vertical position for humans
    }

    /** Keeps the health bar in sync with the unit’s current health */
    protected void updateHealthBar() {
        if (healthBar != null) {
            healthBar.update(health);
        }
    }

    protected void addedToWorld(World world) {
        healthBar = new SuperStatBar(maxHealth, health, this, 40, 6, 30,
                                     Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() + 30);
    }

    protected Human getClosestHuman() {
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
        double dx = getPreciseX() - ((SuperSmoothMover)a).getPreciseX();
        double dy = getPreciseY() - ((SuperSmoothMover)a).getPreciseY();
        return Math.hypot(dx, dy);
    }

    // --- EDGE CHECKING ---
    protected void checkEdges() {
        if (getWorld() == null) return;

        int x = getX();
        int y = getY();
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

        if (x < 5 || x >= worldWidth-5) {
            if (healthBar != null && getWorld() != null)
            {
                getWorld().removeObject(healthBar);
                getWorld().removeObject(this);
            }
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

    public static int getHumanCash() { return humanCash; }
    public static int getRobotCash() { return robotCash; }
    public static void setHumanCash(int amount) { humanCash = amount; }
    public static void setRobotCash(int amount) { robotCash = amount; }
}

