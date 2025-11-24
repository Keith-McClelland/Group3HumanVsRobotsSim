import greenfoot.*;  

public class Factory extends Buildings
{
    private GreenfootImage factory = new GreenfootImage("factory.png");
    private boolean doorCreated = false;
    private int repairAmount = 5;
    private int cooldown = 50;
    private int timer = 0;

    public Factory() {
        super(400, false);

        // set image
        GreenfootImage img = new GreenfootImage("factory.png");
        img.scale(300, 200);
        setImage(img);
    }

    public void act()
    {
        timer++;

        // heal robots every cooldown tick
        if (timer >= cooldown) {
            repairRobots();
            timer = 0;
        }
    }

    private void repairRobots() {
        // loop through robots and heal them
        for (Robot r : getWorld().getObjects(Robot.class)) {
            if (r.getHealth() < r.maxHealth) {
                r.setHealth(Math.min(r.getHealth() + repairAmount, r.maxHealth));
            }
        }
    }

}

