import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * IntroWorld is a Greenfoot World that displays the introductory scene of the game simulation. 
 * It shows a start background with the title NEXUS, a continue button right under it. 
 * When Continue button is clicked, the story of the game will begin and a robot from the robot side (left) 
 * will attempt to retrieve a gem that sits on the human side (right), while the human rejects the robot 
 * leading to a battle 
 * <p>
 * It displays the front page when the user opens the game
 * 
 * @author Veznu Premathas
 * @version November 2025
 */
public class IntroWorld extends World
{
    private ContinueButton button;
    // checks if robot is done scanning
    public static boolean doneScanning = false;
    // import background music
    private static GreenfootSound backgroundMusic = new GreenfootSound("introWorldBackground.wav");
    
    /**
     * Constructor for IntroWorld. 
     * Initializes the world dimensions to 1240x700 pixels, sets the initial 
     * background image to "introworld.png", and places the continue button.  
     */
    public IntroWorld()
    {           
        super(1240, 700, 1);
        setBackground(new GreenfootImage("introworld.png"));

        button = new ContinueButton();
        addObject(button, getWidth()/2, getHeight()/2 - 80);
    

    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * - Checks if the continue button is clicked to transition the scene. 
     * - Checks if doneScanning is true to spawn a human. 
     */
    public void act() {
        // Check if the user clicks the ContinueButton
        if (Greenfoot.mouseClicked(button)) {
            playMusic();
            setBackground(new GreenfootImage("storyworld.png"));
            removeObject(button);
                        
            Crystal crystal = new Crystal();
            addObject(crystal,954,503);
            
            StoryWorldDrone drone = new StoryWorldDrone();
            addObject(drone,0,560);
        
        }
        
        if(doneScanning)
        {
            spawnHuman();
            doneScanning = false;
        }
    }
    
    /**
     * Plays background music
     */
    public void playMusic()
    {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setVolume(20);
            backgroundMusic.playLoop();
        }
    }
    
    /**
     * Starts playing the music
     */
    @Override
    public void started() {
        playMusic(); // resumes loop if not already playing
    }

    /**
     * Stops the music
     */
    @Override
    public void stopped()
    {
        backgroundMusic.stop();
    }
    
    /**
     * Stops the music
     */
    public static void stopMusic()
    {
        backgroundMusic.stop();
    }

    
    /**
     * Spawns a StoryWorldHuman at a specific position on the right side of the world. 
     */
    public void spawnHuman() 
    {
        StoryWorldHuman human = new StoryWorldHuman();
        addObject(human, 1200, 540); // right side spawn
    }

}



