import greenfoot.*;

public class Projectile extends SuperSmoothMover
{
    //controls movement / damage info
    private double speed;
    private double angle;
    private int damage;

    //who fired this projectile (ensures it only damages the oppostion side)
    public enum Owner { HUMAN, ROBOT, CANON }
    private Owner owner;

    public Projectile(double speed, double angle, int damage, Owner owner)
    {
        this.speed = speed;
        this.angle = angle;
        this.damage = damage;
        this.owner = owner;

        //face direction of travel
        setRotation((int)angle);

        /*
        //set correct image based on owner
        if (owner == Owner.HUMAN) 
        {
            setImage("laser.png");
        }
        else if (owner == Owner.ROBOT) 
        {
            setImage("ray.png");
        }
        else 
        {
            setImage("canonBall.png");
        }*/
    }

    public void act()
    {
        if (getWorld() == null) return;

        moveProjectile();
        checkCollision();
        checkEdges();
    }

    //moves the projectile depending on angle/speed 
    //(info is given by object shot from)
    private void moveProjectile()
    {
        double rad = Math.toRadians(angle);
        double dx = Math.cos(rad) * speed;
        double dy = Math.sin(rad) * speed;
        setLocation(getX() + dx, getY() + dy);
    }

    //handles all hit detection
    private void checkCollision()
    {
        if (getWorld() == null) return;

        //all projectiles can hit fences
        Fences fence = (Fences)getOneIntersectingObject(Fences.class);
        if (fence != null) {
            fence.damage(damage);
            removeBullet();
            return;
        }

        //robot bullets can damage humans
        if (owner == Owner.ROBOT) {
            Human human = (Human)getOneIntersectingObject(Human.class);
            if (human != null) {
                human.takeDamage(damage);
                removeBullet();
            }
        }

        //human + canon bullets can damage robots
        if (owner == Owner.HUMAN || owner == Owner.CANON) {
            Robot robot = (Robot)getOneIntersectingObject(Robot.class);
            if (robot != null) {
                robot.takeDamage(damage);
                removeBullet();
            }
        }
    }

    //removes bullet when leaving the world bounds
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

    //safe removal helper
    private void removeBullet()
    {
        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
