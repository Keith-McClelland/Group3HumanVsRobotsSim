import greenfoot.*;
import java.util.List;
/**
 *  This simulation represents an ongoing conflict between Humans and Robots,
 *  where both sides fight for territorial control in order to obtain the
 *  “Nexus,” a gem required for each side to continue existing. Both
 *  sides depend on the Nexus. Therefore, each side expands and upgrades over
 *  time in response to resources collected through combat.
 *
 *  As the simulation progresses, each side earns cash through combat. When
 *  specific cash thresholds are met, new evolution stages unlock. These
 *  stages change which units are spawned, increase overall combat strength, 
 *  and unlock structures such as cannons, turrets, hospitals, and factories.
 *
 *  Human Progression:
 *  Stage 1: Caveman (melee)
 *      - Features: basic melee unit, standard HP and damage
 *  Stage 2: Unlocks Archer; Cavemen continue spawning
 *      - Features: Archers have ranged combat up to 300 pixels, reduced HP and slower speed
 *  Stage 3: Cannons begin spawning for long-range support; Cavemen and Archers continue spawning
 *      - Features: Cannons have slow fire rate, but high damage to enemy units
 *  Stage 4: Unlocks Giant Human; Cannons, Cavemen, and Archers continue spawning
 *      - Features: Giant moves slowly, very high HP, deals high damage to buildings
 *  Stage 5+: Unit evolution and Hospital spawns
 *      - CaveMan evolves into Cyborg (futuristic melee unit; faster, higher damage)
 *      - Archers evolve into Futuristic Gunner (ranged unit; faster, higher damage)
 *      - Giant evolves into Tank (heavy unit; extremely high HP, high damage)
 *      - Structures: Cannon and Hospital continue functioning as support; Hospital heals nearby humans
 *
 *  Robot Progression:
 *  Stage 1: Basic Melee Robots
 *      - Features: standard health, speed from GameSettings, short-range combat
 *  Stage 2: Unlocks Ranged Robots and improved melee variants
 *      - Features: Ranged robots have 300-range attacks; melee robots have increased HP
 *  Stage 3: Unlocks Exploding Robots, upgraded ranged robots, and faster melee units
 *      - Features: Exploding Robots have high HP and detonate on death; upgraded melee units are faster
 *      - Structures: Turrets spawn; provide ranged defensive fire
 *  Stage 4: Factory structure unlocks
 *      - Features: Factory supports production of robots and allows continuous spawning
 *  Stage 5+: Robot units evolve further
 *      - Basic Melee Robots evolve into Cyborg Bots (faster, higher damage)
 *      - Ranged Robots evolve into Gunner Bots (higher range and fire rate)
 *      - Exploding Robots evolve into Bomber Bots (detonate on death, larger area damage)
 *      - Structures: Turrets and Factories continue functioning, providing support and production
 *   
 *   
 *   Art Credits:
 *   - Robot 0 by Hannah Cohan (PlatForge) — https://opengameart.org/content/robot-0
 *   - Tuxemon Tileset by Buch — https://opengameart.org/content/tuxemon-tileset
 *   - Sci-Fi Tileset Layer – Junkyard (Kelvana Prime) by Galacti-Chron — https://opengameart.org/content/sci-fi-tileset-layer-junkyard-kelvana-prime
 *   - Wasteland Tileset by Jarp.pix — https://jarppix.itch.io/wasteland-tileset
 *   - Explosion Set 1 by M484 Games — https://opengameart.org/content/explosion-set-1-m484-games
 *   - Simple Sci-Fi Bullets by Bonsaiheldin — https://opengameart.org/content/sci-fi-space-simple-bullets
 *   - Laser Pack 2020 by Wenrexa — https://wenrexa.itch.io/laser2020
 *   - Pixel Art Cannon by 1up Indie — https://opengameart.org/content/pixel-art-canon
 *   - Open Gunner Starter Kit by Master484 — https://opengameart.org/content/open-gunner-starter-kit
 *   - 16x16 Desert Tilesets by 16Pixel / OGA collection — https://opengameart.org/content/16x16-desert-tilesets
 *   - Free RPG Desert Tileset by itch.io asset page — https://free-game-assets.itch.io/free-rpg-desert-tileset
 *   - Free Robot Pixel Art Sprite Sheets by CraftPix — https://craftpix.net/freebies/free-robot-pixel-art-sprite-sheets
 *   - Bullets – Pixel 64-Bit Assets by PIOmakesPIXELS — https://piomakespixels.itch.io/bullets-pixel-64-bit-assets
 *   - 2D Sci-Fi Turret Pack by LeavarioxStudios — https://leavarioxstudios.itch.io/2d-sci-fi-turret-pack
 *   - Crystals & Stone Set by phobi — https://opengameart.org/content/crystals-and-stone-set
 *   - Stone by contributor — https://opengameart.org/content/stone
 *   - Overworld Objects by Kelvin Shadewing — https://opengameart.org/content/overworld-objects
 *   - Play / Pause / Mute / Unmute Buttons by contributor — https://opengameart.org/content/play-pause-mute-and-unmute-buttons
 *   - Transparent PNG assets (misc props/robots) by HiClipArt — https://www.hiclipart.com
 *   - Free Factory-related assets by multiple authors — https://itch.io/game-assets/free/tag-factory
 *   - Factory search results (OpenGameArt) — https://opengameart.org/art-search-advanced?keys=factory
 *   - Persian Foot Archer by Spring Spring — https://opengameart.org/content/greek-hypaspist-persian-foot-archer-pegasus-pony
 *   - Free 3 Cyberpunk Characters Pixel Art by CraftPix — https://craftpix.net/freebies/free-3-cyberpunk-characters-pixel-art
 *   - Character Animations (Caveman) by Chasersgaming — https://opengameart.org/content/character-animations-caveman
 *   - Free Villagers Sprite Sheets Pixel Art by CraftPix — https://craftpix.net/freebies/free-villagers-sprite-sheets-pixel-art
 *   
 *   Sound Credits:
 *   - Classic Punch Impact — https://pixabay.com/sound-effects/classic-punch-impact-352711
 *   - Sword Sound — https://pixabay.com/sound-effects/sword-sound-260274
 *   - Laser — https://pixabay.com/sound-effects/laser-45816
 *   - Arrow Swish — https://pixabay.com/sound-effects/arrow-swish-03-306040
 *   - Gun Shot — https://pixabay.com/sound-effects/gun-shot-350315
 *   - Explosion — https://pixabay.com/sound-effects/explosion-312361
 **/



