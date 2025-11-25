import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * SettingsWorld is a Greenfoot World that displays the settings menu of the game simulation. 
 * It shows two sides Human (left) and Robots (right) with four options of settings {Spawn rate, starting hp, speed, cash earned per kill}.  
 * These settings only affect how the units behave. 
 * The white text values are the base settings applied for the units. 
 * <p>
 * Clicking the left arrow button will decrease the value inside the middle box by 1. 
 * Clicking the right arrow button will increase the value inside the middle box by 1. 
 * @author Jonathan Shi 
 * @version November 2025
 */
public class SettingsWorld extends World
{
    ContinueButton continueButton;
    /**
     * Creates the settings menu world, initializes default settings, 
     * draws the layout, and places all UI components.
     */
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
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Handles UI interaction such as clicking the Continue button. 
     */
    public void act() {
        handleContinueButton();
    }
    
    /**
     * Checks if the Continue button is clicked.
     * If clicked, transitions into the main gameplay world.
     */
    private void handleContinueButton() {
        if (Greenfoot.mouseClicked(continueButton)) {
            // Enter the next world
            Greenfoot.setWorld(new MyWorld()); // <-- change to desired world
        }
    }
    
    /**
     * Creates and positions the right column of settings for Robot units.
     * <p>
     * Settings included:
     *     SpawnRate
     *     Starting HP
     *     Speed
     *     Cash
     */
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
    
    /**
     * Creates and positions the left column of settings for Human units.
     * <p>
     * Settings included:
     *     SpawnRate
     *     Starting HP
     *     Speed
     *     Cash
     */
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
    
    /**
     * Draws all title labels and row labels for both Human and Robot columns. 
     * <p>
     * Title labels include:
     *     Humans, Robots
     * Small row labels include:
     *     SpawnRate
     *     Starting HP
     *     Speed
     *     Cash gained per kill
     */
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