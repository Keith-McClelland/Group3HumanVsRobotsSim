import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SettingsWorld extends World
{


    public SettingsWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1240, 700, 1);
        
        // Dark grey background
        GreenfootImage bg = new GreenfootImage(1240, 700);
        bg.setColor(new Color(45, 45, 45));
        bg.fill();
        setBackground(bg);
        
        // Settings menu components all combined
        setupLabels();
        setupHumanColumn();
        setupRobotColumn();
    }
    
    // Right side (Robot settings)
    private void setupRobotColumn() {
        int y = 200;
        
        // Creates four columns 
        for (int i = 0; i < 4; i++) {
            addObject(new ArrowButton(false), 770, y); // left arrow
            addObject(new ValueBox(), 860, y); // middle box
            addObject(new ArrowButton(true), 950, y);  // right arrow
            y += 80;
        }
    }
    
    // Left side (Human settings)
    private void setupHumanColumn() {
        int y = 200;
        
        // Creates four columns
        for (int i = 0; i < 4; i++) {
            addObject(new ArrowButton(false), 330, y); // left arrow
            addObject(new ValueBox(), 420, y); // middle box
            addObject(new ArrowButton(true), 510, y);  // right arrow
            y += 80;
        }
    }
    
    private void setupLabels() {
        // Titles
        addObject(new TitleLabel("Humans", 48), 350, 80);
        addObject(new TitleLabel("Robots", 48), 890, 80);

        // Row labels
        String[] labels = {
            "Spawn rate",
            "Starting HP",
            "Speed",
            "Cash gained per kill"
        };

        int y = 200;
        
        for (String s : labels) {
            // Human side settings
            addObject(new SmallLabel(s), 150, y);
            
            // Robot side settings
            addObject(new SmallLabel(s), 750 + 370, y);
            y += 80;
        }
    }
}
