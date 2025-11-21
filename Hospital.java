import greenfoot.*;

public class Hospital extends Buildings
{
    private int healAmount = 10;
    private int cooldown = 30;
    private int timer = 0;

    GreenfootImage hospital = new GreenfootImage("hospital.png");
    
    public Hospital() {
        super(300);
        setImage(hospital);
    }
    
    public void act() {
        timer++;
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
