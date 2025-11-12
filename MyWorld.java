import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World {
    GreenfootImage background = new GreenfootImage("images/background.png");

    // Text display images
    private GreenfootImage humanCashImage;
    private GreenfootImage robotCashImage;

    // Positions for text
    private int humanCashX, humanCashY;
    private int robotCashX, robotCashY;

    public MyWorld() {    
        super(1240, 700, 1); 
        setBackground(background);

        testPrepare();

        // Set text positions (bottom corners)
        humanCashX = getWidth() - 250;
        humanCashY = getHeight() - 40;
        robotCashX = 30;
        robotCashY = getHeight() - 40;
    }

    private void testPrepare() {
        for (int i = 0; i < 5; i++) {
            RangedHuman rockThrower = new RangedHuman(100, 1.5, 200, 10, 100, 100);
            addObject(rockThrower, Greenfoot.getRandomNumber(getWidth() / 2) + getWidth()/2, Greenfoot.getRandomNumber(getHeight()));
        }

        for (int i = 0; i < 5; i++) {
            ExplodingRobot er = new ExplodingRobot(80, 1.2, 150, 50, 100);
            addObject(er, Greenfoot.getRandomNumber(getWidth() / 2), Greenfoot.getRandomNumber(getHeight()));
        }

        for (int i = 0; i < 3; i++) {
            MeleeHuman rockSmasher = new MeleeHuman(150, 1.0, 75, 20, 10, 100);
            addObject(rockSmasher, Greenfoot.getRandomNumber(getWidth() / 2) + getWidth()/2, Greenfoot.getRandomNumber(getHeight()));
        }

        for (int i = 0; i < 3; i++) {
            MeleeRobot tinyRobot = new MeleeRobot(150, 1.0, 75, 20, 50, 100);
            addObject(tinyRobot, Greenfoot.getRandomNumber(getWidth() / 2), Greenfoot.getRandomNumber(getHeight()));
        }

        Builders men = new Builders();
        addObject(men, getWidth()/2, getHeight()/4);

        Turret turret = new Turret();
        addObject(turret, 100 , getHeight()/2+100);
    }

    public void act() {
        updateCashDisplay();
    }

    private void updateCashDisplay() {
        // Draw the cash display text
        showText("", 0, 0); // clear any previous Greenfoot text

        GreenfootImage frame = getBackground(); // get background image reference
        frame.drawImage(background, 0, 0); // redraw background so text refreshes cleanly

        // Create new text images
        humanCashImage = new GreenfootImage("Human Cash: " + Units.getHumanCash(), 24, Color.WHITE, new Color(0, 0, 0, 150));
        robotCashImage = new GreenfootImage("Robot Cash: " + Units.getRobotCash(), 24, Color.WHITE, new Color(0, 0, 0, 150));

        // Draw them directly onto the world background
        frame.drawImage(robotCashImage, robotCashX, robotCashY);
        frame.drawImage(humanCashImage, humanCashX, humanCashY);
    }
}
