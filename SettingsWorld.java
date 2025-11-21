import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SettingsWorld extends World
{

    ContinueButton continueButton;
    public SettingsWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1240, 700, 1);
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
        
        ContinueButton continueButton = new ContinueButton();
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
