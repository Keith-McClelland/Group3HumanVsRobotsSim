import java.util.List;
import greenfoot.*;

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

    private void moveTowardCrystal() {
        //find the crystal on first update
        if (target == null) {
            List<Crystal> crystals = getWorld().getObjects(Crystal.class);
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
        List<RobotSpeech> robotSpeeches = getWorld().getObjects(RobotSpeech.class);
        if (!robotSpeeches.isEmpty()) 
        {
            getWorld().removeObject(robotSpeeches.get(0));
        }

        //add human speech bubble
        speech = new HumanSpeech();
        getWorld().addObject(speech, getX() - 105, getY() - 105);

        //hold speech on screen for 5 seconds for people to read
        Greenfoot.delay(300);

        //remove the speech bubble
        if (speech != null && speech.getWorld() != null)
        {
            getWorld().removeObject(speech);
        }

        //speech completed
        speechDone = true;

        //choose drone to chase
        List<Drone> drones = getWorld().getObjects(Drone.class);
        if (!drones.isEmpty()) droneTarget = drones.get(0);
    }

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

    //after chase human turns around and exits right side
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
            Greenfoot.setWorld(new SettingsWorld());
        }
    }

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


