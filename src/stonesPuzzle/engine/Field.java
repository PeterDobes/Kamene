package stonesPuzzle.engine;

import stonesPuzzle.BestTimes;
import stonesPuzzle.StonesPuzzle;
import stonesPuzzle.consoleui.ConsoleUI;

import java.io.*;
import java.util.Random;

public class Field implements Serializable {
    private static final String FILENAME = "FieldState.bin";
    private Stone[][] stones;
    private final int rowCount;
    private final int columnCount;
    private final int stoneCount;
    private Space space;
    private long time;
    private BestTimes bestTimes;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.stoneCount = rowCount * columnCount;
        this.time = 0L;
        this.bestTimes = new BestTimes();

        scramble();
    }

    public void scramble() {
        stones = new Stone[rowCount][columnCount];
        Random rowN = new Random();
        Random colN = new Random();
        int stonsLeft = stoneCount;

        while (stonsLeft > 0) {
            int row = rowN.nextInt(rowCount);
            int col = colN.nextInt(columnCount);
            if (stones[row][col] == null) {
                stones[row][col] = new Stone(stonsLeft);
                if (stonsLeft == stoneCount) {
                    space = new Space(row,col);
                }
                stonsLeft--;
            }
        }
    }

    public boolean isSolved() {
        int expected = 1;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j <= (columnCount -1); j++) {
                if (getStone(i, j).getValue() != expected) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }

    public void saveFieldIntoFile() {
        time = StonesPuzzle.getInstance().getPlayingTime();
        try (FileOutputStream os = new FileOutputStream(FILENAME);
             ObjectOutputStream oos = new ObjectOutputStream(os);) {
            oos.writeObject(stones);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Stone getStone(int row, int col) {
        return stones[row][col];
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public BestTimes getBestTimes() {
        return bestTimes;
    }
}
