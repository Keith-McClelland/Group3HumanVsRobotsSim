import greenfoot.GreenfootImage;
import greenfoot.Actor;
import greenfoot.Font;

public class ValueBox extends Actor {
    private int value;
    private String property; // e.g. "humanHP", "robotSpeed"

    public ValueBox(String property) {
        this.property = property;
        // Initialize value from GameSettings
        switch(property) {
            case "humanHP": value = GameSettings.humanHP; break;
            case "humanSpeed": value = (int)(GameSettings.humanSpeed * 10); break; // scale to int for display
            case "humanSpawnRate": value = GameSettings.humanSpawnRate; break;
            case "humanCash": value = GameSettings.humanCashPerKill; break;
            case "robotHP": value = GameSettings.robotHP; break;
            case "robotSpeed": value = (int)(GameSettings.robotSpeed * 10); break;
            case "robotSpawnRate": value = GameSettings.robotSpawnRate; break;
            case "robotCash": value = GameSettings.robotCashPerKill; break;
        }
        updateImage();
    }

    public void increment() {
        value += 1; // increment by 1
        updateImage();
        updateSetting();
    }

    public void decrement() {
        value -= 1; // decrement by 1
        if (value < 0) value = 0;
        updateImage();
        updateSetting();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(140, 40);
        img.setColor(greenfoot.Color.WHITE);  // background
        img.fillRect(0, 0, 140, 40);
        img.setColor(greenfoot.Color.BLACK);  // text
        img.setFont(new Font(true, false, 24));
    
        String displayText;
        if (property.equals("humanSpeed") || property.equals("robotSpeed")) {
            displayText = String.format("%.1f", value / 10.0);  // show as 1 decimal
        } else {
            displayText = String.valueOf(value);
        }
    
        img.drawString(displayText, 50, 28);
        setImage(img);
    }

    private void updateSetting() {
         switch(property) {
            case "humanHP": GameSettings.humanHP = value; break;
            case "humanSpeed": GameSettings.humanSpeed = value / 10.0; break; // convert back to double
            case "humanSpawnRate": GameSettings.humanSpawnRate = value; break;
            case "humanCash": GameSettings.humanCashPerKill = value; break;
            case "robotHP": GameSettings.robotHP = value; break;
            case "robotSpeed": GameSettings.robotSpeed = value / 10.0; break; // convert back to double
            case "robotSpawnRate": GameSettings.robotSpawnRate = value; break;
            case "robotCash": GameSettings.robotCashPerKill = value; break;
        }
    }

    public int getValue() {
        return value;
    }
}