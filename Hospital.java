public class Hospital extends Buildings
{
    private int healAmount = 10;
    private int cooldown = 30;
    private int timer = 0;

    public Hospital(boolean isHumanSide) {
        super(300, isHumanSide);
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
