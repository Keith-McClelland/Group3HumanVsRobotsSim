import java.util.List;
import greenfoot.*;

public class StoryWorldHuman extends SuperSmoothMover
{
    private GreenfootImage[] walk = new GreenfootImage[4];
    private int frame = 0;
    private int animationTimer = 0;

    private Crystal target;
    private HumanSpeech speech;

    private RobotSpeech robotSpeech;  
    private int convoTimer = 0;
    private int convoStage = 0;  // 0=H1,1=R3,2=H2,3=R4,4=done
    private boolean conversationStarted = false;

    private Drone drone;          // reference to existing drone
    private RobotSpeech droneSpeech; // speech bubble above drone

    public StoryWorldHuman() {
        for (int i = 0; i < 4; i++) {
            walk[i] = new GreenfootImage("cavemanwalk" + (i+1) + ".png");
            walk[i].mirrorHorizontally();
            walk[i].scale(60, 70);
        }
        setImage(walk[0]);
    }

    public void addedToWorld(World world) {
        // Get the first existing drone in the world
        List<Drone> drones = world.getObjects(Drone.class);
        if (!drones.isEmpty()) {
            drone = drones.get(0);

            // Spawn its speech bubble above the drone
            droneSpeech = new RobotSpeech();
            world.addObject(droneSpeech, drone.getX() + 110, drone.getY() - 95);
        }
    }

    public void act() {
        // Update drone speech position to follow the drone
        if (drone != null && droneSpeech != null) {
            droneSpeech.setLocation(drone.getX() + 110, drone.getY() - 95);
        }

        // Handle human conversation
        if (conversationStarted) {
            convoTimer++;

            if (convoStage == 0 && convoTimer >= 300) { // 5 sec
                if (speech != null) getWorld().removeObject(speech);

                if (robotSpeech != null) {
                    robotSpeech.showSpeech3();
                    robotSpeech.setLocation(getX() + 50, getY() - 105);
                }

                convoStage = 1;
                convoTimer = 0;
            }
            else if (convoStage == 1 && convoTimer >= 300) {
                if (speech != null) getWorld().removeObject(speech);

                // Human speech 2
                speech = new HumanSpeech();
                getWorld().addObject(speech, getX() - 105, getY() - 105);
                speech.showHuman2();

                convoStage = 2;
                convoTimer = 0;
            }
            else if (convoStage == 2 && convoTimer >= 300) {
                if (speech != null) getWorld().removeObject(speech);

                if (robotSpeech != null) {
                    robotSpeech.showSpeech4();
                    robotSpeech.setLocation(getX() + 50, getY() - 105);
                }

                convoStage = 3;
                convoTimer = 0;
            }
            else if (convoStage == 3 && convoTimer >= 300) {
                if (speech != null) getWorld().removeObject(speech);
                // Robot speech remains on screen
                conversationStarted = false;
            }
        }

        // Move toward crystal
        if (target == null) {
            findCrystal();
        } else {
            moveTowardCrystal();
        }
    }

    private void findCrystal() {
        List<Crystal> crystals = getWorld().getObjects(Crystal.class);
        if (!crystals.isEmpty()) target = crystals.get(0);
    }

    private void moveTowardCrystal() {
        double dx = target.getX() - getX();
        double dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance > 100) {
            setLocation(getX() + dx / distance * 1, getY());
            animateWalk();
        } else {
            // Spawn Human Speech 1 if conversation hasn't started
            if (getWorld() != null && speech == null) {

                // Get existing RobotSpeech in world for human conversation
                List<RobotSpeech> robotSpeechList = getWorld().getObjects(RobotSpeech.class);
                if (!robotSpeechList.isEmpty()) {
                    robotSpeech = robotSpeechList.get(0);
                    robotSpeech.setLocation(getX() + 50, getY() - 105);
                }

                // Spawn Human Speech 1
                speech = new HumanSpeech();
                getWorld().addObject(speech, getX() - 105, getY() - 105);
                speech.setImage(speech.speech1);

                convoTimer = 0;
                convoStage = 0;
                conversationStarted = true;
            }
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



