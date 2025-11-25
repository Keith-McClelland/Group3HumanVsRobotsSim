import greenfoot.*;
/**
 * Projectile represents a moving projectile fired by a unit in the world. 
 * <p>
 * Projectiles move at a given speed and angle, deal damage to targets, 
 * and interact with both Fences and opposing units depending on the owner. 
 *
 * @author Veznu Premathas
 * @version November 2025 
 */
public class Projectile extends SuperSmoothMover
{
    //controls movement / damage info
    private double speed;
    private double angle;
    private int damage;
    
    /**
     * Enum representing who fired the projectile. 
     * Determines which targets it can damage. 
     */
    public enum Owner { HUMAN, ROBOT }
    private Owner owner;

    /**
     * Constructor for Projectile that creates a new Projectile. 
     *
     * @param speed  Movement speed of the projectile. 
     * @param angle  Direction of movement. 
     * @param damage Amount of damage to deal on hit. 
     * @param owner  The entity that fired this projectile. 
     */
    public Projectile(double speed, double angle, int damage, Owner owner)
    {
        this.speed = speed;
        this.angle = angle;
        this.damage = damage;
        this.owner = owner;

        //face direction of travel
        setRotation((int)angle);
    }

    /**
     * The act method is called repeatedly by Greenfoot. 
     * Moves the projectile and checks for collision while also 
     * checking if it leaves the screen edges. 
     */
    public void act()
    {
        if (getWorld() == null) return;

        moveProjectile();
        checkCollision();
        checkEdges();
    }

    /**
     * Moves the projectile in the direction of its angle at its speed. 
     */ 
    private void moveProjectile()
    {
        double rad = Math.toRadians(angle);
        double dx = Math.cos(rad) * speed;
        double dy = Math.sin(rad) * speed;
        setLocation(getX() + dx, getY() + dy);
    }

    /**
     * Handles collision detection. 
     * Removes the projectile after collision. 
     */
    private void checkCollision()
    {
        if (getWorld() == null) return;

        //robot bullets can damage humans
        if (owner == Owner.ROBOT) {
            Human human = (Human)getOneIntersectingObject(Human.class);
            if (human != null) {
                human.takeDamage(damage);
                removeBullet();
            }
        }

        //human + canon bullets can damage robots
        if (owner == Owner.HUMAN) {
            Robot robot = (Robot)getOneIntersectingObject(Robot.class);
            if (robot != null) {
                robot.takeDamage(damage);
                removeBullet();
            }
        }
    }

    /**
     * Removes the projectile if it leaves the boundaries of the world. 
     */
    private void checkEdges()
    {
        if (getWorld() == null) return;

        int x = getX();
        int y = getY();
        int w = getWorld().getWidth();
        int h = getWorld().getHeight();

        int margin = 5;

        //top is limited (175) to prevent bullets going upward off-screen in your layout
        if (x < margin || x > w - margin || y < 175 || y > h - margin)
            removeBullet();
    }

    /**
     * Safely removes the projectile from the world. 
     */
    private void removeBullet()
    {
        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
