import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ValueBox extends Actor
{
    /**
     * Act - do whatever the ValueBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public ValueBox() {
        GreenfootImage img = new GreenfootImage(140, 40);
        img.setColor(Color.WHITE);
        img.fillRect(0, 0, 140, 40);

        img.setColor(Color.BLACK);
        img.setFont(new Font(true, false, 24));
        img.drawString("100", 50, 28); // placeholder value

        setImage(img);
    }
}
