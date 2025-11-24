import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class SettingsWorld extends World
{
    ContinueButton continueButton;
    public SettingsWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1240, 700, 1);
        
        // Reset all settings
        GameSettings.reset();
        
        setBackground("storyworld.png");
        
        Actor panel = new Actor() {};
        
        // Dark grey background
        GreenfootImage bg = new GreenfootImage(1200, 600);
        bg.setColor(new Color(45, 45, 45));
        bg.fill();
        panel.setImage(bg);

        addObject(panel, getWidth()/2, 350);
        
        
        // Settings menu components all combined
        setupLabels();
        setupHumanColumn();
        setupRobotColumn();
        
        continueButton = new ContinueButton();
        addObject(continueButton, getWidth() / 2, 550);
    }

    public void act() {
        handleContinueButton();
    }

    // Checks if continue button is clicked
    private void handleContinueButton() {
        if (Greenfoot.mouseClicked(continueButton)) {
            // Enter the next world
            Greenfoot.setWorld(new MyWorld()); // <-- change to desired world
        }
    }
    
    // Right side (Robot settings)
    private void setupRobotColumn() {
        int y = 200;
        String[] properties = {"robotSpawnRate", "robotHP", "robotSpeed", "robotCash"};
        for (int i = 0; i < 4; i++) {
            ValueBox box = new ValueBox(properties[i]); // use new constructor
            addObject(new ArrowButton(false, box), 770, y);
            addObject(box, 860, y);
            addObject(new ArrowButton(true, box), 950, y);
            y += 80;
        }
    }
    
    // Left side (Human settings)
    private void setupHumanColumn() {
        int y = 200;
        String[] properties = {"humanSpawnRate", "humanHP", "humanSpeed", "humanCash"};
        for (int i = 0; i < 4; i++) {
            ValueBox box = new ValueBox(properties[i]); // use new constructor
            addObject(new ArrowButton(false, box), 330, y);
            addObject(box, 420, y);
            addObject(new ArrowButton(true, box), 510, y);
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