import greenfoot.*;

public class Hospital extends Buildings
{
    private int healAmount = 5;
    private int cooldown = 50;
    private int timer = 0;

    private boolean doorConstructed = false;
    GreenfootImage hospital = new GreenfootImage("hospital.png");
    
    public Hospital() {
        super(300);
        setImage(hospital);
    }
    
    public void act() {
        timer++;
        
        if(!doorConstructed)
        {
            getWorld().addObject(new HospitalDoor(), getX(), getY()+20);
            doorConstructed = true;
        }
        if (timer >= cooldown) {
            healHumans();
            timer = 0;
        }
    }

    private void healHumans() {
        for (Human h : getWorld().getObjects(Human.class)) {
            if (h.getHealth() < h.maxHealth) {
                int newHealth = Math.min(h.getHealth() + healAmount, h.maxHealth);
                h.setHealth(newHealth);
            }
        }
    }
}
