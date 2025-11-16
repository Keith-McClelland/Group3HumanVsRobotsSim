import greenfoot.*;

public class Projectile extends SuperSmoothMover
{
    private double speed;
    private double angle;
    private int damage;
    private Owner owner; // Who fired the projectile

    public enum Owner { HUMAN, ROBOT, CANON }

    public Projectile(double speed, double angle, int damage, Owner owner)
    {
        this.speed = speed;
        this.angle = angle;
        this.damage = damage;
        this.owner = owner;

        setRotation((int)angle);
        
        if (owner == Owner.HUMAN) {
            setImage("laser.png");
        } 
        else if (owner == Owner.ROBOT) {
            setImage("ray.png");
        } 
        else if (owner == Owner.CANON) {
            setImage("canonBall.png");
        }
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

        // All projectiles hit fences
        Fences fenceHit = (Fences)getOneIntersectingObject(Fences.class);
        if (fenceHit != null) {
            fenceHit.damage(damage);
            removeSelf();
            return;
        }

        // Humans are hit only by Robot projectiles
        if (owner == Owner.ROBOT) {
            Human humanHit = (Human)getOneIntersectingObject(Human.class);
            if (humanHit != null) {
                humanHit.takeDamage(damage);
                removeSelf();
                return;
            }
        }

        // Robots are hit only by Human projectiles
        if (owner == Owner.HUMAN || owner == Owner.CANON) {
            Robot robotHit = (Robot)getOneIntersectingObject(Robot.class);
            if (robotHit != null) {
                robotHit.takeDamage(damage);
                removeSelf();
            }
        }
    }

    private void checkEdges()
    {
        if (getWorld() == null) return;
    
        int x = getX();
        int y = getY();
        int width = getWorld().getWidth();
        int height = getWorld().getHeight();
    
        int margin = 5; // buffer in pixels
    
        if (x < margin || x > width - margin || y < 175 || y > height - margin) {
            removeSelf();
        }
    }


    private void removeSelf()
    {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
}
