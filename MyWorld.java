import greenfoot.*;
import java.util.List;
/**
    Art Credits:
    
    - Robot 0 by Hannah Cohan (PlatForge) — https://opengameart.org/content/robot-0
    - Tuxemon Tileset by Buch — https://opengameart.org/content/tuxemon-tileset
    - Sci-Fi Tileset Layer – Junkyard (Kelvana Prime) by Galacti-Chron — https://opengameart.org/content/sci-fi-tileset-layer-junkyard-kelvana-prime
    - Wasteland Tileset by Jarp.pix — https://jarppix.itch.io/wasteland-tileset
    - Explosion Set 1 by M484 Games — https://opengameart.org/content/explosion-set-1-m484-games
    - Simple Sci-Fi Bullets by Bonsaiheldin — https://opengameart.org/content/sci-fi-space-simple-bullets
    - Laser Pack 2020 by Wenrexa — https://wenrexa.itch.io/laser2020
    - Pixel Art Cannon by 1up Indie — https://opengameart.org/content/pixel-art-canon
    - Open Gunner Starter Kit by Master484 — https://opengameart.org/content/open-gunner-starter-kit
    - 16x16 Desert Tilesets by 16Pixel / OGA collection — https://opengameart.org/content/16x16-desert-tilesets
    - Free RPG Desert Tileset by itch.io asset page — https://free-game-assets.itch.io/free-rpg-desert-tileset
    - Free Robot Pixel Art Sprite Sheets by CraftPix — https://craftpix.net/freebies/free-robot-pixel-art-sprite-sheets
    - Bullets – Pixel 64-Bit Assets by PIOmakesPIXELS — https://piomakespixels.itch.io/bullets-pixel-64-bit-assets
    - 2D Sci-Fi Turret Pack by LeavarioxStudios — https://leavarioxstudios.itch.io/2d-sci-fi-turret-pack
    - Crystals & Stone Set by phobi — https://opengameart.org/content/crystals-and-stone-set
    - Stone by contributor — https://opengameart.org/content/stone
    - Overworld Objects by Kelvin Shadewing — https://opengameart.org/content/overworld-objects
    - Play / Pause / Mute / Unmute Buttons by contributor — https://opengameart.org/content/play-pause-mute-and-unmute-buttons
    - Transparent PNG assets (misc props/robots) by HiClipArt — https://www.hiclipart.com
    - Free Factory-related assets by multiple authors — https://itch.io/game-assets/free/tag-factory
    - Factory search results (OpenGameArt) — https://opengameart.org/art-search-advanced?keys=factory
**/


