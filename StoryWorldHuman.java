import java.util.ArrayList;
import greenfoot.*;
/**
 * StoryWorldHuman represents a human character in the story sequence. 
 * <p>
 * The human: 
 * Walks toward a Crystal and delivers a speech. 
 * Chases away a Drone. 
 * Exits the world to the right after completing its actions. 
 * Animations for walking and speech bubbles are included. 
 * 
 * @author Veznu Premathas 
 * @version November 2025
 * 
 */
public class StoryWorldHuman extends SuperSmoothMover
{
    //controls the propeties of the walk animation frames
    private GreenfootImage[] walk = new GreenfootImage[4];
    private int frame = 0;
    private int animationTimer = 0;

    //targets and speech objects
    private Crystal target;
    private HumanSpeech speech;

    //tracks states to continue to the next stage
    private boolean speechDone = false;
    private boolean chasingDone = false; 
    private Drone droneTarget;
    private boolean flipped = false;
    
    /**
     * Constructor for StoryWorldHuman that creates a StoryWorldHuman character and loads its walking frames. 
     */
    public StoryWorldHuman() {
        //load and prepare walking frames
        for (int i = 0; i < 4; i++) {
            walk[i] = new GreenfootImage("cavemanwalk" + (i+1) + ".png");
            walk[i].mirrorHorizontally(); 
            walk[i].scale(60, 70);
        }

        //start on first walk frame
        setImage(walk[0]);
    }
    
    /**
     * The act method is called repeatedly by Greenfoot. 
     * Handles the three stages of the story: 
     */
    public void act() {
        //walk to crystal and talk
        if (!speechDone) {
            moveTowardCrystal();
            return;
        }

        //chase drone after speech
        if (!chasingDone) {
            chaseDrone();
            return;
        }

        //run off screen to the right, returning to it's own side after 
        //chasing drone away
        returnToRightSide();
    }
    
    /**
     * Moves the human toward the Crystal, triggers speech, and selects a drone to chase. 
     */
    private void moveTowardCrystal() {
        //find the crystal on first update
        if (target == null) {
            ArrayList<Crystal> crystals = new ArrayList<>( getWorld().getObjects(Crystal.class) );
            if (!crystals.isEmpty()) target = crystals.get(0);
            return;
        }

        //calculate horizontal distance
        double dx = target.getX() - getX();
        double distance = Math.abs(dx);

        //walk until close enough
        if (distance > 100) {
            setLocation(getX() + Math.signum(dx) * 1, getY());
            animateWalk();
            return;
        }

        //remove robot speech bubbles, no overlapping
        ArrayList<RobotSpeech> robotSpeeches = new ArrayList<>( getWorld().getObjects(RobotSpeech.class) );
        if (!robotSpeeches.isEmpty()) 
        {
            getWorld().removeObject(robotSpeeches.get(0));
        }

        //add human speech bubble
        speech = new HumanSpeech();
        getWorld().addObject(speech, getX() - 105, getY() - 105);

        //hold speech on screen for 5 seconds for people to read
        Greenfoot.delay(400);

        //remove the speech bubble
        if (speech != null && speech.getWorld() != null)
        {
            getWorld().removeObject(speech);
        }

        //speech completed
        speechDone = true;

        //choose drone to chase
        ArrayList<Drone> drones = new ArrayList<>( getWorld().getObjects(Drone.class) );
        if (!drones.isEmpty()) droneTarget = drones.get(0);
    }
    
    /**
     * Chases the target drone until it escapes or is removed from the world. 
     */
    private void chaseDrone() {
        //if drone already escaped, move to next phase
        if (droneTarget == null || droneTarget.getWorld() == null) {
            chasingDone = true;
            return;
        }

        //drone flees from human
        droneTarget.runAway();

        //if drone is removed from world, chase is done
        if (droneTarget.getWorld() == null) {
            chasingDone = true;
            return;
        }

        //move human toward drone position
        double dx = droneTarget.getX() - getX();
        double dy = droneTarget.getY() - getY();
        double dist = Math.hypot(dx, dy);

        if (dist > 5) {
            setLocation(getX() + dx / dist * 2, getY() + dy / dist * 2);
            animateWalk();
        }
    }
    
    /**
     * Moves the human off-screen to the right and flips image if necessary. 
     * <p>
     * Once fully off-screen, removes the human and switches to the SettingsWorld. 
     */
    private void returnToRightSide() {
        //flip human image to face right side 
        if (!flipped) {
            for (int i = 0; i < walk.length; i++) {
                walk[i].mirrorHorizontally();
                setImage(walk[0]);
            }
            flipped = true;
        }

        //run off screen to the right
        if (getX() < getWorld().getWidth() - 5) 
        {
            setLocation(getX() + 3, getY());
            animateWalk();
        } else 
        {
            //once off screen → go to settings world
            getWorld().removeObject(this);
            IntroWorld.stopMusic();
            Greenfoot.setWorld(new SettingsWorld());
            
        }
    }
    
    /**
     * Animates walking by cycling through the frames. 
     */
    private void animateWalk() {
        //slow down animation speed
        animationTimer++;
        
        //change frame 
        if (animationTimer % 6 == 0) {
            frame = (frame + 1) % walk.length;
            setImage(walk[frame]);
        }
    }
}
