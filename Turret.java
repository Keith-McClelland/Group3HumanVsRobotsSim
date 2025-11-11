import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Turret extends SuperSmoothMover
{
    GreenfootImage turret = new GreenfootImage("images/turret.png");
    private long lastShotTime = 0;
    private long cooldown = 2000;
    
    public Turret()
    {
        turret.scale(100,200);
        turret.mirrorHorizontally();
        setImage(turret);
    }
    
    public void act()
    {
        if (System.currentTimeMillis() - lastShotTime >= cooldown) {
            shoot();
            lastShotTime = System.currentTimeMillis();
        }
    }
    
    private void shoot()
    {
        Projectile b = new Projectile(1);                 // create a Bullet object
        getWorld().addObject(b, getX()+60, getY()-43);
    }
}
