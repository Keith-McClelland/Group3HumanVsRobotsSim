/**
 * The GameSettings class stores all customizable gameplay variables for both the 
 * Human and Robot sides. These values are read by units, spawners, and 
 * buildings during gameplay. These settings can be modified from the settings menu 
 * to adjust gameplay balance, speed, and difficulty. 
 * <p>
 * This class uses static variables so that all worlds and classes can access 
 * the active settings. 
 * @author Jonathan Shi 
 * @version November 2025 
 */
public class GameSettings {
    // Human base settings
    public static int humanSpawnRate = 120; // Frames/delay
    public static int humanHP = 100; // Starting Hp for each human unit
    public static double humanSpeed = 1.8; // Movement speed of humans 
    public static int humanCashPerKill = 10; // Money earned when a Robot kills a Human

    // Robot base settings
    public static int robotSpawnRate = 120; // Frames/delay
    public static int robotHP = 100; // Starting Hp for each robot unit
    public static double robotSpeed = 1.8; // Movement speed of robots 
    public static int robotCashPerKill = 10; // Money earned when a Human kills a Robot

    // Reset all values
    public static void reset() {
        humanSpawnRate = 120;
        humanHP = 100;
        humanSpeed = 1.8;
        humanCashPerKill = 10;

        robotSpawnRate = 120;
        robotHP = 100;
        robotSpeed = 1.8;
        robotCashPerKill = 10;
    }
}