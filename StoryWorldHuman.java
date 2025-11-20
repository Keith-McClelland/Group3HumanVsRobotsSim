import java.util.List;
import greenfoot.*;

public class StoryWorldHuman extends SuperSmoothMover
{
    private GreenfootImage[] walk = new GreenfootImage[4];
    private int frame = 0;
    private int animationTimer = 0;

    private Crystal target;
    private HumanSpeech speech;


    public StoryWorldHuman() {
        for (int i = 0; i < 4; i++) {
            walk[i] = new GreenfootImage("cavemanwalk" + (i+1) + ".png");
            walk[i].mirrorHorizontally();
            walk[i].scale(60, 70);
        }
        setImage(walk[0]);
    }

    

    public void act() {
     
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
            
                List<RobotSpeech> robotSpeeches = getWorld().getObjects(RobotSpeech.class);
                if (!robotSpeeches.isEmpty()) {
                    RobotSpeech robotSpeech = robotSpeeches.get(0);
                    getWorld().removeObject(robotSpeech);
                }
                
                speech = new HumanSpeech();
                getWorld().addObject(speech, getX() - 105, getY() - 105);
                speech.setImage(speech.speech1);

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



