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
    GreenfootSound backgroundMusic = new GreenfootSound("settingWolrd.mp3");

    public SettingsWorld()
    {    
        super(1240, 700, 1);
        
        GameSettings.reset();
        playMusic();
        setBackground("storyworld.png");
        
        Actor panel = new Actor() {};
        
        GreenfootImage bg = new GreenfootImage(1200, 600);
        bg.setColor(new Color(45, 45, 45));
        bg.fill();
        panel.setImage(bg);

        addObject(panel, getWidth()/2, 350);
        
        setupLabels();
        setupHumanColumn();
        setupRobotColumn();
        
        continueButton = new ContinueButton();
        addObject(continueButton, getWidth() / 2, 550);
    }

    public void act() {
        handleContinueButton();
    }
    
    private void handleContinueButton() {
        if (Greenfoot.mouseClicked(continueButton)) {
            stopMusic();
            Greenfoot.setWorld(new MyWorld());
        }
    }
    
    public void playMusic()
    {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setVolume(40);
            backgroundMusic.playLoop();
        }
    }

    @Override
    public void started() {
        playMusic();
    }

    @Override
    public void stopped()
    {
        backgroundMusic.stop();
    }
    
    public void stopMusic()
    {
        backgroundMusic.stop();
    }
    
    /**
     * Creates and positions the right column of settings for Robot units.
     */
    private void setupRobotColumn() {
        int y = 200;
        String[] properties = {"robotSpawnRate", "robotHP", "robotSpeed", "robotCash"};
        for (int i = 0; i < 4; i++) {

            ValueBox box = new ValueBox(properties[i]);  // ONE value box

            addObject(new ArrowButton(false, box), 330, y);  // left arrow
            addObject(box, 420, y);                         // value box
            addObject(new ArrowButton(true, box), 510, y);  // right arrow

            y += 80;
        }
    }
    
    /**
     * Creates and positions the left column of settings for Human units.
     */
    private void setupHumanColumn() {
        int y = 200;
        String[] properties = {"humanSpawnRate", "humanHP", "humanSpeed", "humanCash"};
        for (int i = 0; i < 4; i++) {

            ValueBox box = new ValueBox(properties[i]);  // ONE value box

            addObject(new ArrowButton(false, box), 770, y);  // left arrow
            addObject(box, 860, y);                          // value box
            addObject(new ArrowButton(true, box), 950, y);   // right arrow

            y += 80;
        }
    }
    
    /**
     * Draws all title labels and row labels for both Human and Robot columns. 
     */
    private void setupLabels() {
        // Titles
        addObject(new TitleLabel("Humans", 48), 890, 80);
        addObject(new TitleLabel("Robots", 48), 350, 80);

        String[] labels = {
            "Spawn rate",
            "Starting HP",
            "Speed",
            "Cash gained per kill"
        };

        int y = 200;
        
        for (String s : labels) {
            addObject(new SmallLabel(s), 150, y);           // human side
            addObject(new SmallLabel(s), 750 + 370, y);     // robot side
            y += 80;
        }
    }
}
