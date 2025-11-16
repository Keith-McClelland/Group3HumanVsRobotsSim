import greenfoot.*;

public class Projectile extends SuperSmoothMover
{
    private double speed;
    private double angle;
    private int damage;

    public Projectile(double speed, double angle, int damage)
    {
        this.speed = speed;
        this.angle = angle;
        this.damage = damage;

        setRotation((int)angle);
    }

    public void act()
    {
        if (getWorld() == null) return;

        moveProjectile();
        checkCollision();
        checkEdges();
    }

    private void moveProjectile()
    {
        double rad = Math.toRadians(angle);
        double dx = Math.cos(rad) * speed;
        double dy = Math.sin(rad) * speed;
        setLocation(getX() + dx, getY() + dy);
    }

    private void checkCollision()
    {
        if (getWorld() == null) return;

        // Check Fences
        Fences fenceHit = (Fences)getOneIntersectingObject(Fences.class);
        if (fenceHit != null)
        {
            fenceHit.damage(damage);
            removeSelf();
            return;
        }

        // Check Robots (any type)
        Robot robotHit = (Robot)getOneIntersectingObject(Robot.class);
        if (robotHit != null)
        {
            robotHit.takeDamage(damage);
            removeSelf();
        }
    }

    private void checkEdges()
    {
        if (getWorld() == null) return;

        if (getX() < 0 || getX() > getWorld().getWidth() ||
            getY() < 0 || getY() > getWorld().getHeight())
        {
            removeSelf();
        }
    }

    private void removeSelf()
    {
        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}



