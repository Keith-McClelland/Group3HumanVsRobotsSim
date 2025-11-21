import java.util.List;
import greenfoot.*;

public class StoryWorldHuman extends SuperSmoothMover
{
    private GreenfootImage[] walk = new GreenfootImage[4];
    private int frame = 0;
    private int animationTimer = 0;

    private Crystal target;
    private HumanSpeech speech;

    private boolean speechDone = false;
    private boolean chasingDone = false; // NEW
    private Drone droneTarget;
    private boolean flipped = false;

    public StoryWorldHuman() {
        for (int i = 0; i < 4; i++) {
            walk[i] = new GreenfootImage("cavemanwalk" + (i+1) + ".png");
            walk[i].mirrorHorizontally();
            walk[i].scale(60, 70);
        }
        setImage(walk[0]);
    }

    public void act() {
        if (!speechDone) {
            moveTowardCrystal();
            return;
        }

        if (!chasingDone) {
            chaseDrone();
            return;
        }

        // After chase → return to right side
        returnToRightSide();
    }

    private void moveTowardCrystal() {
        if (target == null) {
            List<Crystal> crystals = getWorld().getObjects(Crystal.class);
            if (!crystals.isEmpty()) target = crystals.get(0);
            return;
        }

        double dx = target.getX() - getX();
        double distance = Math.abs(dx);

        if (distance > 100) {
            setLocation(getX() + Math.signum(dx) * 1, getY());
            animateWalk();
            return;
        }

        // REMOVE robot speech
        List<RobotSpeech> robotSpeeches = getWorld().getObjects(RobotSpeech.class);
        if (!robotSpeeches.isEmpty()) getWorld().removeObject(robotSpeeches.get(0));

        // ADD human speech
        speech = new HumanSpeech();
        getWorld().addObject(speech, getX() - 105, getY() - 105);

        // Wait exactly 5 seconds
        Greenfoot.delay(300);

        // REMOVE human speech
        if (speech != null && speech.getWorld() != null)
            getWorld().removeObject(speech);

        speechDone = true;

        // Pick drone target
        List<Drone> drones = getWorld().getObjects(Drone.class);
        if (!drones.isEmpty()) droneTarget = drones.get(0);
    }

    private void chaseDrone() {
        if (droneTarget == null || droneTarget.getWorld() == null) {
            chasingDone = true;
            return;
        }

        // Drone escapes
        droneTarget.runAway();

        if (droneTarget.getWorld() == null) {
            chasingDone = true;
            return;
        }

        // Human moves toward drone
        double dx = droneTarget.getX() - getX();
        double dy = droneTarget.getY() - getY();
        double dist = Math.hypot(dx, dy);

        if (dist > 5) {
            setLocation(getX() + dx / dist * 2, getY() + dy / dist * 2);
            animateWalk();
        }
    }

    // AFTER chase → human turns around and runs off screen
    private void returnToRightSide() {
        // Face right side
        if(!flipped){
        for (int i = 0; i < walk.length; i++)
        {
            walk[i].mirrorHorizontally();  // mirror once
            setImage(walk[0]);
        }
        flipped = true;
    }

        // Move right until off screen
        if (getX() < getWorld().getWidth() -5) {
            setLocation(getX() + 3, getY());
            animateWalk();
        } else {
            getWorld().removeObject(this);
            Greenfoot.setWorld(new SettingsWorld());
        }
    }

    private void animateWalk() {
        animationTimer++;
        if (animationTimer % 6 == 0) {
            frame = (frame + 1) % walk.length;
            setImage(walk[frame]);
        }
    }
}


