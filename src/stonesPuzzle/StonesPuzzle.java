package stonesPuzzle;

import stonesPuzzle.consoleui.ConsoleUI;
import stonesPuzzle.engine.Field;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import static javax.script.ScriptEngine.FILENAME;

public class StonesPuzzle {

    private ConsoleUI userInterface;
    public static long startMillis;
    private static BestTimes bestTimes = new BestTimes();
    private static StonesPuzzle instance;
    private static final String FILENAME = "FieldState.bin";

    private StonesPuzzle() {
        instance = this;
        userInterface = new ConsoleUI();
        Field field;
        try (FileInputStream is = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            field = (Field) ois.readObject();
            System.out.println("Saved game loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Starting new game");
            field = new Field(4, 4);
        }
        userInterface.newGame(field);
    }

    public static void main(String[] args) {
        new StonesPuzzle();
    }

    public static BestTimes getBestTimes() {
        return bestTimes;
    }

    public static void setBestTimes(BestTimes bestTimes) {
        StonesPuzzle.bestTimes = bestTimes;
    }

    public int getPlayingTime() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public void restartMillis() {
        startMillis = System.currentTimeMillis();
    }

    public static StonesPuzzle getInstance() {
        return instance;
    }
}