public class MyWorld extends World {

    GreenfootImage background = new GreenfootImage("images/background.png");
    public static GreenfootSound backgroundMusic = new GreenfootSound("backgroundMusic.mp3");
    public static int frameCount = 0;

    private int humanSpawnTimer = 0;
    private int robotSpawnTimer = 0;
    private int humanCannonTimer = 0;
    private int humanCannonInterval = 1200; 
    private int robotTurretTimer = 0;
    private int robotTurretInterval = 1200; 
    private int humanHospitalTimer = 0;
    private int humanHospitalInterval = 1500; 
    private int robotFactoryTimer = 0;
    private int robotFactoryInterval = 1500;
    private int modernMultiplier = 2; 

    private static int humanEvolutionStage ;
    private static int robotEvolutionStage ;
    
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
        
        
        humanEvolutionStage = 1;
        robotEvolutionStage = 1;

        setBackground(background);

        humanStatboard = new StatBoard("robot");
        robotStatboard = new StatBoard("human");
        addObject(humanStatboard, 105, 75);
        addObject(robotStatboard, getWidth() - 105, 75);

        addObject(new StatsHUD(true), 126, 96);
        addObject(new StatsHUD(false), 1157, 96);
        addObject(new TerritoryBar(), getWidth() / 2, 30);

        Units.setHumanCash(0);
        Units.setRobotCash(0);
        Human.setTotalHumansSpawned(0);
        Robot.setTotalRobotsSpawned(0);

        Human.setHumansAlive(0);
        Human.setHumansDead(0);

        Robot.setRobotsAlive(0);
        Robot.setRobotsDead(0);

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
        
        if (humanEvolutionStage >= 3) { 
            humanCannonTimer++;
            if (humanCannonTimer >= humanCannonInterval) {
                humanCannonTimer = 0;
                spawnHumanCanon();
            }
        }
        
        if (robotEvolutionStage >= 3) {
            robotTurretTimer++;
            if (robotTurretTimer >= robotTurretInterval) {
                robotTurretTimer = 0;
                spawnRobotTurret();
            }
        }

