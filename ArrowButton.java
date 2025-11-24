import greenfoot.*;

public class ArrowButton extends Actor
{
    private boolean right; // true = increase, false = decrease
    private ValueBox targetBox;

    public ArrowButton(boolean right, ValueBox targetBox) {
        this.right = right;
        this.targetBox = targetBox;

        GreenfootImage img = new GreenfootImage(40, 40);
        img.setColor(Color.WHITE);

        int[] xPoints, yPoints;
        if (right) {
            xPoints = new int[]{10, 30, 10};
            yPoints = new int[]{5, 20, 35};
        } else {
            xPoints = new int[]{30, 10, 30};
            yPoints = new int[]{5, 20, 35};
        }

        img.fillPolygon(xPoints, yPoints, 3);
        setImage(img);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (targetBox != null) {
                if (right) {
                    targetBox.increment();
                } else {
                    targetBox.decrement();
                }
            }
        }
    }
}