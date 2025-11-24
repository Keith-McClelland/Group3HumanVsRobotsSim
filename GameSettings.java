/**
 * Write a description of class GameSettings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameSettings {
    // Humans
    public static int humanSpawnRate = 120;
    public static int humanHP = 100;
    public static double humanSpeed = 1.8;
    public static int humanCashPerKill = 10;

    // Robots
    public static int robotSpawnRate = 120;
    public static int robotHP = 100;
    public static double robotSpeed = 1.8;
    public static int robotCashPerKill = 10;

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