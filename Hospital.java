import greenfoot.*;

public class Hospital extends Buildings
{
    private int healAmount = 5;       // how much health to restore per tick
    private int cooldown = 50;        // ticks between healing
    private int timer = 0;            // counts up to cooldown

    private boolean doorConstructed = false;  // only spawn door once
    GreenfootImage hospital = new GreenfootImage("hospital.png");  // hospital image
    
    public Hospital() 
    {
        super(1000, true);   // max hp 1000, belongs to human side
        setImage(hospital);  // set hospital sprite
    }

    // main method controlling hospital actions
    public void completeTask() 
    {
        timer++;

        // spawn hospital door only once
        if(!doorConstructed)
        {
            getWorld().addObject(new HospitalDoor(), getX(), getY() + 20);
            doorConstructed = true;
        }

        // heal humans periodically
        if (timer >= cooldown) 
        {
            healHumans();  // restore health to humans
            timer = 0;     // reset timer
        }
    }

    // loop through humans and restore health
    private void healHumans() 
    {
        for (Human h : getWorld().getObjects(Human.class)) 
        {
            if (h.getHealth() < h.maxHealth) 
            {
                int newHealth = Math.min(h.getHealth() + healAmount, h.maxHealth);
                h.setHealth(newHealth);  // set capped health
            }
        }
    }
}

