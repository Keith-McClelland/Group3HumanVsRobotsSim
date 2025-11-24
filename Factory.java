import greenfoot.*;

public class Factory extends Buildings {

    private boolean doorCreated = false;
    private int repairAmount = 5;
    private int cooldown = 50;
    private int timer = 0;

    public Factory() {
        super(200);

        // set image
        GreenfootImage img = new GreenfootImage("factory.png");
        img.scale(300, 200);
        setImage(img);
    }

    public void act() {
        timer++;

        // create door on first act
        if (!doorCreated) {
            getWorld().addObject(new FactoryDoor(), getX() + 30, getY() + 50);
            doorCreated = true;
        }

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

