import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class IntroWorld extends World
{
    private ContinueButton button;
    public static boolean doneScanning = false;
    
    public IntroWorld()
    {           
        super(1240, 700, 1);
        setBackground(new GreenfootImage("introworld.png"));

        button = new ContinueButton();
        addObject(button, getWidth()/2, getHeight()/2 - 80);
    

    }

    public void act() {
        // Check if the user clicks the ContinueButton
        if (Greenfoot.mouseClicked(button)) {
            setBackground(new GreenfootImage("storyworld.png"));
            removeObject(button);
            
            Crystal crystal = new Crystal();
            addObject(crystal,954,503);
            
            Drone drone = new Drone();
            addObject(drone,0,560);
        }
        
        if(doneScanning)
        {
            spawnHuman();
            doneScanning = false;
        }
    }
    
    public void spawnHuman() 
    {
        StoryWorldHuman human = new StoryWorldHuman();
        addObject(human, 1200, 540); // right side spawn
    }

}