public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static int frameCount = 0;

    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;

    private int evolutionStage = 1; // stage 1
    private int maxHumans = 10;
    private int maxRobots = 10;

    private int humanSpawnInterval = 120; // frames (~2s)
    private int robotSpawnInterval = 120;

    private StatBoard humanStatboard;
    private StatBoard robotStatboard;

    public MyWorld() {
        super(1240, 700, 1);

        setPaintOrder(
            StatsHUD.class,
            SuperStatBar.class,
            Builders.class,
            Fences.class,
            Robot.class
        );

        setBackground(background);

        humanStatboard = new StatBoard();
        robotStatboard = new StatBoard();
        addObject(humanStatboard, 105, 75);
        addObject(robotStatboard, getWidth() - 105, 75);

        addObject(new StatsHUD(true), 126, 96);
        addObject(new StatsHUD(false), 1157, 96);
        addObject(new TerritoryBar(), getWidth() / 2, 30);

        Units.setHumanCash(0);
        Units.setRobotCash(0);
        Human.setTotalHumansSpawned(0);
        Robot.setTotalRobotsSpawned(0);

        frameCount = 0;
    }

    public void act() {
        frameCount++;
        EndSimWorld.totalTimeElapsed = frameCount;

        humanSpawnTimer++;
        if (humanSpawnTimer >= humanSpawnInterval) {
            humanSpawnTimer = 0;
            if (getObjects(Human.class).size() < maxHumans) spawnHumans();
        }

        robotSpawnTimer++;
        if (robotSpawnTimer >= robotSpawnInterval) {
            robotSpawnTimer = 0;
            if (getObjects(Robot.class).size() < maxRobots) spawnRobots();
        }

        updateEvolutionStage();
    }

    private void updateEvolutionStage() 
    {
        // Human upgrades and cannon
        int humanCash = Units.getHumanCash();
        if (evolutionStage == 1 && humanCash >= 50) {
            Units.addHumanCash(-50); // spend cash
            evolutionStage = 2;
        } else if (evolutionStage == 2 && humanCash >= 100) {
            Units.addHumanCash(-100);
            evolutionStage = 3;
        } else if (evolutionStage == 3 && humanCash >= 150) {
            Units.addHumanCash(-150);
            evolutionStage = 4;
            // Spawn human canon
            if (getObjects(Canon.class).isEmpty()) { // only one canon
                addObject(new Canon(true), getWidth() - 100, getHeight() / 2);
            }
        }
    
        // Robot upgrades and turret
        int robotCash = Units.getRobotCash();
        if (evolutionStage == 1 && robotCash >= 50) {
            Units.addRobotCash(-50);
            evolutionStage = 2;
        } else if (evolutionStage == 2 && robotCash >= 100) {
            Units.addRobotCash(-100);
            evolutionStage = 3;
            // Spawn robot turret
            if (getObjects(Turret.class).isEmpty()) { // only one turret
                addObject(new Turret(false), 100, getHeight() / 2);
            }
        }
    }


    private void spawnHumans() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);

        if (evolutionStage == 1) {
            addObject(new MeleeHuman(100, 1.8, 40, 30, 20, 10, "caveman"), getWidth() - 50, y);
        } else if (evolutionStage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(100, 1.8, 40, 30, 20, 10, "caveman"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(50, 1.5, 300, 25, 20, 12, "archer"), getWidth() - 50, y);
        } else if (evolutionStage >= 3) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(100, 2.0, 50, 40, 25, 15, "cyborg"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(50, 1.8, 350, 30, 25, 15, "gunner"), getWidth() - 50, y);
        }else if (evolutionStage >= 4) {
            int choice = Greenfoot.getRandomNumber(3); // 0,1,2
            if(choice == 0)
            {
                addObject(new MeleeHuman(100, 2.0, 50, 40, 25, 15, "cyborg"), getWidth() - 50, y);
            }
            else if (choice == 1)
            {
                addObject(new RangedHuman(50, 1.8, 350, 20, 25, 15, "gunner"), getWidth() - 50, y);
            }
            else
            {
                addObject(new GiantHuman(1000, 0.5, 100, 20, 30, 500, "giant"), getWidth() - 50, y);
            }
        }
    }

    private void spawnRobots() 
    {
            int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);
    
            if (evolutionStage == 1) {
                addObject(new MeleeRobot(100, 1.8, 40, 30, 20, 12), 50, y);
            } else if (evolutionStage == 2) {
                if (Greenfoot.getRandomNumber(2) == 0)
                    addObject(new MeleeRobot(150, 1.8, 35, 40, 20, 12), 50, y);
                else
                    addObject(new RangedRobot(100, 1.5, 300, 60, 15, 10), 50, y);
            } else if (evolutionStage >= 3) {
                int choice = Greenfoot.getRandomNumber(10); // 0,1,2
                if(choice == 0)
                {
                    addObject(new ExplodingRobot(500, 1.5, 50, 40, 20, 20), 50, y);
                }
                else if (choice <= 5)
                {
                    addObject(new RangedRobot(200, 1.5, 300, 70, 20, 20), 50, y);
                }
                else
                {
                    addObject(new MeleeRobot(180, 2.0, 45, 40, 25, 15), 50, y);
                }
        }
    }
}