        updateEvolutionStage();
        playMusic();
    }
    
    public void playMusic()
    {
        backgroundMusic.setVolume(20);
        backgroundMusic.playLoop();
    }
   
    public static void stopMusic()
    {
        backgroundMusic.stop();
    }

    private void updateEvolutionStage() {
        int humanCash = Units.getHumanCash();
        int robotCash = Units.getRobotCash();
    
        // Human upgrades
        if (humanEvolutionStage == 1 && humanCash >= 25) {
            Units.addHumanCash(-25);
            humanEvolutionStage = 2;
            addObject(new LevelUpIndicator(), humanStatboard.getX(), humanStatboard.getY() + 90);
        } else if (humanEvolutionStage == 2 && humanCash >= 50) {
            Units.addHumanCash(-50);
            humanEvolutionStage = 3;
            addObject(new LevelUpIndicator(), humanStatboard.getX(), humanStatboard.getY() + 90);
        } else if (humanEvolutionStage == 3 && humanCash >= 75) {
            Units.addHumanCash(-75);
            humanEvolutionStage = 4;
            addObject(new LevelUpIndicator(), humanStatboard.getX(), humanStatboard.getY() + 90);
            if (getObjects(Canon.class).isEmpty()) {
                addObject(new Canon(true), getWidth() - 100, getHeight() / 2);
            }
        } else if (humanEvolutionStage == 4 && humanCash >= 100) {
            Units.addHumanCash(-100);
            humanEvolutionStage = 5;
            addObject(new LevelUpIndicator(), humanStatboard.getX(), humanStatboard.getY() + 90);
            if (getObjects(Hospital.class).isEmpty()) {
                spawnHumanHospital();
            }
        }
        
        // Robot upgrades
        if (robotEvolutionStage == 1 && robotCash >= 25) {
            Units.addRobotCash(-25);
            robotEvolutionStage = 2;
            addObject(new LevelUpIndicator(), robotStatboard.getX(), robotStatboard.getY() + 90);
        } else if (robotEvolutionStage == 2 && robotCash >= 50) {
            Units.addRobotCash(-50);
            robotEvolutionStage = 3;
            addObject(new LevelUpIndicator(), robotStatboard.getX(), robotStatboard.getY() + 90);

            if (getObjects(Turret.class).isEmpty()) {
                addObject(new Turret(false), 100, getHeight() / 2);
            }
        } else if (robotEvolutionStage == 3 && robotCash >= 75) {
            Units.addRobotCash(-75);
            robotEvolutionStage = 4;
            addObject(new LevelUpIndicator(), robotStatboard.getX(), robotStatboard.getY() + 90);
            if (getObjects(Factory.class).isEmpty()) {
                spawnRobotFactory(); 
            }
        }
    }

    private void spawnHumans() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);
        int hp = GameSettings.humanHP;
        double speed = GameSettings.humanSpeed;
        int cash = GameSettings.humanCashPerKill;
    
        // Stage 1: Caveman only
        if (humanEvolutionStage == 1) {
            addObject(new MeleeHuman(hp, speed, 40, 30, 20, cash, "caveman"), getWidth() - 50, y);
            return;
        }
    
        // Stage 2: Caveman + Archer
        if (humanEvolutionStage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(hp, speed, 40, 30, 20, cash, "caveman"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(hp / 2, speed * 0.8, 300, 25, 20, cash, "archer"), getWidth() - 50, y);
            return;
        }
    
        // Stage 3: Caveman + Archer + Cannon (spawn handled separately)
        if (humanEvolutionStage == 3) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeHuman(hp, speed, 40, 30, 20, cash, "caveman"), getWidth() - 50, y);
            else
                addObject(new RangedHuman(hp / 2, speed * 0.8, 300, 25, 100, cash, "archer"), getWidth() - 50, y);
            return;
        }
    
        // Stage 4: Caveman + Archer + Giant + Cannons
        if (humanEvolutionStage == 4) {
            int choice = Greenfoot.getRandomNumber(10); // 0–9
            if (choice <= 4) // 50% chance
                addObject(new MeleeHuman(hp * 2, speed, 40, 30, 20, cash, "caveman"), getWidth() - 50, y);
            else if (choice <= 8) // 40% chance
                addObject(new RangedHuman(hp / 2, speed * 0.8, 300, 30, 100, cash, "archer"), getWidth() - 50, y);
            else
                addObject(new GiantHuman(hp * 5, 0.5, 100, 50, 30, cash * 10, "giant"), getWidth() - 50, y);
        }
    
        // Stage 5+: Cyborg + Gunner + Tank + Hospital + Cannon
        if (humanEvolutionStage >= 5) {
            int choice = Greenfoot.getRandomNumber(10); // 0–9
            if (choice <= 4) // 50% chance
                addObject(new MeleeHuman(hp* modernMultiplier, speed * 1.1, 50, 40, 25, cash, "cyborg"), getWidth() - 50, y);
            else if (choice <= 8) // 40% chance
                addObject(new RangedHuman((hp / 2)* modernMultiplier, speed * 0.9, 350, 40, 20, cash, "gunner"), getWidth() - 50, y);
            else // 10% chance
                addObject(new GiantHuman((hp * 5)* modernMultiplier, 0.7, 100, 50, 30, cash * 20, "tank"), getWidth() - 50, y);
        }
    }


    private void spawnRobots() {
        int y = 175 + Greenfoot.getRandomNumber(getHeight() - 175);
        int hp = GameSettings.robotHP;
        double speed = GameSettings.robotSpeed;
        int cash = GameSettings.robotCashPerKill;
        // --------------------------
        // Stage 1: Melee Robot
        // --------------------------
        if (robotEvolutionStage == 1) {
            addObject(new MeleeRobot(hp, speed, 40, 30, 20, cash), 50, y);
        } 
        // --------------------------
        // Stage 2: Melee Robot + Ranged Robot + Turret
        // --------------------------
        else if (robotEvolutionStage == 2) {
            if (Greenfoot.getRandomNumber(2) == 0)
                addObject(new MeleeRobot(hp + 50, speed, 35, 45, 20, cash), 50, y);
            else
                addObject(new RangedRobot(hp, speed * 0.8, 300, 40, 15, cash), 50, y);
        } 
        // --------------------------
        // Stage 3: Melee Robot + Ranged Robot + Turret + Factory
        // --------------------------
        else if (robotEvolutionStage >= 3) {
            int choice = Greenfoot.getRandomNumber(10); // 0–9
            if (choice == 0) {
                addObject(new ExplodingRobot(hp * 5, speed * 0.8, 50, 40, 20, cash * 2), 50, y);
            } else if (choice <= 5) {
                addObject(new RangedRobot(hp * 2, speed * 0.9, 300, 50, 20, cash * 2), 50, y);
            } else {
                addObject(new MeleeRobot(hp + 80, speed * 1.1, 45, 50, 25, cash), 50, y);
            }
        }
    }
    
    private void spawnHumanCanon() {
        if (Units.getHumanCash() > 25 && humanEvolutionStage >= 3) return;
        Units.addHumanCash(-25);
    
        int x = getWidth() - 120;
        int y = 0;
        boolean valid = false;
    
        for (int attempts = 0; attempts < 5; attempts++) {
            y = 175 + Greenfoot.getRandomNumber(getHeight() - 350);
            valid = true;
    
            for (Canon c : getObjects(Canon.class)) {
                if (Math.abs(c.getY() - y) < 80) {
                    valid = false;
                    break;
                }
            }
    
            if (valid) break;
        }
    
        if (valid) addObject(new Canon(true), x, y);
    }
    
    private void spawnRobotTurret() {
        if (Units.getRobotCash() > 25 && robotEvolutionStage >= 3) return;
        Units.addRobotCash(-25);
    
        int x = 120;
        int y = 0;
        boolean valid = false;
    
        for (int attempts = 0; attempts < 5; attempts++) {
            y = 175 + Greenfoot.getRandomNumber(getHeight() - 350);
            valid = true;
    
            for (Buildings b : getObjects(Buildings.class)) {
                if (!b.isHumanSide() && Math.abs(b.getY() - y) < 80) {
                    valid = false;
                    break;
                }
            }
    
            if (valid) break;
        }
    
        if (valid) addObject(new Turret(false), x, y);
    }
    
    private void spawnHumanHospital() {
        // Cost check
        if (Units.getHumanCash() > 100) return;
        Units.addHumanCash(-100);
    
        int x = getWidth() - 350; // fixed right side
        int y = 175 ;
    
        addObject(new Hospital(), x, y);
    }
    
    private void spawnRobotFactory() {
        // Cost check
        if (Units.getRobotCash() > 100) return; // corrected
        Units.addRobotCash(-100);
    
        int x = 350; // fixed left side
        int y = 175 ;
        addObject(new Factory(), x, y);
    }
    
    public static int getHumanLevel()
    {
        return humanEvolutionStage;
    }
    
    public static int getRobotLevel()
    {
        return robotEvolutionStage;
    }
    
    

}


